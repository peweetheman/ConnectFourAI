package ConnectFour;
import java.util.ArrayList;
import java.util.Scanner;
public class Game {

	Scanner scan = new Scanner(System.in);
	Board board = new Board(1,1,1);
	PlayerAI AIplayer;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean play = true;
		while(play == true) {
			Game game = new Game();
			boolean turn = game.intro();
			int isTerminal =0;
			while(isTerminal == 0) {
				if(turn) {
					game.playerMove();
				}
				else {
					game.AImove();
				}
				turn = !turn;
				isTerminal = game.board.isTerminal(game.board);
			}
			play = endGame(isTerminal);
		}	
	}
	
	
	private void AImove() {
		System.out.println("The computer is thinking....");
		int play = AIplayer.move(AIplayer.root);
		board = board.move(play, board);
		for(Node child : AIplayer.root.children) {
			if(child.move == play) {
				AIplayer.root = child;
			}
		}
		board.printBoard();
		
	}

	private void playerMove() {
		System.out.print("What column would you like to play in: ");
		int play =scan.nextInt();
		board = board.move(play, board);
		for(Node child : AIplayer.root.children) {
			if(child.move == play) {
				AIplayer.root = child;
			}
		}
		board.printBoard();
	}

	private static boolean endGame(int isTerminal) {
		Scanner scan = new Scanner(System.in);
		if(isTerminal == 1) {
			System.out.println("But the X's have won!");
		}
		else if(isTerminal == 2) {
			System.out.println("But the O's have won!");
		}
		else {
			System.out.println("BUT WOW ITS A TIE!");
		}
		System.out.println("Enter 1 to play again!");
		if(scan.nextInt() != 1) {
			return false;
		}
		return true;
	}


	public boolean intro() {
		boolean turnbool;
		Scanner scan = new Scanner(System.in);
		System.out.println("Prepare to be destroyed by the4 Connect Four Rampaging Monster AI");
		System.out.println("NO CONNECT FOUR PLAYERS SHALL PASS!");
		System.out.println();
		System.out.println("Which game would you like to play? (Enter integer 1-5)");
		System.out.println("1. 2x2x2 Demo Board");
		System.out.println("2. 3x3x3 Tiny Connect-Three Board");
		System.out.println("3. 4x4x4 Medium Connect-Four Board");
		System.out.println("4. 6x7x4 Regular Connect-Four Board");
		System.out.println("5. Test the limits with 10x10x5 Supreme Connect-Four Board");

		int game = scan.nextInt();
		System.out.println("Who would you like to play against? (Enter integer 1-4)");
		System.out.println("1. A totally random player (we all gotta feel like winners sometimes)");
		System.out.println("2. Basic minimax algorithm player (this may not be able to compute tree initially on many boards)");
		System.out.println("3. A minimax player with alpha-beta pruning (this may not be able to compute tree on larger boards)");
		System.out.println("4. A heuristic minimax player with alpha-beta pruning (this may crush you and make you give up on life)");


		int player = scan.nextInt();
		System.out.println("Enter 1 to go first or 2 to go second: ");
		int turn = scan.nextInt();
		if(game == 1) {
			board = new Board(2,2,2);
		}
		else if(game == 2) {
			board = new Board(3, 3, 3);
		}
		else if(game == 3) {
			board = new Board(4,4,4);
		}
		else if(game == 4) {
			board = new Board(6, 7, 4);
		}
		else if(game == 5) {
			board = new Board(10,10,5);
		}
		else {
			System.out.println("cmon bro, restart it you know what you did");
		}
		if(player == 1) {
			AIplayer = new randomPlayer();
		}
		else if( player == 2) {
			AIplayer = new basicMinimaxPlayer();
		}
		else if(player == 3) {
			AIplayer = new minimaxAlphaBeta();
		}
		else if(player == 4) {
			AIplayer = new HminimaxPlayer();
		}
		else {
			System.out.println("cmon bro, restart it you know what you did");
		}
		AIplayer.root = new Node(board, -1);
		AIplayer.makeTree(AIplayer.root);
		board.printBoard();	
		//AIplayer.root.printTree(AIplayer.root);
		if(turn == 1) {
			turnbool = true;
		}
		else {
			turnbool = false;
		}
		return turnbool;
	}
	
}
