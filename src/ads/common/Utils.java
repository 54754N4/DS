package ads.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public interface Utils {
	
	/**
	 * Simple class to allow us to pass or return many elements
	 * from methods
	 */
	static class Bag<T> {
		public final T[] elements;
		
		@SafeVarargs
		private Bag(T...elements) {
			this.elements = elements;
		}
		
		public T get(int index) {
			return elements[index];
		}
		
		@SafeVarargs
		public static <T> Bag<T> of(T...elements) {
			return new Bag<>(elements);
		} 
	}
	
	static interface Maths {
		static Set<Long> PRIMES_CACHE = new ConcurrentSkipListSet<>();
		
		/* Probability of generating unique elements reduces after each draw,
		 * which is modelled (for k draws in a probability space of cardinality N)
		 * by : (N-1)!/(N^k * (N-(k-1))!)
		 * Using taylor series expansions of e^x we can get the following approximative
		 * formula: e^(-k*(k-1)/(2*N))
		 * 
		 * Refs: https://preshing.com/20110504/hash-collision-probabilities/
		 */
		static double probabilityOfDistinct(double n, double k) {
			return Math.exp(k*(k-1)/(2*n));
		}
		
		static double probabilityOfDuplicate(double n, double k) {
			double x = k*(k-1)/(2*n);
			if (x < 1/10)
				return x;
			return 1 - probabilityOfDistinct(n, k);
		}
		
		/* Given a probability p of random numbers drawn from a uniform distribution
		 * of [1, n], we can estimate the number of draws required to get a duplicate
		 * by : sqrt(2 . n . ln(1/(1-p)))
		 */
		static double drawsUntilDuplicate(double p, int n) {
			return Math.sqrt(2*n*Math.log(1/(1-p)));
		}
		
		static boolean isPowerOfTwo(long x) {
		    return (x != 0) && ((x & (x - 1)) == 0);
		}
		
		static boolean isMerseenePrime(long n) {
			return isPrime(n) && isPowerOfTwo(n+1);
		}
		
		/* Steins algorithm for gcd */
		static long gcd(long n1, long n2) {
			if (n1 == 0)
				return n2;
			if (n2 == 0)
				return n1;
			int n;
			for (n = 0; ((n1 | n2) & 1) == 0; n++) {
				n1 >>= 1;
				n2 >>= 1;
			}
			while ((n1 & 1) == 0)
				n1 >>= 1;
			do {
				while ((n2 & 1) == 0)
					n2 >>= 1;
				if (n1 > n2) {
					long temp = n1;
					n1 = n2;
					n2 = temp;
				}
				n2 = (n2 - n1);
			} while (n2 != 0);
			return n1 << n;
		}
		
		static boolean isPrime(long n) {
			if (PRIMES_CACHE.contains(n))
				return true;
		    if (n == 2 || n == 3)
		        return true;
		    if (n <= 1 || n % 2 == 0 || n % 3 == 0)
		        return false;
		    for (long i = 5; i * i <= n; i += 6)
		        if (n % i == 0 || n % (i + 2) == 0)
		            return false;
		    PRIMES_CACHE.add(n);
		    return true;			
		}
		
		static class PrimesIterator implements Iterator<Long> {
			private long next, end;

			public PrimesIterator(long start, long end) {
				if (start <= 1)
					throw new IllegalArgumentException("Start can't be negative");
				this.end = end;
				next = start;
			}
			
			public PrimesIterator(long until) {
				this(2, until);
			}
			
			@Override
			public boolean hasNext() {
				return next < end;
			}

			@Override
			public Long next() {
				long current = next;
				next = current+1;
				while(!isPrime(next))
					next = (next & 1) == 0 ? next+1 : next+2;
				return current;
			}
		}
	}
	
	static interface Time {		
		static enum Precision {
			MS(1000, "ms"),
			SEC(60, "s"),
			MIN(60, "m"),
			HOUR(24, "h"),
			DAY(365, "d"),
			YEAR(10, "y"),
			DECADE(100, "de");
			
			private final Dimension dimension;
			
			Precision(long bound, String shorthand) {
				dimension = new Dimension(bound, shorthand);
			}
			
			public String getShorthand() {
				return dimension.shorthand;
			}
			
			public long getBound() {
				return dimension.bound;
			}
			
			public int getFlag() {
				return dimension.flag;
			}
			
			public long getFactor() {
				return dimension.factor;
			}
			
			@Override
			public String toString() {
				return String.format(Dimension.PRINT_FORMAT, getShorthand(), getBound(), getFactor(), getFlag());
			}
			
			static int of(Precision...precisions) {
				int flags = 0;
				for (Precision precision : precisions)
					flags = flags | precision.getFlag();
				return flags;
			}
			
			static Precision[] from(int flags) {
				List<Precision> precisions = new ArrayList<>();
				Precision[] values = values();
				for (int i=values.length-1; i>=0; i--)		// iterate from biggest to smallest
					if ((flags & values[i].getFlag()) != 0)
						precisions.add(values[i]);
				return precisions.toArray(new Precision[0]);
			}
			
			private static final class Dimension {
				private static final String PRINT_FORMAT = "%s(Bound=%d|Factor=%d|Flag=%d)";
				private static int COUNT = 0;
				private static long FACTOR = 1;
				
				public final int flag;
				public final long bound, factor;
				public final String shorthand;
				
				private Dimension(long bound, String shorthand) {
					this.bound = bound;
					this.shorthand = shorthand;
					factor = FACTOR;
					FACTOR = FACTOR * bound;
					flag = (int) Math.pow(2, COUNT++);
				}
			}
		}

		static String fromMillis(int flags, long millis) {
			StringBuilder sb = new StringBuilder();
			long value;
			for (Precision precision : Precision.from(flags))
				if ((flags & precision.getFlag()) != 0 && 
						(value = (millis / precision.getFactor()) % precision.getBound()) != 0)
					sb.append(value + precision.getShorthand() + " ");
			return sb.toString();
		}
		
		static String fromMillis(long millis) {
			return fromMillis(Precision.of(Precision.values()), millis);
		}
	}
}