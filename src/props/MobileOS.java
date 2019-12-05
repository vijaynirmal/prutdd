package props;

public enum MobileOS {

	ANDROID("Android"),
	IOS ("iOS");

	  private final String value;

	  private MobileOS(String value) {
	    this.value = value;
	  }

	  public String value() {
	    return value;
	  }
}
