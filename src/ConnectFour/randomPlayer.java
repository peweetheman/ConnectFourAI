package ConnectFour;

public class randomPlayer extends PlayerAI{

	@Override
	public double utility(Board board) {
		return 0;
	}
	
	@Override
	public int move(Node node) {
		while(true) {
			if(node.board.applicable((int) (Math.random() * (node.board.getColumns())), node.board.getState())) {
				return (int) (Math.random() * (node.board.getColumns()));
			}
		}
	}

	@Override
	int makeTree(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}

}
