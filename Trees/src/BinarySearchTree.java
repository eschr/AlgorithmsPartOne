

public class BinarySearchTree<Key extends Comparable<Key>, Val> {
	
	private Node root;
	
	private class Node {
		private Key key;
		private Val value;
		private Node left;
		private Node right;
		
		public Node(Key key, Val val){
			this.key = key;
			value = val;
		}
		
	}
	
	public void put(Key key, Val val) {
		if (key == null) throw new NullPointerException();
		if (val == null) throw new NullPointerException();
		
		root = put(root, key, val);
	}
	
	private Node put(Node x, Key key, Val val) {
		if (x == null) return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if (cmp < 0) 
			x.left = put(x.left, key, val);
		else if (cmp > 0) 
			x.right = put(x.right, key, val);
		return x;
	}
	
	public Val get(Key key) {
		return get(root, key);
	}
	
	private Val get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0) return get(x.left, key);
		if (cmp > 0) return get(x.right, key);
		else return x.value;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
		

	}

}
