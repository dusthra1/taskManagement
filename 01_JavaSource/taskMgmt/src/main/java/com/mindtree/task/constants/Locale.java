package com.mindtree.task.constants;

public enum Locale {	
	
	ENGLISH(1),
	SPANISH(2),
	FRENCH(3);
	
	public final int value;
	
	private Locale(int locale){
		this.value = locale;
	}

	public int getValue() {
		return value;
	}
	
}
