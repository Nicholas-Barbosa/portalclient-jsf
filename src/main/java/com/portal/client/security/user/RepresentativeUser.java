package com.portal.client.security.user;

public class RepresentativeUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333174724267823064L;
	private String code, fantasyName;
	private SaleType type;

	public RepresentativeUser(String code, String fantasyName, String name, String username, String email,
			char[] password, SaleType type) {
		super(name, username, email, password);
		this.code = code;
		this.fantasyName = fantasyName;
		this.type = type;
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

	public SaleType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}

	public static enum SaleType {
		CARROS("C"), MOTOS("M"), AGRICOLA("A"), INTERNO("U");

		private final String type;

		private SaleType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public static SaleType fromAcronym(String acronym) {
			switch (acronym) {
			case "A":
				return SaleType.AGRICOLA;
			case "C":
				return SaleType.CARROS;
			case "M":
				return SaleType.MOTOS;
			case "U":
				return SaleType.INTERNO;
			default:
//				throw new IllegalArgumentException("RepresentativeType Arg0-" + acronym + " not supported!");
				return SaleType.INTERNO;
			}
		}
	}
}
