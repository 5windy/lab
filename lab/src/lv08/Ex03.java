package lv08;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

class Box {
	public static final int P1 = 1;
	public static final int P2 = 2;

	private int player;
	private boolean isFlipped;
	
	public int getPlayer() {
		return this.player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}
	
	public boolean isFlipped() {
		return this.isFlipped;
	}
	
	public void setFlipped() {
		this.isFlipped = !this.isFlipped;
	}

	@Override
	public String toString() {
		return this.player == 0 ? "[ ]" : (this.player == P1 ? "[O]" : "[X]");
	}
}

class Tictactoe {
	private final int SIZE = 3;

	private Scanner scanner = new Scanner(System.in);

	private ArrayList<Vector<Box>> map;
	private int turn, win, count;

	public Tictactoe() {
		setGame();
	}

	public void run() {
		while (isRun()) {
			printMap();
			play();
		}
		printMap();
		showWinner();
	}

	private void setGame() {
		setMap();
		this.turn = Box.P1;
		this.win = 0;
	}

	private void setMap() {
		this.map = new ArrayList<Vector<Box>>();

		for (int i = 0; i < SIZE; i++) {
			Vector<Box> row = new Vector<>();

			for (int j = 0; j < SIZE; j++)
				row.add(new Box());

			map.add(row);
		}
	}

	private boolean isRun() {
		return this.win == 0 && count < SIZE * SIZE;
	}

	private void printMap() {
		for (Vector<Box> row : map) {
			for (Box box : row)
				System.out.print(box);
			System.out.println();
		}
	}

	private void play() {
		if (marking()) {
			check();
			changeTurn();
		}
	}

	private boolean marking() {
		int yx = inputNumber(String.format("P%d yx", this.turn));

		int y = yx / 10;
		int x = yx % 10;
		
		if(y < 0 || y >= SIZE || x < 0 || x >= SIZE || map.get(y).get(x).isFlipped())
			return false;
		
		Box box = map.get(y).get(x);
		box.setPlayer(this.turn);
		box.setFlipped();
		
		this.count ++;

		return true;
	}

	private int inputNumber(String message) {
		int number = -1;
		try {
			System.out.print(message + " : ");
			String input = scanner.next();
			number = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("숫자를 입력하세요.");
		}
		return number;
	}
	
	private void check() {
		boolean win = false;
		
		win = checkHorison();
		win = win ? win : checkVertical();
		win = win ? win : checkDiagonal();
		win = win ? win : checkReverseDiagonal();
		
		if(win)
			this.win = this.turn;
	}
	
	private boolean checkHorison() {
		for(Vector<Box> row : map) {
			int count = 0;
			for(Box box: row) {
				if(box.getPlayer() == this.turn)
					count ++;
			}
			if(count == SIZE)
				return true;
		}
		return false;
	}
	
	private boolean checkVertical() {
		for(int i=0; i<SIZE; i++) {
			int count = 0;
			for(int j=0; j<SIZE; j++) {
				Box box = map.get(j).get(i);
				
				if(box.getPlayer() == this.turn)
					count ++;
			}
			if(count == SIZE)
				return true;
		}
		return false;
	}
	
	private boolean checkDiagonal() {
		int count = 0;
		for(int i=0; i<SIZE; i++) {
			Box box = map.get(i).get(i);
			
			if(box.getPlayer() == this.turn)
				count ++;
		}
		if(count == SIZE)
			return true;
		return false;
	}
	
	private boolean checkReverseDiagonal() {
		int count = 0;
		for(int i=0; i<SIZE; i++) {
			Box box = map.get(i).get(SIZE-1-i);
			
			if(box.getPlayer() == this.turn)
				count ++;
		}
		if(count == SIZE)
			return true;
		return false;
	}
	
	private void changeTurn() {
		this.turn = this.turn == Box.P1 ? Box.P2 : Box.P1;
	}

	private void showWinner() {
		System.out.println(this.win == 0 ? "DRAW~" : String.format("P%d WIN!", this.win));
	}
}

public class Ex03 {
	public static void main(String[] args) {

		Tictactoe game = new Tictactoe();
		game.run();

	}
}