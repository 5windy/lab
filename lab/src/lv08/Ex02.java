package lv08;

import java.util.Scanner;
import java.util.Vector;

// Vector를 활용한 컨트롤러 구현

class Tv {
	private String name;
	private String brand;
	private int price;

	public Tv(String name, String brand, int price) {
		this.name = name;
		this.brand = brand;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s] : %d원", name, brand, price);
	}
}

class SalesManager {
	
	private final int ADD = 1;
	private final int INSERT = 2;
	private final int DELETE = 3;
	private final int DELETE_BY_VALUE = 4;
	private final int SEARCH = 5;
	private final int UPDATE = 6;
	private final int SIZE = 7;

	private Scanner scanner;
	
	private Vector<Tv> list;
	
	public SalesManager() {
		this.scanner = new Scanner(System.in);
		this.list = new Vector<>();
	}
	
	private void printDataAll() {
		for(int i=0; i<list.size(); i++)
			System.out.println(list.get(i));
	}
	
	private int inputNumber(String message) {
		int number = 0;
		
		System.out.print(message + " : ");
		try {
			String input = scanner.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자를 입력하세요.");
		}
		return number;
	}
	
	private String inputString(String message) {
		System.out.print(message + " : ");
		return scanner.next();
	}
	
	private void runMenu(int select) {
		if(select == ADD)
			add();
		else if(select == INSERT)
			insert();
		else if(select == DELETE)
			deleteByIndex();
		else if(select == DELETE_BY_VALUE)
			deleteByValue();
		else if(select == SEARCH)
			search();
		else if(select == UPDATE)
			update();
		else if(select == SIZE)
			printSize();
	}
	
	private void add() {
		Tv tv = createTv();
		list.add(tv);
	}
	
	private Tv createTv() {
		Tv tv = null;
		
		String name = inputString("name");
		String brand = inputString("brand");
		int price = inputNumber("price");
		
		tv = new Tv(name, brand, price);
		
		return tv;
	}
	
	private void insert() {
		int index = inputNumber("index");
		Tv tv = createTv();
		
		if(index < 0 || index > list.size())
			return;
		
		list.add(index, tv);
	}
	
	private void deleteByIndex() {
		int index = inputNumber("index");
		
		if(index < 0 || index >= list.size())
			return;
		
		list.remove(index);
	}
	
	private void deleteByValue() {
		Tv target = searchTvByData();
		list.remove(target);
	}
	
	private Tv searchTvByData() {
		Tv target = null;
		
		String name = inputString("name");
		String brand = inputString("brand");
		int price = inputNumber("price");
		
		for(int i=0; i<list.size(); i++) {
			Tv tv = list.get(i);
			
			if(tv.getName().equals(name) 
					&& tv.getBrand().equals(brand) 
						&& tv.getPrice() == price)
				target = tv;
		}
		
		return target;
	}
	
	private void search() {
		Tv tv = searchTvByData();
		System.out.println(tv);
	}
	
	public void update() {
		try {
			int index = inputNumber("index");
			Tv tv = createTv();
			
			list.set(index, tv);
			
		} catch (IndexOutOfBoundsException e) {
			System.err.println("유효하지 않은 범위입니다.");
		}
	}
	
	public void printSize() {
		System.out.println("size : " + list.size());
	}
	
	public void run() {
		while(true){
			printDataAll();
			int select = inputNumber("메뉴");
			runMenu(select);
			// 추가, 삽입, 삭제, 삭제(값), 조회, 수정, 크기
		}
	}
	
}
public class Ex02 {
	public static void main(String[] args){
		
		SalesManager system = new SalesManager();
		system.run();
	}
}