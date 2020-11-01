package com.robert.java.unioviscope.model.types;

public enum SyllabusTeachingType {

	FACE_TO_FACE("FACE_TO_FACE"), NON_FACE_TO_FACE("NON_FACE_TO_FACE");

	private String teachingType;

	SyllabusTeachingType(String teachingType) {
		this.teachingType = teachingType;
	}

	public String getTeachingType() {
		return teachingType;
	}

	public static SyllabusTeachingType fromString(String teachingType) {
		for (SyllabusTeachingType tT : SyllabusTeachingType.values()) {
			if (tT.getTeachingType().equalsIgnoreCase(teachingType)) {
				return tT;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (SyllabusTeachingType tT : SyllabusTeachingType.values()) {
			sb.append(tT.getTeachingType());
			if (!tT.equals(SyllabusTeachingType.values()[SyllabusTeachingType.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}