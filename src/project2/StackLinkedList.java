package project2;

public class StackLinkedList <T extends Comparable<T>> {
	
	// instance vars
	Node top;
	private int sizeLinkedList;

	public static void main(String[] args) {
		StackLinkedList<Integer> st1 = new StackLinkedList<Integer>();
		StackLinkedList<Integer> st2 = new StackLinkedList<Integer>();
		
		st1.push(13);
		st2.push(14);
		StackLinkedList<Boolean> st = new StackLinkedList<Boolean>();
		
		st.compareLists(st1, st2);
		System.out.println(st.compareLists(st2, st1));

	} 
	// ------------------ end of main
	
	public boolean compareLists(StackLinkedList<Integer> ls1, StackLinkedList<Integer> ls2) {
		
		while(!(ls1.isEmpty()) && (!ls2.isEmpty())) {
			
			if(ls1.top.data.compareTo(ls2.top.data) >= 0) {
				return true;
			} else if (ls1.top.data.compareTo(ls2.top.data) <= 0) {
				return false;
			}
			Node top1 = ls1.top.next;
			Node top2 = ls2.top.next;
		}
		return true;
	}
	
//	public Node getBiggerLinkedLs(Node node1, Node node2) {
//		Node top1 = node1;
//		Node top2 = node2;
//		
//		while(node1 != null) {
//			if(node1.data.compareTo(node2.data) > 0) {
//				return top1;
//			} else if (node1.data.compareTo(node2.data) < 0) {
//				return top2;
//			}
//			node1 = node1.next;
//			node2 = node2.next;
//		}
//		return top1;
//	}
	
	public StackLinkedList() {

		this.top = null;
		this.sizeLinkedList = 0;
		
	}
	
	/**
	 * Nested class node
	 *
	 */
	private class Node<T extends Comparable<T>> {
		
		// data might need to be either string or int
		private T data;
//			this.data = passedD;
		private Node next;
	}
	//  ------------------ end of Node class 
	
	/**
	 * push data to the stack one on top the other ones.
	 * @param passData
	 */
	public void push(T passData) {
		
		Node newNode = new Node();
		newNode.data = (Comparable) passData;
		newNode.next = top;
		top = newNode;
		this.sizeLinkedList++;
	}
	
	/**
	 * This method pop data from the top of the stack 
	 * @return removed data
	 */
	public T pop() {
		if (this.top == null) {
			System.out.println("Stack is empty!!");
		}
		// asign the head value to a var
		T value = (T) top.data;
		// asign head to the next value in the stack 
		this.top = top.next;
		this.sizeLinkedList--;
		return value;
	}
	
	public void clearStack() {
		this.top = null;
		this.sizeLinkedList =0;
	}
	
	public int sizeLinkedLs() {
		return this.sizeLinkedList;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	public T peek() {
		if(!(isEmpty())) {
			return (T) top.data;
		} else {
			return null;
		}
	}
	

	
	/**
	 * This method does not return any values. This reverse the order of the stack 
	 */
	public void reverseLinkLsStack() {
		Node prev = null;
		Node curr = this.top;
		while(curr != null) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		this.top = prev;
	}
	
	public String toString() {
		Node temp = top;
		String result = "";
		while(temp !=null) {
			result += temp.data;
			temp = temp.next;
		}
//		result += " null";
		return result;
	}
	// ------------------------------ end of stack methods
	

}
