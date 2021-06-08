package com.portal.java.pojo;

public class ObjectValue {

	private Object currentValue;
	private Object oldValue;

	public ObjectValue(Object currentValue, Object oldValue) {
		super();
		this.currentValue = currentValue;
		this.oldValue = oldValue;
	}

	public Object getCurrentValue() {
		return currentValue;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public ObjectValue setCurrentValue(Object currentValue) {
		this.currentValue = currentValue;
		return this;
	}

	public ObjectValue setOldValue(Object oldValue) {
		this.oldValue = oldValue;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentValue == null) ? 0 : currentValue.hashCode());
		result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
		return result;
	}

}
