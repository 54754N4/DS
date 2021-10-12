package ads.tensors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import ads.contracts.Tensor;

public class SparseTensor extends AbstractTensor {
	private Map<Coordinate, Double> memory;
	
	public SparseTensor(int[] shape, double... data) {
		super(MemoryLayout.SPARSE, shape);
		memory = new ConcurrentHashMap<>();
	}
	
	@Override
	public double get(int... indices) {
		Coordinate c = searchByCoords(indices);
		return c == null ? 0 : memory.get(c);
	}
	
	@Override
	public Tensor set(double value, int... indices) {
		Coordinate c = searchByCoords(indices);
		if (c == null) 
			c = createCoords(indices);
		if (value != 0)
			memory.put(c, value);
		return this;
	}
	
	@Override
	public double[] memory() {
		int count = count();
		final double[] memory = new double[count];
		Coordinate coordinate;
		for (int i=0; i<count; i++) {
			coordinate = searchByOffset(i);
			memory[i] = coordinate == null ? 0d : this.memory.get(coordinate);
		}
		return memory;
	}
	
	@Override
	public Tensor elementWiseTransformi(Function<Double, Double> map) {
		memory.forEach((coord, val) -> memory.put(coord, map.apply(val)));
		return this;
	}
	
	@Override
	public Tensor elementWiseIndexedTransformi(BiFunction<int[], Double, Double> map) {
		memory.forEach((coord, val) -> memory.put(coord, map.apply(coord.coords, val)));
		return this;
	}
	
	@Override
	public Tensor elementWiseBiTransformi(BiFunction<Double, Double, Double> map, Tensor t) {
		memory.forEach((coord, val) -> memory.put(coord, map.apply(val, t.get(coord.coords))));
		return this;
	}

	@Override
	public Tensor elementWiseIndexedBiTransformi(TriFunction<int[], Double, Double, Double> map, Tensor t) {
		memory.forEach((coord, val) -> memory.put(coord, map.apply(coord.coords, val, t.get(coord.coords))));
		return this;
	}
	
	/* Coordinates cache management */
	
	private Coordinate createCoords(int...coords) {
		Coordinate c = Coordinate.CACHE.stream()
				.filter(coord -> Arrays.equals(coord.coords, coords))
				.findFirst()
				.orElse(null);
		return c == null ? new Coordinate(shape(), coords) : c;
	} 

	private Coordinate searchByCoords(int...coords) {
		Coordinate result = memory.keySet()
				.stream()
				.filter(c -> Arrays.equals(c.coords, coords))
				.findFirst()
				.orElse(null);
		return result != null ? 	// fallback to cache in case you can't find
				result : 
				Coordinate.CACHE.stream()
				.filter(c -> Arrays.equals(c.coords, coords))
				.findFirst()
				.orElse(null); 
	}
	
	private Coordinate searchByOffset(int offset) {
		Coordinate result = memory.keySet()
				.stream()
				.filter(c -> c.offset == offset)
				.findFirst()
				.orElse(null);
		return result != null ? 
				result :
				Coordinate.CACHE.stream()
				.filter(c -> c.offset == offset)
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * I had no choice but to go with a custom coordinate class
	 * since Map<int[], ?> would return the same hashcode IFF
	 * it's the same array pointer and not based on their contents.
	 * At least with caching, many tensors should be able to reuse 
	 * the same coordinate instances taken from the cache if they
	 * can avoid it (thanks to the SparseTensor::createCoords method).
	 * */
	private static final class Coordinate {
		public static final Comparator<Coordinate> COMPARATOR = new Comparator<>() {
			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				return Arrays.compare(o1.coords, o2.coords);
			}
		};
		public static final Set<Coordinate> CACHE = new ConcurrentSkipListSet<>(COMPARATOR);
		
		private final int[] coords;
		private final int offset;
		
		private Coordinate(int[] shape, int...coords) {
			this.coords = coords;
			offset = MemoryLayout.SPARSE.offsetProcessor().apply(shape, coords);
			CACHE.add(this);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(coords);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (!Arrays.equals(coords, other.coords))
				return false;
			return true;
		}
	}
}
