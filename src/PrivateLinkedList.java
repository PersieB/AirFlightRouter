
public class PrivateLinkedList {
	
	private Node head;
	
	public PrivateLinkedList() {;
	}
	public PrivateLinkedList(Node node) {
		this.head = node;
	}
	
	protected static class Node{
		private Airports data;
		private double weight = 0;
		private Node next;
		
		public Node() {
			
		}
		
		public Node( Airports data, double weight) {
			this.data = data;
			this.weight = weight;
		}
		
		public Airports getAirport() {
			return this.data;
		}
		
		public Node getNext() {
			return this.next;
		}
		
		public double getWeight() {
			return this.weight;
		}
		
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public void setAirport(Airports airport) {
			this.data = airport;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
	}
	
	// Add an element to the linked list
	public void addAirport(Airports airport, double weight) {
		Node node = new Node(airport, weight);
		if(this.head == null) {
			this.head = node;
		}
		else {
			Node temp = head;
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = node;
		}
		
	}
	
	// Add an element to the linked list
		public void addAirportNode(Node node) {
			if(this.head == null) {
				this.head = node;
			}
			else {
				Node temp = head;
				while(temp.next != null) {
					temp = temp.next;
				}
				temp.next = node;
			}
			
		}
	
	// This method helps us determine if a particular route belongs to the linked list
	public boolean containsAirport(Airports airport) {
		Node temp = head;
		while(temp != null) {
			if(temp.data.equals(airport)) {return true;}
			temp = temp.next;
		}
		return false;
	}
	
	// This method returns the length of the linked list
	public int lengthList() {
		Node temp = head;
		int count = 0;
		while(temp != null) {
			temp = temp.next;
			count++;
		}
		return count;
	}
	
}
