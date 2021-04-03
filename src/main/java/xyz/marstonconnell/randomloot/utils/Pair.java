package xyz.marstonconnell.randomloot.utils;

/**
 * A pair of two types.
 * 
 * @author marston connell
 *
 * @param <L>
 * @param <R>		// TODO Auto-generated method stub

 */
public class Pair<L, R> {
	L left;
	R right;
	public Pair(L leftIn, R rightIn) {
		this.left = leftIn;
		this.right = rightIn;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
	public void setLeft(L leftIn) {
		left = leftIn;
	}
	
	public void setRight(R rightIn) {
		right = rightIn;
	}
	
	@Override
	public String toString() {
		return "< " + left + " , " + right + " >";
	}
	
}
