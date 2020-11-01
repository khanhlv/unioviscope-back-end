package com.robert.java.unioviscope.model.types;

public enum ImportType {

	CSV("CSV");

	private String type;

	ImportType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ImportType fromString(String type) {
		for (ImportType t : ImportType.values()) {
			if (t.getType().equalsIgnoreCase(type)) {
				return t;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (ImportType t : ImportType.values()) {
			sb.append(t.getType());
			if (!t.equals(ImportType.values()[ImportType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}
