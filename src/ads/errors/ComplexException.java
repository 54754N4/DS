package ads.errors;

public class ComplexException extends ADSException {
	private static final long serialVersionUID = -736395665571259292L;

	public ComplexException(String msg) {
		super(msg);
	}

	public static class InverseZeroComplexException extends ComplexException {
		private static final long serialVersionUID = -5405434972993050733L;
		
		public InverseZeroComplexException() {
			super("Cannot inverse the complex (0,0).");
		}
	}
	
	public static class PhaseZeroComplexException extends ComplexException {
		private static final long serialVersionUID = 2320628836150673767L;

		public PhaseZeroComplexException() {
			super("The phase of the complex number z=0 cannot be calculated.");
		}
	}
}
