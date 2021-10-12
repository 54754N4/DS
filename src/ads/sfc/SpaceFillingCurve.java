package ads.sfc;

public interface SpaceFillingCurve {
	int mappedDimensions();
	int[] coords(int offset);
	int offset(int...coords);
}
