package lv08;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

// 1 to 50

class Card {
	private int number;
	private int hidden;

	public Card(int number, int hidden) {
		this.number = number;
		this.hidden = hidden;
	}

	public int getNumber() {
		return this.number;
	}

	public void flip() {
		this.number = this.hidden;
		this.hidden = 0;
	}

	@Override
	public String toString() {
		return number == 0 ? "[  ]" : String.format("[%2d]", this.number);
	}
}

class Manager1to50 {
	private final int SIZE = 5;

	private Scanner scanner = new Scanner(System.in);

	private Vector<ArrayList<Card>> map;
	private int[] data;

	private int gameNumber;

	public Manager1to50() {
		setGame();
	}

	public void run() {
		while (isRun()) {
			printMap();
			play();
		}
	}

	private boolean isRun() {
		return this.gameNumber <= SIZE * SIZE * 2;
	}

	private void setGame() {
		this.gameNumber = 1;
		setData();
		setMap();
	}

	private void setData() {
		data = new int[SIZE * SIZE * 2];

		for (int i = 0; i < data.length; i++)
			data[i] = i + 1;

		shuffle();
	}
	
	private void setMap() {
		map = new Vector<>();

		int idx = 0;
		for (int i = 0; i < SIZE; i++) {
			ArrayList<Card> row = new ArrayList<>();

			for (int j = 0; j < SIZE; j++) {
				Card card = new Card(data[idx], data[idx++ + (SIZE*SIZE)]);
				row.add(card);
			}
			map.add(row);
		}
	}

	private void shuffle() {
		Random random = new Random();

		for (int i = 0; i < 1000; i++) {
			int rIdx = random.nextInt(SIZE * SIZE);

			int temp = data[0];
			data[0] = data[rIdx];
			data[rIdx] = temp;

			rIdx = random.nextInt(SIZE * SIZE) + (SIZE * SIZE);

			temp = data[SIZE * SIZE];
			data[SIZE * SIZE] = data[rIdx];
			data[rIdx] = temp;
		}
	}

	private void printMap() {
		for (ArrayList<Card> row : map) {
			for (Card card : row)
				System.out.print(card);
			System.out.println();
		}
	}

	private void play() {
		int yx = inputNumber("yx");

		int y = yx / 10;
		int x = yx % 10;

		if (y < 0 || y >= SIZE || x < 0 || x >= SIZE)
			return;

		Card card = map.get(y).get(x);

		flipCard(card);
	}

	private void flipCard(Card card) {
		if (card.getNumber() != this.gameNumber)
			return;

		card.flip();
		
		this.gameNumber ++;
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
}

class Ex05 {
	public static void main(String[] args) {
		Manager1to50 game = new Manager1to50();
		game.run();
	}
}