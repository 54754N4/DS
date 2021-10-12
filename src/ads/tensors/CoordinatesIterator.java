package ads.tensors;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import ads.common.Numbers;

public class CoordinatesIterator implements Iterator<int[]> { 
	private int rank, count, axis, start, max, done;
	private final int[] coords, strides, shape, current;
	private final Predicate<Integer> canShiftDim;
	private final Function<Integer, Integer> peekNext;
	private final Supplier<Integer> moveHigher, moveLower;
	
	public CoordinatesIterator(MemoryLayout layout, int...dimensions) {
		this.shape = dimensions;
		rank = dimensions.length;
		strides = layout.stridesProcessor().apply(dimensions);
		if (layout == MemoryLayout.ROW_MAJOR) {
			start = rank - 1;
			max = 0;
			canShiftDim = (axis) -> axis > max;
			peekNext = (axis) -> axis-1;
			moveHigher = this::decrementAxis;
			moveLower = this::incrementAxis;
		} else {
			start = 0;
			max = rank - 1;
			canShiftDim = (axis) -> axis < max;
			peekNext = (axis) -> axis+1;
			moveHigher = this::incrementAxis;
			moveLower = this::decrementAxis;
		}
		count = 0;
		coords = Numbers.zerosI(rank);
		current = Numbers.zerosI(rank);
		axis = start;
		done = peekNext.apply(max);
	}
	
	@Override
	public boolean hasNext() {
		return peekNext.apply(axis) != done			// didn't reach axis limit 
				|| coords[axis]+1 != shape[axis];	// didn't reach dim limit
	}
	
	@Override
	public int[] next() {
		// Store current coord (to also return 1st coord 0,0,...,0)
		for (int i=0; i<rank; i++)
			current[i] = coords[i];
		// Increment coords algorithm (based on strides)
		if (coords[axis]+1 != shape[axis])
			coords[axis]++;
		else {					
			// Find axis with unfulfilled dimension
			while (canShiftDim.test(axis) && 
					(count+1)%strides[peekNext.apply(axis)] == 0) 
				moveHigher.get();
			if (hasNext()) {
				// Increment current biggest dimension
				coords[axis]++;	
				// Reset previous dimension increments
				while (axis != start && axis != done) 
					coords[moveLower.get()] = 0;
			}
		}
		// Increment count
		count++;
		return current;
	}
	
	/* Helper private methods tied to object so we can increment instance 
	 * variables and not just locally (like an anonymous lambda would do).
	 */
	private final int incrementAxis() { return ++axis; }
	private final int decrementAxis() { return --axis; }
}