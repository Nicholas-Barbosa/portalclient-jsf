package com.portal.client.service;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ItemXlsxProjection;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.microsoft.excel.RowObject;
import com.portal.client.service.microsoft.excel.reader.XssfReader;

@ApplicationScoped
public class ItemImportServiceImpl implements ItemImportService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8496725734748305921L;
	private XssfReader xssReader;
	private BudgetCrudService budgetCrudService;

	@Inject
	public ItemImportServiceImpl(XssfReader xssReader, BudgetCrudService budgetCrudService) {
		super();
		this.xssReader = xssReader;
		this.budgetCrudService = budgetCrudService;
	}

	@Override
	public List<ItemXlsxProjection> read(ItemXlsxFileLayout fileLayout) {
		try {
			List<RowObject> rows = xssReader.read(fileLayout.getXlsxStreams(), fileLayout.getInitPosition(),
					fileLayout.getLastPosition());
			return rows.parallelStream()
					.map(r -> this.of(r, fileLayout.getOffSetCellForProductCode(),
							fileLayout.getOffSetCellForProductQuantity()))
					.distinct().collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private ItemXlsxProjection of(RowObject row, int offsetForCode, int offSetForQuantity) {
		Map<Integer, Object> attributes = row.getCellAttributes().stream()
				.collect(Collectors.toMap(k -> k.getCellOffset(), v -> v.getValue()));
		String code = (String) attributes.get(offsetForCode);
		Double quantity = (Double) attributes.get(offSetForQuantity);
		return new ItemXlsxProjection(code, quantity.intValue());
	}

	@Override
	public BaseBudget findPrice(List<ItemXlsxProjection> items, String customerCode, String customerStore)
			throws ConnectException, SocketException, TimeoutException, SocketTimeoutException,
			CustomerNotFoundException, ItemsNotFoundException {
		return budgetCrudService.estimate(customerCode, customerStore,
				items.parallelStream().filter(i -> i.getQuantity() > 0).map(this::toItem)
						.collect(CopyOnWriteArraySet::new, Set::add, Set::addAll));

	}

	private ItemToFindPrice toItem(ItemXlsxProjection itemToConvert) {
		return new ItemToFindPrice(itemToConvert.getCode(), itemToConvert.getQuantity());
	}

}
