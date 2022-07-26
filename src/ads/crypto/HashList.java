package ads.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import ads.crypto.Hash.Digest;

public class HashList<T> implements Iterable<HashBlock<T>> {
	public static final int NOT_FOUND = -1;
	
	private final BiPredicate<? super HashBlock<T>, T> predicateData;
	private final BiPredicate<? super HashBlock<T>, byte[]> predicateBytes;
	private final BiPredicate<? super HashBlock<T>, String> predicateString;
	
	private MessageDigest digest;
	private final Function<T, byte[]> bytefier;
	
	private byte[] root;
	private List<HashBlock<T>> list;
	
	public HashList(Digest digest, Function<T, byte[]> bytefier) {
		this.bytefier = bytefier;
		try {
			this.digest = MessageDigest.getInstance(digest.toString());
		} catch (NoSuchAlgorithmException e) {
			// should not be reached since digest enum restricts it
			e.printStackTrace();
		}
		list = Collections.synchronizedList(new ArrayList<>());
		predicateData = (block, data) -> block.getData().equals(data);
		predicateBytes = (block, hash) -> Arrays.equals(block.getHash(), hash);
		predicateString = (block, hash) -> block.getHashString().equals(hash);
	}
	
	protected void computeRoot() {
		if (size() == 0)
			return;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			for (HashBlock<T> block : list)
				baos.write(digest.digest(bytefier.apply(block.getData())));
			root = digest.digest(baos.toByteArray()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Accessors */
	
	public Function<T, byte[]> getBytefier() {
		return bytefier;
	}
	
	public MessageDigest getDigest() {
		return digest;
	}
	
	public byte[] getRoot() {
		return root;
	}
	
	public String getRootString() {
		return Hash.bytesToHex(root);
	}
	
	public int size() {
		return list.size();
	}
	
	/* Generic methods */
	
	public boolean contains(Predicate<? super HashBlock<T>> predicate) {
		return list.stream().anyMatch(predicate);
	}
	
	public int indexOf(int start, Predicate<? super HashBlock<T>> predicate) {
		if (start < 0 || start > list.size())
			return NOT_FOUND;
		for (int i=start; i<list.size(); i++)
			if (predicate.test(list.get(i)))
				return i;
		return NOT_FOUND;
	}
	
	private <K> K mutableAction(Supplier<K> action) {
		K returned = action.get();
		computeRoot();
		return returned;
	}
	
	/* List methods */
	
	public boolean contains(final T data) {
		return contains(block -> predicateData.test(block, data));
	}
	
	public boolean contains(final byte[] hash) {
		return contains(block -> predicateBytes.test(block, hash));
	}
	
	public boolean contains(final String hash) {
		return contains(block -> predicateString.test(block, hash));
	}
	
	public int indexOf(int start, final T data) {
		return indexOf(start, block -> predicateData.test(block, data));
	}
	
	public int indexOf(int start, final byte[] hash) {
		return indexOf(start, block -> predicateBytes.test(block, hash));
	}
	
	public int indexOf(int start, final String hash) {
		return indexOf(start, block -> predicateString.test(block, hash));
	}
	
	public int indexOf(final T data) {
		return indexOf(0, block -> predicateData.test(block, data));
	}
	
	public int indexOf(final byte[] hash) {
		return indexOf(0, block -> predicateBytes.test(block, hash));
	}
	
	public int indexOf(final String hash) {
		return indexOf(0, block -> predicateString.test(block, hash));
	}
	
	public HashBlock<T> get(int index) {
		return list.get(index);
	}
	
	public HashBlock<T> set(int index, T data) {
		return mutableAction(() -> list.set(index, new HashBlock<>(data, bytefier, digest)));
	}
	
	public boolean add(T data) {
		return mutableAction(() -> list.add(new HashBlock<>(data, bytefier, digest)));
	}
	
	public boolean remove(T data) {
		return mutableAction(() -> list.remove(data));
	}
	
	public HashBlock<T> remove(int index) {
		return mutableAction(() -> list.remove(index));
	}
	
	@Override
	public String toString() {
		return Arrays.deepToString(list.toArray());
	}

	@Override
	public Iterator<HashBlock<T>> iterator() {
		return list.iterator();
	}
	
	public Iterator<T> iteratorData() {
		final Iterator<HashBlock<T>> iterator = iterator();
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return iterator.next().getData();
			}
		};
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
		
		public HashList<T> build() {
			if (digest == null)
				throw new IllegalArgumentException("No digest/hashing algorithm specified");
			if (bytefier == null)
				throw new IllegalArgumentException("No byte conversion function defined");
			return new HashList<>(digest, bytefier);
		}
	}
}