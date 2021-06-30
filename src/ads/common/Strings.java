package ads.common;

public interface Strings {
	public static String tabs(int count) {
		StringBuilder sb = new StringBuilder();
		while (count-->0)
			sb.append("\t");
		return sb.toString();
	}
}
