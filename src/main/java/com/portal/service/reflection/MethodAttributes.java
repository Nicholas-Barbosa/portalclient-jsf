package com.portal.service.reflection;

public class MethodAttributes  implements Comparable<MethodAttributes> {

	private String name;
	private Object[] params;

	public MethodAttributes(String name, Object[] params) {
		super();
		this.name = name;
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public Object[] getParams() {
		return params;
	}

	@Override
	public int compareTo(MethodAttributes o) {
		// TODO Auto-generated method stub
		return name.compareTo(o.name);
	}

}
