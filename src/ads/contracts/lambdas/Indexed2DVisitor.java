package ads.contracts.lambdas;

@FunctionalInterface
public interface Indexed2DVisitor<E> {
	void visit(int i, int j, E cell);
}