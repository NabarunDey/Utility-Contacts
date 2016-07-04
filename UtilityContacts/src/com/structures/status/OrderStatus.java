package com.structures.status;

public enum OrderStatus {
	
	INITIATED("INITIATED"),
	PROCESSING("PROCESSING"),
	COMPLETE("COMPLETE");
	
	private String text;

	private OrderStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	
	

}
