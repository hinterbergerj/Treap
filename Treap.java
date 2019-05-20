public class Treap <K1 extends Comparable<K1>,K2 extends Comparable<K2>> {
	
	private class Pair {	// pair class
		
    	K1 first;
    	K2 second;
    	Pair left, right;
	
    	public Pair(K1 first, K2 second, Pair left, Pair right) {	// pair constructor
    		this.first = first;
    		this.second = second;
    		this.left = left;
    		this.right = right;
    	}
    	
    	public Pair(K1 first, K2 second) {	// pair constructor without children
    		this(first, second, null, null);
    	}
    	
    	public String toString() { 					// to string
    		return "(" + first + "," + second + ")"; 
			}
  
	}
	
	public Pair root;	// instantiate root
	
	public String toString(Pair p, int i) {		// toString method for testing purposes
		if (p == null) {
			return "";
		}
		else {
			return toString(p.right, i+2) + String.format("%" + i + "s%s", "", p) + toString(p.left, i+2);	// prints in format of a tree
		}
	}
	
	private Pair deleteMin(Pair p) {		// deleteMin recursive method
        if (p.left == null) return p.right;
        p.left = deleteMin(p.left);
        return p;
    }

    public void deleteMin() {		// deleteMin method used in deleteMax
        root = deleteMin(root); 
    }
	
	private Pair rotL(Pair p) {		// left rotation
		Pair Rtemp = p.right;
		p.right = Rtemp.left;
		Rtemp.left = p;
		return Rtemp;
	}
	
	private Pair rotR(Pair p) {		// right rotation
		Pair Ltemp = p.left;
		p.left = Ltemp.right;
		Ltemp.right = p;
		return Ltemp;
	}
	
	private Pair put(Pair p, K1 key1, K2 key2) {		// recursive put method
        if (p == null) 
        	return new Pair(key1, key2);
        int cmp = key1.compareTo(p.first);		// compare first key
        if (cmp < 0) { 
        	p.left  = put(p.left,  key1, key2);
        	if(p.left.second.compareTo(p.second) > 0) {		// if heap property is invalid
        		p = rotR(p);								// rotate right
        	}
        }
        else if (cmp > 0) {
        	p.right = put(p.right, key1, key2);
        	if (p.right.second.compareTo(p.second) > 0) {	// if heap property is invalid
        		p = rotL(p);								// rotate left
        	}
        }
        else { 
        	p.second = key2;	// if key is the same, replace the second key
        }
        return p;
    }

    public void put(K1 key1, K2 key2) {		// put method calls recursive put
        root = put(root, key1, key2);
    }
	
	public K2 get(K1 key1) {		// general get method
		if(root == null) {		// if tree is empty
			return null;
		}
		else {
			return get(root, key1);		// recursive call
		}
	}
	
	private K2 get(Pair p, K1 key1) {
		int cmp = p.first.compareTo(key1);		// set comparison
		if(cmp == 0) {		// if K1 of p = key1
			return p.second;	// return its key2
		}
		else if (cmp > 0) {		// if K1 of p > key1
			if(p.right != null) {	// and right sub tree is not empty
				return get(p.right, key1);	// search right subtree for key1
			}
		}
		else if (cmp < 0) {		// if K1 of p < key1
			if(p.left != null) {	// and left sub tree is not empty
				return get(p.left, key1);	// search left subtree for key1
			}
		}
		return null;	// if not found
	}
	
	public Pair min(Pair p) {	// find min used in removeMax
		  if(p == null) {
		   return null;
		  }
		  if(p.left == null) {	// leftmost node
		   return p;
		  }
		  return min(p.left);
		 }
	
	public Pair removeMax() {	// find and remove maximum value of key2
		return removeMax(root);
	}
	
	// write a sink
	
	// cannot get removeMax to work properly
	private Pair removeMax(Pair p) {	// find and remove maximum value of key2
		Pair max = root;	// root should be maximum value if put is restoring the heap property correctly
		if (root == null) {
			return null;
		}
		else if (root.right == null) {	// if right tree is empty, you're done
			root = root.left;
		}
		else if (root.left == null) {
			root = root.right;
		}
		else if (root.left != null && root.right != null) {	// if root has 2 children
			root = min(root.right);	// replace root with minimum of right subtree
			root.right = deleteMin(max.right);
			root.left = max.left;
			root = sink(root);
			}
		return max;	// if root is bigger or left tree is null, heap property is correct, return max
	}
	
	// compare roots key2 to key2 of the larger of the two children
	// rotate if smaller
	private Pair sink(Pair p) {		// sink according to heap property
		if (p.left == null) {
			if (p.right == null) {
				return p;
			}
			else if(p.right.second.compareTo(p.second) > 0) {
        		p = rotL(p);
        		p.left = sink(p.left);
        	}
		}
		else if (p.right == null) {
				if(p.left.second.compareTo(p.second) > 0) {
				p = rotR(p);
				p.right = sink(p.right);
				}
		}
		else {
				if(p.left.second.compareTo(p.right.second) > 0) {
					if (p.left.second.compareTo(p.second) > 0) {
						p = rotR(p);
						p.right = sink(p.right);
					}
				}
				else if (p.right.second.compareTo(p.second) > 0) {
						p = rotL(p);
						p.left = sink(p.left);
					}
				}
		return p;
	}
	
}