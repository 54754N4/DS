package ads.funcs;
import ads.common.Tensors;
import ads.contracts.Tensor;

public interface Func {
	
	public static void main(String[] args) {
		int[] shape = new int[] {1,2,3};
		double[] data = new double[] {0,1,2,3,4,5};
		Tensor t = Func.of(Tensors::colMajor, shape, data)
			.exec();
		System.out.println(t);
	}
	
	/* Convenience methods */
	
	static Func of(Func f) {
		return f;
	}
	
	static <R> Func0<R> of(Func0<R> f) {
		return f;
	}
	
	static <T,R> Func0<R> of(Func1<T,R> f, final T t) {
		return () -> f.exec(t);
	}
	
	static <T1,T2,R> Func0<R> of(Func2<T1,T2,R> f, final T1 t1, final T2 t2) {
		return () -> f.exec(t1, t2);
	}
	
	static <T1,T2,T3,R> Func0<R> of(Func3<T1,T2,T3,R> f, final T1 t1, final T2 t2, final T3 t3) {
		return () -> f.exec(t1, t2, t3);
	}
	
	static <T1,T2,T3,T4,R> Func0<R> of(Func4<T1,T2,T3,T4,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4) {
		return () -> f.exec(t1, t2, t3, t4);
	}
	
	static <T1,T2,T3,T4,T5,R> Func0<R> of(Func5<T1,T2,T3,T4,T5,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5) {
		return () -> f.exec(t1, t2, t3, t4, t5);
	}
	
	static <T1,T2,T3,T4,T5,T6,R> Func0<R> of(Func6<T1,T2,T3,T4,T5,T6,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,R> Func0<R> of(Func7<T1,T2,T3,T4,T5,T6,T7,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,R> Func0<R> of(Func8<T1,T2,T3,T4,T5,T6,T7,T8,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,R> Func0<R> of(Func9<T1,T2,T3,T4,T5,T6,T7,T8,T9,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,R> Func0<R> of(Func10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,R> Func0<R> of(Func11<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,R> Func0<R> of(Func12<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,R> Func0<R> of(Func13<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,R> Func0<R> of(Func14<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
	}
	
	static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,R> Func0<R> of(Func15<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,R> f, final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15) {
		return () -> f.exec(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
	}
	
	/* Lambda definitions */
	
	@FunctionalInterface
	static interface Func0<R> extends Func {
		R exec();
	}

	@FunctionalInterface
	static interface Func1<T, R> extends Func {
		R exec(T t1);
	}

	@FunctionalInterface
	static interface Func2<T1, T2, R> extends Func {
		R exec(T1 t1, T2 t2);
	}

	@FunctionalInterface
	static interface Func3<T1,T2,T3,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3);
	}

	@FunctionalInterface
	static interface Func4<T1,T2,T3,T4,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4);
	}
	
	@FunctionalInterface
	static interface Func5<T1,T2,T3,T4,T5,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
	}
	
	@FunctionalInterface
	static interface Func6<T1,T2,T3,T4,T5,T6,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
	}
	
	@FunctionalInterface
	static interface Func7<T1,T2,T3,T4,T5,T6,T7,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7);
	}
	
	@FunctionalInterface
	static interface Func8<T1,T2,T3,T4,T5,T6,T7,T8,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
	}
	
	@FunctionalInterface
	static interface Func9<T1,T2,T3,T4,T5,T6,T7,T8,T9,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9);
	}
	
	@FunctionalInterface
	static interface Func10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10);
	}

	@FunctionalInterface
	static interface Func11<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11);
	}
	
	@FunctionalInterface
	static interface Func12<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12);
	}

	@FunctionalInterface
	static interface Func13<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13);
	}
	
	@FunctionalInterface
	static interface Func14<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14);
	}
	
	@FunctionalInterface
	static interface Func15<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,R> extends Func {
		R exec(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15);
	}
	
}