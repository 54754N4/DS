package algebra.common;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public interface Algebras {
	
	/* Magmas */
	
	static interface BaseMagma<Scalar, Vector> {
		default boolean hasClosure() {	// AKA Totality
			return true;
		}
		
		default boolean hasAssociativity() {
			return false;
		}
		
		default boolean hasLeftAssociativity() {
			return hasAssociativity() || false;
		}
		
		default boolean hasRightAssociativity() {
			return hasAssociativity() || false;
		}
		
		default boolean hasDivisibility() {
			return false;
		}
		
		default boolean hasLeftDivisibility() {
			return hasDivisibility() || false;
		}
		
		default boolean hasRightDivisibility() {
			return hasDivisibility() || false;
		}
		
		default boolean hasIdentity() {
			return false;
		}
		
		default boolean hasUniqueIdentity() {
			return false;
		}
		
		default boolean hasInvertibility() {
			return false;
		}
		
		default boolean hasCommutativity() {
			return false;
		}
		
		default boolean hasIdempotency() {
			return false;
		}
	}
	
	@FunctionalInterface
	static interface Magma<Scalar, Vector> extends BaseMagma<Scalar, Vector> {
		BiFunction<Vector, Scalar, Vector> operation();
	}
	
	static interface Quasigroup<Scalar, Vector> extends Magma<Scalar, Vector> {
		BiFunction<Vector, Scalar, Vector> leftInverse();
		BiFunction<Vector, Scalar, Vector> rightInverse();
		
		@Override
		default boolean hasDivisibility() {
			return true;
		}
	}
	
	static interface UnitalMagma<Scalar, Vector> extends Magma<Scalar, Vector> {
		Vector identity();
		
		@Override
		default boolean hasUniqueIdentity() {
			return true;
		}
	}
	
	@FunctionalInterface
	static interface Semigroup<Scalar, Vector> extends Magma<Scalar, Vector> {
		@Override
		default boolean hasAssociativity() {
			return true;
		}
	}
	
	@FunctionalInterface
	static interface CommutativeSemigroup<Scalar, Vector> extends Semigroup<Scalar, Vector> {
		@Override
		default boolean hasCommutativity() {
			return true;
		}
	}
	
	@FunctionalInterface
	static interface IdempotentCommutativeSemigroup<Scalar, Vector> extends CommutativeSemigroup<Scalar, Vector> {
		@Override
		default boolean hasIdempotency() {
			return true;
		}
	}
	
	static interface Loop<Scalar, Vector> extends Quasigroup<Scalar, Vector>, UnitalMagma<Scalar, Vector> {
		
	}
	
	static interface InverseSemigroup<Scalar, Vector> extends Quasigroup<Scalar, Vector>, Semigroup<Scalar, Vector> {
		BiFunction<Vector, Scalar, Vector> inverse();
		
		@Override
		default BiFunction<Vector, Scalar, Vector> leftInverse() {
			return inverse();
		}
		
		@Override
		default BiFunction<Vector, Scalar, Vector> rightInverse() {
			return inverse();
		}
		
		@Override
		default boolean hasInvertibility() {
			return true;
		}
	}
	
	static interface Monoid<Scalar, Vector> extends Semigroup<Scalar, Vector>, UnitalMagma<Scalar, Vector> {
		
	}
	
	static interface CommutativeMonoid<Scalar, Vector> extends Monoid<Scalar, Vector> {
		@Override
		default boolean hasCommutativity() {
			return true;
		}
	}
	
	static interface IdempotentCommutativeMonoid<Scalar, Vector> extends CommutativeMonoid<Scalar, Vector> {
		@Override
		default boolean hasIdempotency() {
			return true;
		}
	}
	
	static interface Group<Scalar, Vector> extends Loop<Scalar, Vector>, InverseSemigroup<Scalar, Vector>, Monoid<Scalar, Vector> {
		
	}

	static interface AbelianGroup<Scalar, Vector> extends Group<Scalar, Vector>, CommutativeMonoid<Scalar, Vector> {
		@Override
		default boolean hasCommutativity() {
			return true;
		}
	}
	
	/* Rings */
	
	static interface BaseRing<
		Scalar, Vector,
		Sum extends UnitalMagma<Scalar, Vector>,
		Product extends Magma<Scalar, Vector>
	> {
		Sum addition();
		Product multiplication();
		
		default Vector absorbantElement() {
			return addition().identity();
		}
		
		default Vector add(Vector a, Scalar b) {
			return addition().operation().apply(a, b);
		}
		
//		default Vector multiply(Scalar a, Scalar b) {
//			return multiplication().operation().apply(a, b);
//		}
		
		default boolean hasMultiplicativeDistributivity() {
			return false;
		}
		
		default boolean hasMultiplicativeLeftDistributivity() {
			return hasMultiplicativeDistributivity() || false;
		}
		
		default boolean hasMultiplicativeRightDistributivity() {
			return hasMultiplicativeDistributivity() || false;
		}
	}
	
	static interface Semiring<
		Scalar,
		Vector, 
		Sum extends CommutativeMonoid<Scalar, Vector>,
		Product extends Monoid<Scalar, Vector>
	> extends BaseRing<Scalar, Vector, Sum, Product> {
		@Override
		default boolean hasMultiplicativeDistributivity() {
			return true;
		}
	}
	
	static interface CommutativeSemiring<
		Scalar,
		Vector,
		Sum extends CommutativeMonoid<Scalar, Vector>,
		Product extends CommutativeMonoid<Scalar, Vector>
	> extends Semiring<Scalar, Vector, Sum, Product> {
		
	}
	
	static interface NonAssociativeRing<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends Quasigroup<Scalar, Vector>
	> extends BaseRing<Scalar, Vector, Sum, Product> {
		
	}
	
	static interface NonUnitalRing<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends Semigroup<Scalar, Vector>
	> extends BaseRing<Scalar, Vector, Sum, Product> {
		@Override
		default boolean hasMultiplicativeDistributivity() {
			return true;
		}
	}
	
	static interface NearRing<
		Scalar,
		Vector,
		Sum extends Group<Scalar, Vector>,
		Product extends Semigroup<Scalar, Vector>
	> extends BaseRing<Scalar, Vector, Sum, Product> {
		@Override
		default boolean hasMultiplicativeRightDistributivity() {
			return true;
		}
	}
	
	static interface Ring<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends Monoid<Scalar, Vector>
	> extends NearRing<Scalar, Vector, Sum, Product> {
		@Override
		default boolean hasMultiplicativeDistributivity() {
			return true;
		}
	}

	static interface CommutativeRing<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends CommutativeMonoid<Scalar, Vector>
	> extends Ring<Scalar, Vector, Sum, Product> {
		
	}
	
	static interface Field<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends AbelianGroup<Scalar, Vector>
	> extends CommutativeRing<Scalar, Vector, Sum, Product> {
		
	}
	
	/* Lattice */
	
	@FunctionalInterface
	static interface BaseLattice<
		Scalar,
		Vector,
		Comparer extends Semigroup<Scalar, Vector>
	> {
		Comparer comparator();
		
	}
	
	@FunctionalInterface
	static interface Semilattice<
		Scalar,
		Vector,
		Comparer extends IdempotentCommutativeSemigroup<Scalar, Vector>
	> extends BaseLattice<Scalar, Vector, Comparer> {
		
	}
	
	@FunctionalInterface
	static interface BoundedSemilattice<
		Scalar,
		Vector,
		Comparer extends IdempotentCommutativeMonoid<Scalar, Vector>
	> extends BaseLattice<Scalar, Vector, Comparer> {
		default Vector bound() {
			return comparator().identity();
		}
	}
	
	static interface Lattice<
		Scalar,
		Vector,
		Comparer extends IdempotentCommutativeSemigroup<Scalar, Vector>
	> {
		Semilattice<Scalar, Vector, Comparer> ascending();
		Semilattice<Scalar, Vector, Comparer> descending();
	}
	
//	static interface BoundedLattice<
//		Scalar,
//		Vector,
//		Comparer extends CommutativeMonoid<Scalar, Vector>
//	> extends Lattice<Scalar, Vector, Comparer> {
//		default Vector bottom() {
//			return ascending().comparator().
//		}
//	}
	
	/* Vector space */
	
	static interface VectorSpace<
		Scalar,
		Vector,
		Sum extends AbelianGroup<Scalar, Vector>,
		Product extends AbelianGroup<Scalar, Vector>,
		F extends Field<Scalar, Vector, Sum, Product>
	> {
		
	}
}
