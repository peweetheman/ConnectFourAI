package ConnectFour;

public abstract class PlayerAI {
	Node root = null;
	abstract double utility(Board board);
	abstract int move(Node node);
	abstract int makeTree(Node node);
}
