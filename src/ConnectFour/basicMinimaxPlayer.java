package ConnectFour;

import java.util.ArrayList;

public class basicMinimaxPlayer extends PlayerAI{
	
	
	public double minimax(Node node){        //create tree of all moves with node at the root, alpha beta as parameters
		double maxminUtility = 0;
		if(node.board.getxMove() == 1) {
			maxminUtility = -100;
		}
		else {
			maxminUtility = 100;
		}
		if(node.board.isTerminal(node.board) != 0) { //TERMINAL STATE
			node.utility = utility(node.board);
			return node.utility;
		}
		
		ArrayList<Integer> actions = node.board.actions(node.board);
		for(int action : actions) {
			Board possibleState = node.board.result(action, node.board);
			Node child = new Node(possibleState, action);
			node.addChild(child);
			
			double value = minimax(child);
			
			if(node.board.getxMove() == 1) { //MAXIMUM among all actions
				if(value > maxminUtility) {
					maxminUtility = value;
					node.utility = value;
				}	
			}
						
			if(node.board.getxMove() == 2) { //MINIMUM AMONG ALL ACTIONS
				if(value < maxminUtility) {
					maxminUtility = value;
					node.utility = value;
				}
			}
		}
		return maxminUtility;
	}

	
	
	public double utility(Board board) {
		int value = board.isTerminal(board);
		if(value !=0) {
			if(value == 1) {
				return 1;
			}
			if(value == 2) {
				return -1;
			}
			if(value == 3) { //ITS A TIE!!!
				return 0;
			}
		}
		return 0;
	}



	@Override
	public int move(Node node)
	{
		double maxminUtility = 0;
		if(node.board.getxMove() == 1) {
			maxminUtility = -100;
		}
		else {
			maxminUtility = 100;
		}
		int bestMove = 0;
		System.out.println("So here are my options:");
		for(Node child : node.children) {
			System.out.println("If i take move: " + child.move);
			System.out.println("Then the game value: " + child.utility);
			if(node.board.getxMove() == 1) {
				if(child.utility > maxminUtility) {
					maxminUtility = child.utility;
					bestMove = child.move;
				}		
			}
			if(node.board.getxMove() == 2) {
				if(child.utility < maxminUtility) {
					maxminUtility = child.utility;
					bestMove = child.move;
				}
			}
		}
		return bestMove;
	}

	@Override
	int makeTree(Node node) {
		minimax(node);
		return 1;
	}
}
