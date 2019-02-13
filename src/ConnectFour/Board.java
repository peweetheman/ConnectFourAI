package ConnectFour;

import java.util.ArrayList;

public class Board {
	private int rows;
	private int columns;
	private int winCondition;
	private int[][] state;
	private int xMove = 1; //1 is X's move, 2 is O's move
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getWinCondition() {
		return winCondition;
	}
	public void setWinCondition(int winCondition) {
		this.winCondition = winCondition;
	}
	public int getxMove() {
		return xMove;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	int[][] getState() {
		return state;
	}
	public void setState(int[][] state) {
		this.state = state;
	}
	public int isxMove() {
		return xMove;
	}
	public void setxMove(int xMove) {
		this.xMove = xMove;
	}
	
	public Board(int rows, int columns,int  winCondition) {
		this.rows = rows;
		this.columns = columns;
		this.state = new int[rows][columns];
		this.winCondition = winCondition;
		for(int i = 0; i<rows; i++) {
			for (int j =0; j<columns; j++) {
				state[i][j] =0;
			}
		}
	}
	public Board move(int column, Board board) {
		if(!applicable(column, this.state)) {
			System.out.println("Not a legal move! Try Again...");
			return board;
		}
		board = result(column, board);
		return board;
	}

	public boolean applicable(int column, int[][] state) {
		if(state[0][column]!=0) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Integer> actions(Board board) {
		ArrayList<Integer> possible = new ArrayList<>();
		for(int i = 0; i<columns; i++) {
			if(applicable(i, board.state)) {
				possible.add(i);

			}
		}
		return possible;
	}
	

	public Board result(int column, Board board) { //Gets the result of dropping a piece in a column
		Board newBoard = new Board(board.getRows(), board.getColumns(), board.getWinCondition());
		for(int i = 0; i<rows; i++) {
			for (int j = 0; j< columns; j++){
				newBoard.state[i][j] = board.state[i][j];
			}
		}
		if(!applicable(column, newBoard.state)) {
			System.out.println("RESULT IS NULL?");
			return null;
		}
		for (int i = rows-1; i>=0; i--) {
			if(newBoard.state[i][column]==0) {
				if(board.getxMove() == 1) {
					newBoard.state[i][column] = 1; //1 is the X's
					newBoard.xMove = 2; //change turn
				}
				else {
					newBoard.state[i][column] = 2; //2 is the O's
					newBoard.xMove = 1; //change turn
				}
				return newBoard;
			}
		}
		System.out.println("ERROR");
		return null;
	}
	
	
	
	public int isTerminal(Board board) { //returns 0 if not a terminal state, 1 if X's win, 2 if 0's win, 3 if tie!
		int win = 0;
		for(int i = 0; i<rows; i++) {
			for (int j =0; j<columns; j++) {
				if(board.state[i][j] == 0) {
				}
				else if(board.state[i][j] == 1) {
					win = checkWin(1, board, i, j);
				}
				else if(board.state[i][j] == 2){
					win = checkWin(2, board, i, j);
				}
				if(win!=0) {
					return win;
				}
			}
		}
		try {
			for(int i = 0; i<rows; i++) {
				for (int j =0; j<columns; j++) {
					if(board.state[i][j] == 0) {
						throw new IllegalArgumentException();
					}
				}
			}
			return 3;
			} catch (IllegalArgumentException e) {
		}
		return 0;
	}
	
	private int checkWin(int num, Board board, int i, int j) {
		int val = num;
		if(checkDiagnol1(num,board,i,j, winCondition)) {
			return val;
		}
		else if(checkDiagnol2(num,board,i,j, winCondition)) {
			return val;
		}
		else if(checkHorizontal(num,board,i,j, winCondition)) {
			return val;
		}
		else if(checkVertical(num,board,i,j, winCondition)) {
			return val;
		}
		return 0;
	}
	
	
	private boolean checkDiagnol1(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = 1; k<numberInARow; k++) {
			if(((i-k) < 0) || ((j-k) < 0)) {
				return false;
			}
			if(board.state[i-k][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkDiagnol2(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = 1; k<numberInARow; k++) {
			if(((i+k) >= board.rows) || ((j-k) < 0)) {
				return false;
			}
			if(board.state[i+k][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}
	private boolean checkHorizontal(int oneORtwo, Board board, int i, int j, int numberInARow) {
		for(int k = 1; k<numberInARow; k++) {
			if((i < 0) || (j-k < 0)) {
				return false;
			}
			if(board.state[i][j-k] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}

	private boolean checkVertical(int oneORtwo, Board board, int i, int j,int numberInARow) {
		for(int k = 1; k<numberInARow; k++) {
			if((i-k < 0) || (j< 0)) {
				return false;
			}
			if(board.state[i-k][j] !=oneORtwo) {
				return false;
			}
		}
		return true;
	}

	
	
	
	public void printBoard() {
		System.out.print("  ");
		for(int i = 0; i<columns; i++) {
			System.out.print(i + " ");
		}
		System.out.println("");
		for (int i = 0; i<rows; i++) {
			for(int j = 0; j<columns; j++) {
				if(j == 0) {
					System.out.print(i + " ");
				}
				if(state[i][j] == 1) {
					System.out.print("X ");
				}
				else if(state[i][j] == 2){
					System.out.print("O ");
				}
				else {
					System.out.print("  ");
				}
				if(j == columns-1) {
					System.out.println(i);
				}
			}
		}
		System.out.print("  ");
		for(int i = 0; i<columns; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		if(xMove == 1) {
			System.out.println("It is the X's turn to play");
		}
		else{
			System.out.println("It is the O's turn to play");
		}
	}
}
