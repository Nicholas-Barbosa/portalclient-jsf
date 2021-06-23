package com.portal.java.service;

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

import com.portal.java.dto.BudgetDTO;
import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;
import com.portal.java.dto.BudgetXlsxPreviewForm;
import com.portal.java.dto.BudgetXlsxPreviewedDTO;
import com.portal.java.dto.CustomerOnOrder.CustomerType;
import com.portal.java.exception.CustomerNotAllowed;
import com.portal.java.dto.Item;
import com.portal.java.microsoft.excel.CellAttribute;
import com.portal.java.microsoft.excel.RowObject;
import com.portal.java.microsoft.excel.XssfReader;
import com.portal.java.microsoft.excel.XssfReaderBuilder;
import com.portal.java.repository.BudgetRepository;

@ApplicationScoped
public class BudgetServiceImpl implements BudgetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;
	@Inject
	private BudgetRepository budgetRepository;
	@Inject
	private ItemService itemService;

	@Override
	public void findAll(int page, int pageSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		BudgetEstimatedDTO dto = budgetRepository.estimate(budgetEstimateForm);
		dto.getItems().parallelStream().forEach(e -> {
			budgetEstimateForm.getItemsForm().parallelStream()
					.filter(i -> i.getCommercialCode().equals(e.getCommercialCode())).findAny()
					.ifPresent((productForm) -> {
						e.setDescription(productForm.getDescription());
						e.setMultiple(productForm.getMultiple());
						e.setInfo(productForm.getInfo());
						BigDecimal discProductForm = productForm.getDiscount();
						e.setDiscount(discProductForm == null ? e.getDiscount() : discProductForm);
					});

		});
		return dto;
	}

	@Override
	public void calculateTotals(BudgetDTO budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(p -> p.getItemPrice().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(p -> p.getItemPrice().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newStValue = budget.getItems().parallelStream().map(p -> p.getItemPrice().getTotalStValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
		budget.setStValue(newStValue);
	}

	@Override
	public void calculateTotals(BudgetDTO budget, Item newProductValues, boolean calculateDueChangesInDiscount,
			boolean calculateDueChangesInQuantity) {
		if (calculateDueChangesInDiscount && calculateDueChangesInQuantity)
			itemService.calculateDueQuantity(newProductValues, true);
		else if (calculateDueChangesInDiscount && !calculateDueChangesInQuantity)
			itemService.calculateDueDiscount(newProductValues);
		else
			itemService.calculateDueQuantity(newProductValues, false);
		this.calculateTotals(budget);
	}

	@Override
	public void removeItem(BudgetDTO budget, Item itemToRemove) {
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
	public void calculateForGlobalDiscount(BudgetDTO budgetDTO) {
		if (budgetDTO.getGlobalDiscount().intValue() > 0) {
			budgetDTO.getItems().parallelStream().forEach(p -> {
//				productService.addDiscount(p, budgetDTO.getGlobalDiscount());
			});
			this.calculateTotals(budgetDTO);
		}
	}

	@Override
	public void addItem(BudgetDTO budgetDTO, Item produc) {
		if (produc != null) {
			budgetDTO.getItems().add(produc);
			this.calculateTotals(budgetDTO);
		}
	}

	@Override
	public void setDiscount(BudgetDTO budget, BigDecimal discount)throws CustomerNotAllowed {
		if (budget.getCustomerOnOrder().getType() == CustomerType.PROSPECT) {
			budget.setGlobalDiscount(discount);
			budget.getItems().parallelStream().forEach(i -> i.setBudgetGlobalDiscount(discount));
			itemService.calculateDueDiscount(budget.getItems());
			this.calculateTotals(budget);
			return;
		}
		throw new CustomerNotAllowed("You can't set global discount for a normal client!");

	}

}
