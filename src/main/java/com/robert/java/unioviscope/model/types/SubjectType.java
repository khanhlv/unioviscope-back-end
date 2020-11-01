package com.robert.java.unioviscope.model.types;

public enum SubjectType {

	BASIC_FORMATION("BASIC_FORMATION"), OBLIGATORY("OBLIGATORY"), OPTIONAL("OPTIONAL"), FINAL_PROJECT(
			"FINAL_PROJECT"), EXTERNAL_PRACTICES("EXTERNAL_PRACTICES");

	private String type;

	SubjectType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static SubjectType fromString(String subjectType) {
		for (SubjectType sT : SubjectType.values()) {
			if (sT.getType().equalsIgnoreCase(subjectType)) {
				return sT;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (SubjectType sT : SubjectType.values()) {
			sb.append(sT.getType());
			if (!sT.equals(SubjectType.values()[SubjectType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}
