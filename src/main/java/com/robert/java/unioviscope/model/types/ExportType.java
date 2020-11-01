package com.robert.java.unioviscope.model.types;

public enum ExportType {

	XLSX("XLSX");

	private String type;

	ExportType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ExportType fromString(String type) {
		for (ExportType t : ExportType.values()) {
			if (t.getType().equalsIgnoreCase(type)) {
				return t;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (ExportType t : ExportType.values()) {
			sb.append(t.getType());
			if (!t.equals(ExportType.values()[ExportType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}
