package ads.errors;

public class MatrixException extends ADSException {
	private static final long serialVersionUID = -522514186888334787L;

	public MatrixException(String msg) {
		super(msg);
	}
	
	public static class MatricesNotMultipliableException extends MatrixException {
		private static final long serialVersionUID = -1770879902979636021L;

		public MatricesNotMultipliableException() {
			super("You need A,B matrices that follow this rule ( A: m-by-n & B: n-by-p )");
		}
	}

	public static class MatricesNotSameSizeException extends MatrixException {
		private static final long serialVersionUID = 8981285656443861687L;

		public MatricesNotSameSizeException() {
			super("The two matrices don't have the same size.");
		}
	}
	
	public static class NotInvertibleMatrixException extends MatrixException {
		private static final long serialVersionUID = -6855091767120172529L;

		public NotInvertibleMatrixException(String msg) {
			super(msg);
		}
	}
	
	public static class NotSquareMatrixException extends MatrixException {
		private static final long serialVersionUID = -7200675495130696696L;

		public NotSquareMatrixException(String msg) {
			super(msg);
		}
	}
}
