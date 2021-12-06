package com.portal.client.security.user;

import java.io.Serializable;
import java.util.Arrays;

public class RepresentativeUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4333174724267823064L;
	private String name, email;
	private final String username;
	private final char[] password;
	private String code, fantasyName;
	private SaleType type;
	private FetchStatus fetchStatus;
	
	public RepresentativeUser(String name, String email, String username, char[] password, String code,
			String fantasyName, SaleType type) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.code = code;
		this.fantasyName = fantasyName;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public char[] getPassword() {
		return password.clone();
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

	public SaleType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}

	public FetchStatus getFetchStatus() {
		return fetchStatus;
	}
	public void setFetchStatus(FetchStatus fetchStatus) {
		this.fetchStatus = fetchStatus;
	}
	public Object writeReplace() {
		Arrays.fill(password, '*');
		return this;
	}

	public static enum SaleType {
		CARROS("C", "Autopeças"), MOTOS("M", "Motopeças"), AGRICOLA("A", "Agrícola & Construção"),
		INTERNO("U", "Interno");

		private final String type;
		private final String reportLabel;

		private SaleType(String type, String reportLabel) {
			this.type = type;
			this.reportLabel = reportLabel;
		}

		public String getType() {
			return type;
		}

		public String getReportLabel() {
			return reportLabel;
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
	
	public static enum FetchStatus{
		FETCHED,NOT_FETCHED
	}
}
