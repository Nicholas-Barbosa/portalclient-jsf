package com.portal.client.security.user;

public class InternalRepresentativeUser extends RepresentativeUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545970305437778898L;

	private SaleType loggedSaleType;

	public InternalRepresentativeUser(String code, String fantasyName, String name, String username, String email,
			char[] password, SaleType type) {
		super(code, fantasyName, name, username, email, password, type);
		// TODO Auto-generated constructor stub
	}

	public InternalRepresentativeUser(RepresentativeUser user) {
		super(user.getCode(), user.getFantasyName(), user.getName(), user.getUsername(), user.getEmail(),
				user.getPassword(), user.getType());
		// TODO Auto-generated constructor stub
	}

	public SaleType getLoggedSaleType() {
		return loggedSaleType;
	}

	public void setLoggedSaleType(SaleType loggedSaleType) {
		this.loggedSaleType = loggedSaleType;
	}

}
