package ConnectFour;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node {
	Board board;
	int move;
	double utility = -100;
	double alpha = -100; //highest utility seen so far (lower bound on max value)
	double beta = 100; //lowest utility seen so far (upper bound on min value)
	List<Node> children;
	
	public Node(Board board, int move) {
		this.board = board;
		this.children = new ArrayList<Node>();
		this.move = move;
	}
	public void printTree(Node node) {
		Node current = node;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(current);
		while(queue.isEmpty() == false) {
			current = queue.poll();
			current.board.printBoard();
			queue.addAll(current.children);
		}
	}
	public void addChild(Node node) {
		this.children.add(node);
	}
}
