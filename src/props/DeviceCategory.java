package props;

public enum DeviceCategory {

	PHONE ("Phone"),
	  TABLET ("Tablet");

	  private final String value;

	  private DeviceCategory(String value) {
	    this.value = value;
	  }

	  public String value() {
	    return value;
	  }
}
