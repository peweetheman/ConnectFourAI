package ConnectFour;

import java.util.ArrayList;

public class HminimaxPlayer extends PlayerAI{
	
	int moveCounter = 0;
	
	public double Hminimax(Node node, int depth, double alpha, double beta){     //create tree of all moves with node at the root, alpha beta as parameters
		double maxminUtility = 0;
		if(node.board.getxMove() == 1) {
			maxminUtility = -1000;
		}
		else if(node.board.getxMove() == 2){
			maxminUtility = 1000;
		}
		
		if(node.board.isTerminal(node.board) != 0) { //TERMINAL STATE
			node.utility = (heuristicUtility(node.board) + utility(node.board));
			return node.utility;
		}
		
		if(depth == 8) {
			node.utility = heuristicUtility(node.board);
			return node.utility;
		}
		
		ArrayList<Integer> actions = node.board.actions(node.board);
		for(int action : actions) {
			Board possibleState = node.board.result(action, node.board);
			Node child = new Node(possibleState, action);
			node.addChild(child);
			
			double value = Hminimax(child, depth+1, alpha, beta);
			
			if(node.board.getxMove() == 1) { //MAXIMUM among all actions
				if(value > maxminUtility) {
					maxminUtility = value;
					node.utility = value;
				}		
				if(maxminUtility>alpha) {
					alpha = maxminUtility; //UPDATE ALPHA ON THE MAXIMUM LEVEL AS IT IS A LOWER BOUND
				}
			}
						
			if(node.board.getxMove() == 2) { //MINIMUM AMONG ALL ACTIONS
				if(value < maxminUtility) {
					maxminUtility = value;
					node.utility = value;
				}
				if(maxminUtility<beta) {
					beta = maxminUtility;  //UPDATE BETA ON THE MIN LEVEL
				}
			}
			if(beta<=alpha) {
				break;
			}
		}
		return maxminUtility;
	}

	

	private double heuristicUtility(Board board) {
		double Hval = 0;
		int columns = board.getColumns();
		int rows = board.getRows();
		
		for(int i = 0; i<rows; i++) {                                  //FAVOR THE MIDDLE
			if(board.getState()[i][columns/2] == 1) {
				Hval = Hval + 1; 
			}
			if(board.getState()[i][columns/2] == 2){
				Hval = Hval - 1; 
			}
			if(board.getState()[i][columns/2 +1] == 1) {
				Hval = Hval + .2; 
			}
			if(board.getState()[i][columns/2 +1] == 2){
				Hval = Hval - .2; 
			}
			if(board.getState()[i][columns/2 -1] == 1) {
				Hval = Hval + .2; 
			}
			if(board.getState()[i][columns/2 -1] == 2){
				Hval = Hval - .2; 
			}
		}
	
		
		for(int i =0; i<rows; i++) { //CHECK COUNT improved version of checking 2's, 3's, n's
			for(int j = 0; j<columns; j++) {
				if(board.getState()[i][j] == 1) {
					Hval = Hval + count(board, 1, board.getWinCondition(), i, j) * 1; 
				}
				if(board.getState()[i][j] == 2){
					Hval = Hval + count(board, 2, board.getWinCondition(), i, j) * -1; 
				}
			}
		}
			
//		
//		for(int i =0; i<rows; i++) { //CHECK TWO's, THREE's, ... winCondition - 1's //Checks the number of two in a row
//			for(int j = 0; j<columns; j++) {
//				for(int z = 2; z<=board.getWinCondition(); z++) {
//					if(board.getState()[i][j] == 1) {
//						Hval = Hval + checkN(board, 1, z, i, j) * z; 
//					}
//					if(board.getState()[i][j] == 2){
//						Hval = Hval + checkN(board, 2, z, i, j) * -z;  or the function
//					}
//				}
//			}
//		}
		return Hval;
	}


	private int checkN(Board board, int oneORtwo, int InARow, int i, int j) {  
		int count = 0;
		if(checkDiagnol1(oneORtwo, board, InARow, i, j)){
			count = count + 1;
		}
		if(checkDiagnol2(oneORtwo, board, InARow, i, j)){
			count = count + 1;
		}
		if(checkHorizontal(oneORtwo, board, InARow, i, j)){
			count = count + 1;
		}
		if(checkVertical(oneORtwo, board, InARow, i, j)){
			count = count + 1;
		}
		return count;
	}
	
	
	private boolean checkDiagnol1(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = numberInARow; k>0; k--) {
			if(((i-k) < 0) || ((j-k) < 0)) {
				return false;
			}
			if(board.getState()[i-k][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkDiagnol2(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = numberInARow; k>0; k--) {
			if(((i+k) >= board.getRows()) || ((j-k) < 0)) {
				return false;
			}
			if(board.getState()[i+k][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}
	private boolean checkHorizontal(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = numberInARow; k>0; k--) {
			if((i < 0) || (j-k < 0)) {
				return false;
			}
			if(board.getState()[i][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}

	private boolean checkVertical(int oneORtwo, Board board, int i, int j,int numberInARow) {
		for(int k = numberInARow; k>0; k--) {
			if((i-k < 0) || (j< 0)) {
				return false;
			}
			if(board.getState()[i-k][j] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}
	
	
	
	private int count(Board board, int oneORtwo, int winCondition, int i, int j) { 
		int count = 0;
		count = countDiagnol2(board, oneORtwo, board.getWinCondition(), i, j)+ countDiagnol1(board, oneORtwo, board.getWinCondition(), i, j) +
				countVertial(board, oneORtwo, board.getWinCondition(), i, j) + countHorizontal(board, oneORtwo, board.getWinCondition(), i, j);
		return count;
	}
	
	
	private int countHorizontal(Board board, int oneORtwo, int winCondition, int i, int j) {
		int val =0;
		for(int k = winCondition; k>0; k--) {
			if((i < 0) || (j-k < 0)) {
				val = 0;
				break;
			}
			else{
				if(board.getState()[i][j-k] == oneORtwo) {
					val++;
				}
				if(board.getState()[i][j-k] != 0 && board.getState()[i][j-k] != oneORtwo) {
					val = 0;
					break;
				}
			}
		}
		return val;
	}
	private int countVertial(Board board, int oneORtwo, int winCondition, int i, int j) {
		int val =0;
		for(int k = winCondition; k>0; k--) {
			if((i-k < 0) || (j< 0)) {
				val = 0;
				break;
			}
			else{
				if(board.getState()[i-k][j] ==oneORtwo) {
					val++;
				}
				if(board.getState()[i-k][j] != 0 && board.getState()[i-k][j] != oneORtwo) {
					val = 0;
					break;
				}
			}
		}
		return val;
	}
	private int countDiagnol1(Board board, int oneORtwo, int winCondition, int i, int j) {
		int val =0;
		for(int k = winCondition; k>0; k--) {
			if(((i-k) < 0) || ((j-k) < 0)) {
				val = 0;
				break;
			}
			else{
				if(board.getState()[i-k][j-k] ==oneORtwo) {
					val++;
				}
				if(board.getState()[i-k][j-k] != 0 && board.getState()[i-k][j-k] != oneORtwo) {
					val = 0;
					break;
				}
			}
		}
		return val;
	}



	private int countDiagnol2(Board board, int oneORtwo, int winCondition, int i, int j) {
		int val =0;
		for(int k = winCondition; k>0; k--) {
			if(((i+k) >= board.getRows()) || ((j-k) < 0)) {
				val = 0;
				break;
			}
			else{
				if(board.getState()[i+k][j-k] == oneORtwo) {
					val++;
				}
				if(board.getState()[i+k][j-k] != 0 && board.getState()[i+k][j-k] != oneORtwo) {
					val = 0;
					break;
				}
			}
		}
		return val;
	}



	public double utility(Board board) {
		int value = board.isTerminal(board);
		if(value !=0) {
			if(value == 1) {
				return 999;
			}
			if(value == 2) {
				return -999;
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
		node.children.clear();
		Hminimax(node, 0, -10000, 10000);
		double maxminUtility = 0;
		if(node.board.getxMove() == 1) {
			maxminUtility = -10000;
		}
		else {
			maxminUtility = 10000;
		}
		int bestMove = -1;
		System.out.println("So here are my options:");
		for(Node child : node.children) {
			System.out.println("If I take move: " + child.move);
			System.out.println("Then i believe the game value is(-1000 to 1000 positive is good for X's): " + child.utility);
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
		Hminimax(node, 0, -10000, 10000);
		return 1;
	}
}
