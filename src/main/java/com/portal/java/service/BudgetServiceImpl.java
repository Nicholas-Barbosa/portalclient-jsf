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
import com.portal.java.dto.ProductDTO;
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
	private ProductService productService;

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
	public void recalculate(BudgetDTO budget, ProductDTO newProductValues) {
		productService.recalculateProduct(newProductValues);
		calculateTotal(budget);
	}

	@Override
	public void removeItem(BudgetDTO budget, ProductDTO itemToRemove) {
		if (budget.getItems().remove(itemToRemove)) {
			calculateTotal(budget);
		}
	}

	@Override
	public void checkQuantityPolicies(BudgetEstimatedDTO budget) {
		// String[]itemsOutOfStock =
		// budget.getEstimatedItemValues().parallelStream().filter(i -> i.get)

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

	private void calculateTotal(BudgetDTO budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(p -> p.getPrice().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(p -> p.getPrice().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
	}
}
