package ads.contracts;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Tree<V> {
	V getValue();
	Tree<V> setValue(V value);
	Tree<V> getParent();
	Tree<V> setParent(Tree<V> parent);
	List<Tree<V>> getChildren();
	Tree<V> getChild(int i);
	Tree<V> addChild(Tree<V> child);
	Tree<V> removeChild(Tree<V> child);
	int getChildCount();
	
	int size();
	int height();	
	boolean isLeaf();
	boolean isRoot();
	
	void forEach(Consumer<Tree<V>> visitor);
	<R> void forEach(Function<Tree<V>, R> map, BiConsumer<R, Tree<V>> visitor);
	
	default void forEachHeighAware(BiConsumer<Integer, Tree<V>> visitor) {
		forEach(Tree::height, visitor);
	}
	
	default void forEachSizeAware(BiConsumer<Integer, Tree<V>> visitor) {
		forEach(Tree::size, visitor);
	}
	
	default void forEachLeafAware(BiConsumer<Boolean, Tree<V>> visitor) {
		forEach(Tree::isLeaf, visitor);
	}
	
	default void connectChild(Tree<V> child) {
		connect(child, this);
	}
	
	default void connectParent(Tree<V> parent) {
		connect(this, parent);
	}
	
	default void disconnectChild(Tree<V> child) {
		disconnect(child, this);
	}
	
	default void disconnectParent(Tree<V> parent) {
		disconnect(this, parent);
	}
	
	static <V> void connect(Tree<V> child, Tree<V> parent) {
		parent.addChild(child);
		child.setParent(parent);
	}
	
	static <V> void disconnect(Tree<V> child, Tree<V> parent) {
		if (!child.getParent().equals(parent))
			throw new IllegalArgumentException("Not an actual parent");
		parent.removeChild(child);
		child.setParent(null);
	}
	
	static <V> void reconnect(Tree<V> child, Tree<V> newParent) {
		disconnect(child, child.getParent());
		connect(child, newParent);
	}
}