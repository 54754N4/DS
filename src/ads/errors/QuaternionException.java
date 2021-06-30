package ads.errors;

public class QuaternionException extends ADSException {
	private static final long serialVersionUID = 3896653765582269857L;

	public QuaternionException(String msg) {
		super(msg);
	}
	
	public static class CannotInverseQuaternionZeroException extends QuaternionException {
		private static final long serialVersionUID = 2558453936142964458L;

		public CannotInverseQuaternionZeroException() {
			super("Quaternion of norm 0 can only be 0 and cannot be inversed.");
		}
	}
}
