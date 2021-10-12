package ads.errors;

public abstract class TensorException extends ADSException {
	private static final long serialVersionUID = -4043050724226732594L;

	public TensorException(String string) {
		super(string);
	}
	
	public static class DifferentDimensionTensorsException extends TensorException {
		private static final long serialVersionUID = 8605131990404842020L;

		public DifferentDimensionTensorsException() {
			this("Tensors need to have same dimensions");
		}
		
		public DifferentDimensionTensorsException(String s) {
			super(s);
		}
	}
	
	public static class ElementsCountInvalidException extends TensorException {
		private static final long serialVersionUID = 806002388603786403L;

		public ElementsCountInvalidException(String string) {
			super(string);
		}
	}
}
