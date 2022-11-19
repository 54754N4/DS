package ads.bool.model;

// https://en.wikipedia.org/wiki/Gray_code#Converting_to_and_from_Gray_code
public interface Convert {
	static int toGrayCode(int num) {
		return num ^ (num >> 1);
	}
	
	static int fromGrayCode(int num) {
		num ^= num >> 16;
	    num ^= num >>  8;
	    num ^= num >>  4;
	    num ^= num >>  2;
	    num ^= num >>  1;
	    return num;
	}
	
	static String toBinary(int padLength, int num) {
		String binary = Integer.toBinaryString(num); 
		if (padLength == -1 || binary.length() >= padLength)
			return binary;
		StringBuilder sb = new StringBuilder(binary);
		while (sb.length() < padLength)
			sb.insert(0, "0");
		return sb.toString();
	}
	
	static String toBinary(int num) {
		return toBinary(-1, num);
	}
}
