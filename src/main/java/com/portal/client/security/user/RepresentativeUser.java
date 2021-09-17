package com.portal.client.security.user;

public class RepresentativeUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333174724267823064L;
	private String code, fantasyName;

	public RepresentativeUser(String code, String fantasyName, String name, String username, String email,
			char[] password) {
		super(name, username, email, password);
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	@Override
	public boolean isDataComplete() {
		// TODO Auto-generated method stub
		return super.isDataComplete() && code != null && fantasyName != null;
	}
}
