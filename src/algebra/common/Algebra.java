package algebra.common;

public interface Algebra {
	
	/* Algebraic properties 
	 * */

	static interface LeftAssociativity {
		default boolean isLeftAssociative() {
			return true;
		}
	}
	
	static interface RightAssociativity {
		default boolean isRightAssociative() {
			return true;
		}
	}
	
	static interface Associativity extends LeftAssociativity, RightAssociativity {
		default boolean isAssociative() {
			return true;
		}
	}
	
	static interface Identity<E> {
		E identity();
	}
	
	static interface LeftDivisibility<E> {
		E leftDivision(E a, E b);
	}
	
	static interface RightDivisibility<E> {
		E rightDivision(E a, E b);
	}
	
	static interface Divisibility<E> extends LeftDivisibility<E>, RightDivisibility<E> {
		
	}

	static interface Invertibility<E> extends Divisibility<E> {
		E division(E a, E b);
		
		@Override
		default E leftDivision(E a, E b) {
			return division(a, b);
		}
		
		@Override
		default E rightDivision(E a, E b) {
			return division(a, b);
		}
	}
	
	static interface Commutativity {
		default boolean isCommutative() {
			return true;
		}
	}
	
	/* Group-like structures
	 * https://en.wikipedia.org/wiki/Magma_(algebra) 
	 * */
	
	static interface Magma<E> {
		E addition(E first, E second);
	}
	
	static interface Quasigroup<E> extends Magma<E>, Divisibility<E> {
		E product(E first, E second);
	}
	
	static interface UnitalMagma<E> extends Magma<E> {
		E additiveIdentity();
	}

	static interface Semigroup<E> extends Magma<E>, Associativity {
		
	}

	static interface Loop<E> extends Quasigroup<E>, UnitalMagma<E>, Invertibility<E> {
		E multiplicativeIdentity();
	}
	
	static interface InverseSemigroup<E> extends Quasigroup<E>, Semigroup<E> {
		
	}
	
	static interface Monoid<E> extends UnitalMagma<E>, Semigroup<E> {
		
	}
	
	static interface Group<E> extends Loop<E>, InverseSemigroup<E>, Monoid<E> {
		
	}
	
	static interface AbelianGroup<E> extends Group<E>, Commutativity {
		
	}
	
	/* Ring-like structures 
	 * https://en.wikipedia.org/wiki/Ring_(mathematics)
	 * */
	
	static interface Rng<E> extends AbelianGroup<E> {
		default boolean isAdditivelyAssociative() {
			return true;
		}
		
		default boolean isMultiplicativelyAssociative() {
			return true;
		}
		
		default boolean isAdditivelyCommutative() {
			return true;
		}
		
		default boolean isMultiplicativelyCommutative() {
			return false;
		}
		
		default boolean isMultiplicativelyDistributiveOverAddition() {
			return true;
		}
	}
	
	static interface Ring<E> extends Rng<E> {
		
	}
	
	static interface NonAssociativeRing<E> extends Ring<E> {
		@Override
		default boolean isAssociative() {
			return false;
		}
	}
	
	static interface CommutativeRing<E> extends Ring<E> {
		@Override
		default boolean isMultiplicativelyCommutative() {
			return true;
		}
	}
}
