package ads.trees;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import ads.contracts.Tree;

public interface AbstractTree<V> extends Tree<V> {
	
	@Override
	default boolean isLeaf() {
		return getChildren().size() == 0;
	}
	
	@Override
	default boolean isRoot() {
		return getParent() == null;
	}
	
	@Override
	default int size() {
		if (isLeaf()) 
			return 1;
		return 1 + getChildren()
				.stream()
				.map(Tree::size)
				.reduce(0, Integer::sum);
	}
	
	@Override
	default int height() {
		if (isLeaf()) 
			return 0;
		return 1 + getChildren()
				.stream()
				.map(Tree::height)
				.max(Integer::compareTo)
				.get();
	}
	
	@Override
	default void forEach(Consumer<Tree<V>> visitor) {
		visitor.accept(this);
		if (isLeaf()) 
			return;
		for (Tree<V> child : getChildren())
			child.forEach(visitor);
	}
	
	@Override
	default <R> void forEach(Function<Tree<V>, R> mapper, BiConsumer<R, Tree<V>> visitor) {
		visitor.accept(mapper.apply(this), this);
		if (isLeaf())
			return;
		for (Tree<V> child : getChildren())
			child.forEach(mapper, visitor);
	}
}