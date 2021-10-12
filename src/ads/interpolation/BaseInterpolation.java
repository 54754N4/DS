package ads.interpolation;

import ads.contracts.Interpolation;

public abstract class BaseInterpolation<V> implements Interpolation<V> {
	private double[][] data;
	
	public BaseInterpolation(double[][] data) {
		setPoints(data);
	}

	@Override
	public void setPoints(double[][] data) {
		this.data = data;
	}

	@Override
	public int points() {
		return data.length;
	}

	@Override
	public double[] point(int index) {
		return data[index];
	}

	@Override
	public double x(int index) {
		return data[index][0];
	}

	@Override
	public double y(int index) {
		return data[index][1];
	}
	
}
