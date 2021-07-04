package ads.common;

import ads.tensors.TensorFactory;

public interface Tensors {
	static TensorFactory factory() {
		return TensorFactory.getInstance();
	}
}
