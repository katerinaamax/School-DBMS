package login;

public enum option {
	Admin, Teacher;
	
	private option() {
	}
	
	public String value () {
		return name();
	}
	
	public static option fromvalue(String v) {
		return valueOf(v);
	}
}
