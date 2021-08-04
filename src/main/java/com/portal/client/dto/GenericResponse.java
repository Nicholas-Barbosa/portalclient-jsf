package com.portal.client.dto;

import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class GenericResponse {
	private int status;

	private Set<ResponseField> fields;

	private String cause;

	public GenericResponse(int status, Set<ResponseField> fields, String cause) {
		super();
		this.status = status;
		this.fields = fields;
		this.cause = cause;
	}

	@JsonbProperty
	public int getStatus() {
		return status;
	}

	@JsonbProperty
	public Set<ResponseField> getFields() {
		return fields;
	}

	@JsonbProperty
	public String getCause() {
		return cause;
	}

	public static class ResponseField {
		private String key;
		private Object value;

		public ResponseField(String key, Object value) {
			super();
			this.key = key;
			this.value = value;
		}

		@JsonbProperty
		public String getKey() {
			return key;
		}

		@JsonbProperty
		public Object getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResponseField other = (ResponseField) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

	}
}
