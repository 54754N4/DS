package ads.crypto;

import java.security.MessageDigest;
import java.util.function.Function;

public class HashBlock<T> {
	private byte[] hash;
	private T data;
	
	public HashBlock(T data, Function<T, byte[]> bytefier, MessageDigest digest) {
		this.data = data;
		hash = digest.digest(bytefier.apply(data));
	}

	public String getHashString() {
		return Hash.bytesToHex(hash);
	}
	
	public byte[] getHash() {
		return hash;
	}

	public HashBlock<T> setHash(byte[] hash) {
		this.hash = hash;
		return this;
	}

	public T getData() {
		return data;
	}

	public HashBlock<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("Block(data=%s, hash=%s)", data, Hash.bytesToHex(hash));
	}
}