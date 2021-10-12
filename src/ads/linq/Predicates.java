package ads.linq;

import java.util.function.Predicate;

public interface Predicates {
	
	static <E> Predicate<E> alwaysTrue() {
		return e -> true;
	}
	
	static <E> Predicate<E> alwaysFalse() {
		return e -> false;
	}
}
