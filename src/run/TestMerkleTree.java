package run;

import java.util.Arrays;

import ads.crypto.Hash.Digest;
import ads.crypto.HashBlock;
import ads.crypto.HashList;
import ads.crypto.MerkleTree;

public class TestMerkleTree {
	public static void main(String[] args) {
//		testHashLists();
		testMerkleTree();
	}
	
	public static void testHashLists() {
		HashList<Integer> list = new HashList.Builder<Integer>()
				.setBytefier(i -> new byte[] { i.byteValue() })
				.setDigest(Digest.MD5)
				.build();
		System.out.println(list.size());
		list.add(0);
		list.add(0);
		list.add(1);
		list.add(2);
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.contains("93b885adfe0da089cdf634904fd59f71"));
		System.out.println(list.contains(0));
		System.out.println(list.indexOf(0));
		System.out.println(list.indexOf(1, 0));
		for (HashBlock<Integer> block : list) {
			System.out.println(block.getHashString());
			System.out.println(Arrays.toString(block.getHash()));
		}
		System.out.println(list.contains(new byte[] {-109, -72, -123, -83, -2, 13, -96, -119, -51, -10, 52, -112, 79, -43, -97, 113}));
		System.out.println(list.getRootString());
	}
	
	public static void testMerkleTree() {
		MerkleTree<Integer> list = new MerkleTree.Builder<Integer>()
				.setBytefier(i -> new byte[] { i.byteValue() })
				.setDigest(Digest.MD5)
				.build();
		System.out.println(list.size());
		list.add(0);
		list.add(0);
		list.add(1);
		list.add(2);
		System.out.println(list.getRootNode());
	}
}
