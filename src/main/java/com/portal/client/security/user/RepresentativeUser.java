package com.portal.client.security.user;

public class RepresentativeUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333174724267823064L;
	private String code, fantasyName;
	private RepresentativeType type;

	public RepresentativeUser(String code, String fantasyName, String name, String username, String email,
			char[] password, RepresentativeType type) {
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

	public RepresentativeType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public static enum RepresentativeType {
		CARROS("C"), MOTOS("M"), AGRICOLA("A");

		private final String type;

		private RepresentativeType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public static RepresentativeType fromAcronym(String acronym) {
			switch (acronym) {
			case "A":
				return RepresentativeType.AGRICOLA;
			case "C":
				return RepresentativeType.CARROS;
			case "M":
				return RepresentativeType.MOTOS;
			default:
				throw new IllegalArgumentException("Arg0-" + acronym + " not supported!");
			}
		}
	}
}
