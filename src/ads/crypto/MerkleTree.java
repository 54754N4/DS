package ads.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

import ads.crypto.Hash.Digest;

public class MerkleTree<T> extends HashList<T> {
	private HashNode rootNode;
	
	public MerkleTree(Digest digest, Function<T, byte[]> bytefier) {
		super(digest, bytefier);
	}
	
	/* Accessors */
	
	public HashNode getRootNode() {
		return rootNode;
	}
	
	@Override
	public byte[] getRoot() {
		return rootNode.getHash();
	}
	
	@Override
	public String getRootString() {
		return rootNode.getHashString();
	}
	
	@Override
	protected void computeRoot() {
		if (size() == 0)
			return;
		Queue<HashNode> nodes = new LinkedList<>(), 
				merged = new LinkedList<>();
		for (int i=0; i<size(); i++)
			nodes.add(new HashNode(String.valueOf(i), get(i).getHash(), getDigest()));
		HashNode node, next;
		while (merged.size() != 1) {
			merged = new LinkedList<>();
			while (nodes.size() != 0) { 
				node = nodes.poll();
				next = nodes.poll();
				if (next == null) 	// odd number of elements - https://bitcoin.stackexchange.com/a/79367/135766
					next = node.clone();
				merged.add(new HashNode(node, next, getDigest()));
			}
			nodes = merged;
		}
		rootNode = merged.poll();
	}
	
	public static class HashNode implements Cloneable {
		private final MessageDigest digest;
		private final String name, count;
		private final byte[] hash;
		private final HashNode left, right;
		
		/** Leaf constructor */
		public HashNode(String count, byte[] hash, MessageDigest digest) {
			this(count, hash, null, null, digest);
		}
		
		/** Intermediate merged nodes */
		public HashNode(HashNode left, HashNode right, MessageDigest digest) {
			this(left.count + right.count, merge(left.hash, right.hash, digest), left, right, digest);
		}
		
		private HashNode(String count, byte[] hash, HashNode left, HashNode right, MessageDigest digest) {
			this.count = count;
			this.hash = hash;
			this.left = left;
			this.right = right;
			this.digest = digest;
			name = "HashNode"+count;
		}
		
		/* Accessors */
		
		public String getName() {
			return name;
		}
		
		public byte[] getHash() {
			return hash;
		}
		
		public String getHashString() {
			return Hash.bytesToHex(hash);
		}
		
		public HashNode getLeft() {
			return left;
		}
		
		public HashNode getRight() {
			return right;
		}
		
		public MessageDigest getDigest() {
			return digest;
		}
		
		/* Tree methods */
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public int height() {
			if (isLeaf())
				return 0;
			return 1 + Math.max(left.height(), right.height());
		}
		
		public int size() {
			if (isLeaf())
				return 1;
			return left.size() + 1 + right.size();
		}
		
		/* Duplication/Cloning */
		
		@Override
		public HashNode clone() {
			return new HashNode(count, Arrays.copyOf(hash, hash.length), left, right, digest);
		}
		
		/* Convenience/Helper methods */
		
		private static byte[] merge(byte[] h1, byte[] h2, MessageDigest digest) {
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				baos.write(h1);
				baos.write(h2);
				return digest.digest(baos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public String toString() {
			return String.format("HashNode(name=%s, hash=%s, left=%s, right=%s)", name, Hash.bytesToHex(hash), left, right);
		}
	}
	
	public static class Builder<T> {
		private Digest digest;
		private Function<T, byte[]> bytefier;
		
		public Digest getDigest() {
			return digest;
		}
		
		public Builder<T> setDigest(Digest digest) {
			this.digest = digest;
			return this;
		}
		
		public Function<T, byte[]> getBytefier() {
			return bytefier;
		}
		
		public Builder<T> setBytefier(Function<T, byte[]> bytefier) {
			this.bytefier = bytefier;
			return this;
		}
		
		public MerkleTree<T> build() {
			if (digest == null)
				throw new IllegalArgumentException("No digest/hashing algorithm specified");
			if (bytefier == null)
				throw new IllegalArgumentException("No byte conversion function defined");
			return new MerkleTree<>(digest, bytefier);
		}
	}
}
