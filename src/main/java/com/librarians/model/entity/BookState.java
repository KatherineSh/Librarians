package com.librarians.model.entity;

public enum BookState {
	
	IN_TAKE("inTake"),
	RETURNED("returned");
	
	private final String stateDescription;

	private BookState(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public String getStateDescription() {
		return stateDescription;
	}
}
