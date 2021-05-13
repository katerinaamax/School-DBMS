package data;

import login.option;

public enum relationship {
	Mother, Father;
	
	private relationship() {
	}
	
	public String value () {
		return name();
	}
	
	public static relationship fromvalue(String v) {
		return valueOf(v);
	}
}
