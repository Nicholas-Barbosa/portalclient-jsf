package com.portal.client.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetListPage;
import com.portal.client.dto.BudgetXlsxPreviewForm;
import com.portal.client.dto.BudgetXlsxPreviewedDTO;
import com.portal.client.dto.Item;
import com.portal.client.dto.Order;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.microsoft.excel.reader.CellAttribute;
import com.portal.client.microsoft.excel.reader.RowObject;
import com.portal.client.microsoft.excel.reader.XssfReader;
import com.portal.client.microsoft.excel.reader.XssfReaderBuilder;
import com.portal.client.repository.BudgetRepository;

@ApplicationScoped
public class BudgetServiceImpl implements BudgetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;

	private ItemService itemService;

	private BudgetRepository budgetRepository;

	@Inject
	public BudgetServiceImpl(ItemService itemService, BudgetRepository budgetRepository) {
		super();
		this.itemService = itemService;
		this.budgetRepository = budgetRepository;
	}

	@Override
	public BudgetListPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.findAll(page, pageSize);

	}

	@Override
	public void calculateTotals(Order budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newStValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalStValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
		budget.setStValue(newStValue);
	}

	@Override
	public void removeItem(Order budget, Item itemToRemove) {
		if (budget.getItems().remove(itemToRemove)) {
			calculateTotals(budget);
		}
	}

	@Override
	public BudgetXlsxPreviewedDTO previewXlsxContent(BudgetXlsxPreviewForm form) {
		XssfReader reader = XssfReaderBuilder.createReader();
		List<CellAttribute> customerAttributes = new ArrayList<>();
		customerAttributes.add(new CellAttribute(form.getOffsetCellForCustomerName()));
		customerAttributes.add(new CellAttribute(form.getOffSetCellForCustomerStore()));
		RowObject customerRowObject = new RowObject(form.getOffsetRowForCustomerObject(), customerAttributes);

		try {
			Deque<RowObject> products = reader.read(form.getXlsxStreams());
			System.out.println(products);
			reader.read(customerRowObject, form.getXlsxStreams());
			BudgetXlsxPreviewedDTO imported = new BudgetXlsxPreviewedDTO((String) customerRowObject.getFirstAttribute(),
					String.valueOf(((Double) customerRowObject.getLastAttribute()).intValue()));
			return imported;
		} catch (IOException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addItem(Order budgetDTO, Item produc) {
		if (produc != null) {
			budgetDTO.getItems().add(produc);
			this.calculateTotals(budgetDTO);
		}
	}

	@Override
	public void setDiscount(Order budget, BigDecimal discount) throws CustomerNotAllowed {
//		if (budget.getCustomerOnOrder().getType() == CustomerType.PROSPECT) {
		budget.setGlobalDiscount(discount);
		itemService.applyGlobalDiscount(budget.getItems(), discount);
		this.calculateTotals(budget);
		return;
//		}
//		throw new CustomerNotAllowed("You can't set global discount for a normal client!");

	}

}
