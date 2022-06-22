package com.farawaybr.portal.security.user;

public class InternalProtheusUser extends ProtheusUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545970305437778898L;

	private SaleType loggedSaleType;

	public InternalProtheusUser(String name, String email, String username, char[] password, String code,
			String fantasyName, SaleType loggedSaleType) {
		super(name, email, username, password, code, fantasyName, SaleType.INTERNO);
		// TODO Auto-generated constructor stub
		this.loggedSaleType = loggedSaleType;
	}

	public InternalProtheusUser(ProtheusUser user) {
		this(user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getCode(),
				user.getFantasyName(), user.getType());
		super.setFetchStatus(user.getFetchStatus());
	}

	public SaleType getLoggedSaleType() {
		return loggedSaleType;
	}

	public void setLoggedSaleType(SaleType loggedSaleType) {
		this.loggedSaleType = loggedSaleType;
	}

}
