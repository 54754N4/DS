package ads.errors;

public class ADSException extends RuntimeException {
	private static final long serialVersionUID = -6370093728911033560L;

	public ADSException(String msg) {
		super(msg);
	}
}
