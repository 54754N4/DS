package run;

import java.util.Arrays;

import ads.common.Utils.Arrays.Splitter;

public class TestLists {
	public static void main(String[] args) {
		testSplitter();
	}
	
	public static void testSplitter() {
		Splitter<Integer> splitter = Splitter.split(2, 1,2,3,4,5,6,7,8,9,10);
		System.out.println(splitter.isPerfect());
		while (splitter.hasNext())
			System.out.println(Arrays.toString(splitter.next()));
	}
}
