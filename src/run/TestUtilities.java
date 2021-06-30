package run;

import ads.common.Utils;
import ads.common.Utils.Maths;
import ads.common.Utils.Maths.PrimesIterator;
import ads.common.Utils.Time;

@SuppressWarnings("unused")
public class TestUtilities {
	public static void main(String[] args) {
//		testProbabilities();
//		testTimeConversion();
//		testPrimes();
		System.out.println(Utils.Maths.gcd(30, 35));
	}

	private static void testProbabilities() {
		System.out.println(Maths.probabilityOfDuplicate(Math.pow(2, 32), 1000));
		System.out.println(Maths.drawsUntilDuplicate(0.5, 100));
	}
	
	private static void testTimeConversion() {
		System.out.println(Time.fromMillis(42042042042l));
		System.out.println(Time.fromMillis(Long.MAX_VALUE));
	}
	
	private static void testPrimes() {
		long max = (long) Math.pow(10, 9);
		PrimesIterator i = new PrimesIterator(max);
		long c = 0, duration = System.currentTimeMillis();
		while (i.hasNext()) {
			i.next();
			c++;
		}
		duration = System.currentTimeMillis() - duration;
		System.out.printf("Took %s to find the first %d primes under %d%n", 
				Time.fromMillis(duration),
				c, 
				max);
	}
}
