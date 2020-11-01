package com.robert.java.unioviscope.model.types;

public enum SyllabusState {

	ACTIVE("ACTIVE"), IN_EXTINCTION("IN_EXTINCTION");

	private String state;

	SyllabusState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public static SyllabusState fromString(String state) {
		for (SyllabusState s : SyllabusState.values()) {
			if (s.getState().equalsIgnoreCase(state)) {
				return s;
			}
		}
		return null;
	}

	public static String stringValues() {
		StringBuilder sb = new StringBuilder("[");

		for (SyllabusState s : SyllabusState.values()) {
			sb.append(s.getState());
			if (!s.equals(SyllabusState.values()[SyllabusState.values().length - 1]))
				sb.append(", ");
		}
		return sb.append("]").toString();
	}
}