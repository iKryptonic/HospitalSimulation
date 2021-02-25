public enum Title {
	
	Dr("Doctor"),
	RN("Registered Nurse"),
	CNA("Nursing Assistant");
	
	String title;
	
	public String getTitle() {
	    return title;
	}
	
	Title(String title) {
	    this.title = title;
	}
}