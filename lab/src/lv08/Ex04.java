package lv08;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

// 기억력 게임 (카드 뒤집기) 

class Node {
	private int front;
	private int back;
	private boolean isFlipped;

	public Node(int back) {
		this.back = back;
	}
	
	public int getFront() {
		return this.front;
	}

	public boolean isFlipped() {
		return this.isFlipped;
	}

	public void setFlipped() {
		this.isFlipped = !this.isFlipped;
		
		if(this.isFlipped)
			this.front = this.back;
		else
			this.front = 0;
	}

	@Override
	public String toString() {
		return this.front == 0 ? "[ ♥︎]" : String.format("[%2d]", front);
	}
}

class MemoryGame {
	private final int SIZE = 10;

	private Scanner scanner = new Scanner(System.in);

	private Vector<Node> nodeList;
	private int data[];
	
	private int count;

	public MemoryGame() {
		setGame();
	}

	public void run() {
		while (isRun()) {
			showCards();
			play();
		}
		showCards();
	}

	private void setGame() {
		setData();

		nodeList = new Vector<>();

		for (int i = 0; i < SIZE; i++) {
			Node node = new Node(data[i]);
			nodeList.add(node);
		}
	}

	private void setData() {
		data = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			data[i] = (i + 2) / 2;
		}

		shuffle();
	}

	private void shuffle() {
		Random random = new Random();

		for (int i = 0; i < SIZE * SIZE; i++) {
			int rIdx = random.nextInt(SIZE);

			int temp = data[0];
			data[0] = data[rIdx];
			data[rIdx] = temp;
		}
	}

	private boolean isRun() {
		return this.count < SIZE/2;
	}

	private void showCards() {
		System.out.println("--------------------");
		for (Node node : nodeList) {
			System.out.print(node);

			if (nodeList.indexOf(node) == SIZE / 2 - 1)
				System.out.println();
		}
		System.out.println("\n--------------------");
	}

	private void play() {
		int x = inputNumber("card 1 number") - 1;
		int xx = inputNumber("card 2 number") - 1;

		if (x < 0 || x >= SIZE || xx < 0 || xx >= SIZE || x == xx || nodeList.get(x).isFlipped())
			return;

		flipCards(new int[] {x, xx});
	}

	private void flipCards(int[] index) {
		Node[] cards = new Node[2];

		for (int i = 0; i < cards.length; i++) {
			cards[i] = nodeList.get(index[i]);
			cards[i].setFlipped();

			showCards();

			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		}

		checkCorrect(index);
	}
	
	private void checkCorrect(int[] index) {
		Node[] cards = new Node[2];

		for (int i = 0; i < cards.length; i++)
			cards[i] = nodeList.get(index[i]);
		
		if(cards[0].getFront() == cards[1].getFront()) {
			this.count ++;
			return;
		}
		
		for (int i = 0; i < cards.length; i++)
			cards[i].setFlipped();
	}

	private int inputNumber(String message) {
		int number = -1;
		try {
			System.out.println(message + " : ");
			String input = scanner.next();
			number = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("숫자를 입려하세요.");
		}
		return number;
	}
}

class Ex04 {
	public static void main(String[] args) {
		MemoryGame game = new MemoryGame();
		game.run();
	}
}