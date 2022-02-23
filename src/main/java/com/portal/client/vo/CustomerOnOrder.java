package com.portal.client.vo;

public class CustomerOnOrder extends Customer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4894289566624375884L;

	protected CustomerType type;

	public CustomerOnOrder(String code, String store, String cnpj, String blocked, String name, String fantasyName,
			CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact) {
		super(code, store, cnpj, blocked, name, fantasyName, address, financialInfo, contact, null);
		type = CustomerType.NORMAL;
	}

	public CustomerOnOrder(Customer customer) {
		this(customer.getCode(), customer.getStore(), customer.getCnpj(), customer.getBlocked(), customer.getName(),
				customer.getFantasyName(), customer.getAddress(), customer.getFinancialInfo(), customer.getContact());
	}

	public CustomerType getType() {
		return type;
	}

	public static enum CustomerType {
		NORMAL, PROSPECT
	}
}
