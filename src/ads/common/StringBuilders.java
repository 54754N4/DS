package ads.common;

public interface StringBuilders {
	static boolean matches(StringBuilder sb, String match, int pos) {
		if (match.length() == 0)
			return sb.length() == 0;
		for (int i=0; i<match.length(); i++)
			if (sb.charAt(pos+i) != match.charAt(i))
				return false;
		return true;
	}
	
	static StringBuilder removeHead(StringBuilder sb, String match) {
		if (!matches(sb, match, 0))
			return sb;
		for (int i=0; i<match.length(); i++)
			sb.deleteCharAt(0);
		return sb;
	}
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(" + 2i + 3k - 3f");
		System.out.println(removeHead(sb, " + "));
	}
}
