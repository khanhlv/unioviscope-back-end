package com.robert.java.unioviscope.model.types;

public enum GroupType {

	THEORY("THEORY"), PRACTICE("PRACTICE"), SEMINAR("SEMINAR"), GROUP_TUTORSHIP("GROUP_TUTORSHIP");

	private String type;

	GroupType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static GroupType fromString(String type) {
		for (GroupType t : GroupType.values()) {
			if (t.getType().equalsIgnoreCase(type)) {
				return t;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (GroupType t : GroupType.values()) {
			sb.append(t.getType());
			if (!t.equals(GroupType.values()[GroupType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}
