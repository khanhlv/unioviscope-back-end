package com.robert.java.unioviscope.model.types;

public enum TemporalitySubjectType {

	FIRST_SEMESTER("FIRST_SEMESTER"), SECOND_SEMESTER("SECOND_SEMESTER");

	private String subjectType;

	TemporalitySubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public static TemporalitySubjectType fromString(String subjectType) {
		for (TemporalitySubjectType sT : TemporalitySubjectType.values()) {
			if (sT.getSubjectType().equalsIgnoreCase(subjectType)) {
				return sT;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (TemporalitySubjectType sT : TemporalitySubjectType.values()) {
			sb.append(sT.getSubjectType());
			if (!sT.equals(TemporalitySubjectType.values()[TemporalitySubjectType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}
