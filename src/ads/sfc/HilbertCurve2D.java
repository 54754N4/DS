package ads.sfc;

import java.awt.Point;

/**
 * Assumes that 2D square is cut into n by n cells,
 * with (0,0) the lower left corner and (n-1, n-1) the upper right.
 */
public class HilbertCurve2D implements SpaceFillingCurve {
	private final int order;
	private Point point;
	
	public HilbertCurve2D(int order) {
		this.order = order;
		point = new Point(0, 0);
	}
	
	public int length() {
		return order * order - 1;
	}
	
	@Override
	public int mappedDimensions() {
		return 2;
	}

	@Override
	public int offset(int... coords) {
		int x = coords[0], 
			y = coords[1], 
			d = 0;
		for (int rx, ry, s = order/2; s>0; s/=2) {
			rx = (x & s) > 0 ? 1 : 0;
			ry = (y & s) > 0 ? 1 : 0;
			d += s * s * ((3 * rx) ^ ry);
			rot(order, x, y, rx, ry);
			x = point.x;
			y = point.y;
		}
		return d;
	}
	
	@Override
	public int[] coords(int offset) {
		int x = 0, y = 0;
		for (int rx, ry, t=offset, s=1; s<order; s*=2) {
			rx = 1 & (t/2);
			ry = 1 & (t ^ rx);
			rot(s, x, y, rx, ry);
			x = point.x;
			y = point.y;
			x += s * rx;
			y += s * ry;
			t /= 4;
		}
		return new int[] {x, y};
	}
	
	// Rotate/flip quadrant 90 degrees appropriately
	private void rot(int n, int x, int y, int rx, int ry) {
		if (ry == 0) {
			if (rx == 1) {
				x = n-1 - x;
				y = n-1 - y;
			}
			// Swap x & y
			int t = x;
			x = y;
			y = t;
		}
		// Return changed or unchanged
		point.setLocation(x, y);
	}

	
	public static void main(String[] args) {
		HilbertCurve2D c = new HilbertCurve2D(2);
		System.out.println(c.length());
	}
}
