package lv08;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// ATM

class User {
	private int code;
	private String name;
	private String phone;

	public User() {

	}

	public User(int code, String name, String phone) {
		this.code = code;
		this.name = name;
		this.phone = phone;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User clone() {
		return new User(this.code, this.name, this.phone);
	}
}

class UserManager {
	private Random random = new Random();

	private static ArrayList<User> list = new ArrayList<User>();

	public UserManager() {
	}

	public User createUser(String name, String phone) {
		if (isValidPhone(phone)) {
			int code = generateUserCode();
			User user = new User(code, name, phone);
			list.add(user);
			return user.clone();
		}
		return new User();
	}

	public static User findUserByUserCode(int code) {
		for (User user : list) {
			if (user.getCode() == code)
				return user.clone();
		}
		return new User();
	}
	
	public User findUserByUserPhone(String phone) {
		for (User user : list) {
			if (user.getPhone().equals(phone))
				return user.clone();
		}
		return new User();
	}
	
	public ArrayList<User> findUserAll() {
		ArrayList<User> copy = new ArrayList<>();
		for(User user : list) {
			copy.add(user.clone());
		}
		return copy;
	}

	public void updateUserPhone(User user, String phone) {
		if (isValidPhone(phone)) {
			int userCode = user.getCode();
			User target = getUserByUserCode(userCode);
			target.setPhone(phone);
		}
	}

	public boolean deleteUser(User user) {
		int userCode = user.getCode();
		User target = getUserByUserCode(userCode);
		return list.remove(target);
	}

	public int getUserSize() {
		return this.list.size();
	}
	
	public static void dataSetting(String data) {
		String[] temp = data.split("\n");
		
		for(int i=0; i<temp.length; i++) {
			String[] info = temp[i].split("/");
			
			int code = Integer.parseInt(info[0]);
			String phone = info[1];
			String name = info[2];
			
			User user = new User(code, name, phone);
			list.add(user);
		}
	}

	private int generateUserCode() {
		int code = 0;
		while (true) {
			code = random.nextInt(9000) + 1000;

			User user = findUserByUserCode(code);
			if (user.getCode() == 0)
				break;
		}
		return code;
	}

	private boolean isValidPhone(String phone) {
		if (!phone.matches("\\d{3}-\\d{4}-\\d{4}"))
			return false;

		User user = findUserByUserPhone(phone);
		if (user.getCode() == 0)
			return true;
		return false;
	}

	private User getUserByUserCode(int code) {
		for (User user : list) {
			if (user.getCode() == code)
				return user;
		}
		return new User();
	}
}

class Account {
	private int userCode;
	private String number;
	private String password;
	private int balance;

	public Account() {

	}

	public Account(int userCode, String number, String password) {
		this.userCode = userCode;
		this.number = number;
		this.password = password;
	}

	public Account(int userCode, String number, String password, int balance) {
		this.userCode = userCode;
		this.number = number;
		this.password = password;
		this.balance = balance;
	}

	public int getUserCode() {
		return this.userCode;
	}

	public String getNumber() {
		return this.number;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public boolean equalsPassword(String password) {
		return this.password.equals(password);
	}

	public Account clone() {
		return new Account(this.userCode, this.number, this.password, this.balance);
	}
}

class AccountManager {
	private final int LIMIT = 3;

	private Random random = new Random();

	private static ArrayList<Account> list = new ArrayList<Account>();

	public AccountManager() {
	}

	public Account createAccount(int userCode, String password) {
		if (isValidUserCode(userCode) && !isExceedLimit(userCode)) {
			String accountNumber = generateNumber();
			Account account = new Account(userCode, accountNumber, password);
			list.add(account);
			return account.clone();
		}
		return new Account();
	}
	
	public Account findAccountByIndex(int index) {
		return list.get(index).clone();
	}

	public Account findAccountByNumber(String number) {
		for (Account account : list) {
			if (account.getNumber().equals(number))
				return account.clone();
		}
		return new Account();
	}

	public ArrayList<Account> findAccountAllByUserCode(int userCode) {
		ArrayList<Account> accounts = new ArrayList<>();
		for (Account account : list) {
			if (account.getUserCode() == userCode)
				accounts.add(account.clone());
		}
		return accounts;
	}

	public void updateAccountBalance(Account account, int balance) {
		String number = account.getNumber();
		Account target = getAccountByNumber(number);
		target.setBalance(balance);
	}

	public void deleteAccount(Account account) {
		String number = account.getNumber();
		Account target = getAccountByNumber(number);
		list.remove(target);
	}

	public int getAccountSize() {
		return this.list.size();
	}
	
	public static void dataSetting(String data) {
		String[] temp = data.split("\n");
		
		for(int i=0; i<temp.length; i++) {
			String[] info = temp[i].split("/");
			
			int userCode = Integer.parseInt(info[0]);
			String number = info[1];
			int balance = Integer.parseInt(info[2]);
			
			
			User user = UserManager.findUserByUserCode(userCode);
			String password = user.getPhone();
			
			Account account = new Account(userCode, number, password, balance);
			list.add(account);
		}
	}

	private String generateNumber() {
		String accountNumber = "";
		while (true) {
			int head = random.nextInt(9000) + 1000;
			int body = random.nextInt(9000) + 1000;
			int tail = random.nextInt(9000) + 1000;

			accountNumber = String.format("%d-%d-%d", head, body, tail);

			if (isValidAccountNumber(accountNumber))
				break;
		}
		return accountNumber;
	}

	private Account getAccountByNumber(String number) {
		for (Account account : list) {
			if (account.getNumber().equals(number))
				return account;
		}
		return new Account();
	}

	private boolean isValidAccountNumber(String accountNumber) {
		Account account = findAccountByNumber(accountNumber);
		if (account.getNumber() == null)
			return true;
		return false;
	}

	private boolean isValidUserCode(int userCode) {
		return userCode != 0;
	}

	private boolean isExceedLimit(int userCode) {
		ArrayList<Account> accounts = findAccountAllByUserCode(userCode);
		if (accounts.size() < LIMIT)
			return false;
		return true;
	}
}

class Bank {
	private final int USER_JOIN = 1;
	private final int DEPOSIT = 2;
	private final int TRANSFER = 3;
	private final int ACCOUNT_CHECK = 4;
	private final int ACCOUNT_OPEN = 5;
	private final int ACCOUNT_WITHDRAW = 6;
	private final int USER_LEAVE = 7;
	private final int EXIT = 0;

	private Scanner scanner = new Scanner(System.in);
	private DecimalFormat dcf = new DecimalFormat("#,###");

	private UserManager userManager = new UserManager();
	private AccountManager accountManager = new AccountManager();

	public final String brand;
	
	public boolean isRun;

	public Bank(String brand) {
		this.brand = brand;
		this.isRun = true;
	}

	public void run() {
		while (isRun) {
			printStatus();
			showMenus();
			runMenu(option());
		}
	}

	private void runMenu(int option) {
		switch (option) {
		case USER_JOIN:
			joinUser();
			break;
		case DEPOSIT:
			deposit();
			break;
		case TRANSFER:
			transfer();
			break;
		case ACCOUNT_CHECK:
			checkAccount();
			break;
		case ACCOUNT_OPEN:
			openAccount();
			break;
		case ACCOUNT_WITHDRAW:
			withdrawAccount();
			break;
		case USER_LEAVE:
			leaveUser();
			break;
		case EXIT:
			this.isRun = false;
			break;
		}
	}

	private void showMenus() {
		System.out.println("------------");
		System.out.println("1. 회원가입");
		System.out.println("2. 입금");
		System.out.println("3. 이체");
		System.out.println("4. 계좌조회");
		System.out.println("5. 계좌개설");
		System.out.println("6. 계좌철회");
		System.out.println("7. 회원탈퇴");
		System.out.println("0. 종료");
		System.out.println("------------");
	}

	private void joinUser() {
		String name = inputString("name");
		String phone = inputString("phone (###-####-####)");

		User user = userManager.createUser(name, phone);
		openAccount(user);

		printWelcomeMessage(user);
	}

	private void printWelcomeMessage(User user) {
		String message = user.getCode() != 0 ? String.format("%s(%d) 회원님 환영합니다.", user.getName(), user.getCode())
				: "회원가입 실패";
		System.out.println(message);
	}

	private void leaveUser() {
		String phone = inputString("phone (###-####-####)");

		User user = userManager.findUserByUserPhone(phone);
		int userCode = user.getCode();
		ArrayList<Account> accounts = accountManager.findAccountAllByUserCode(userCode);

		boolean result = false;
		if (withdrawFullAccount(accounts)) {
			result = userManager.deleteUser(user);
		}

		String message = result ? "회원 탈퇴 완료" : "회원 탈퇴 실패";
		System.out.println(message);
	}

	private boolean withdrawFullAccount(ArrayList<Account> accounts) {
		if (checkAllAccountPassword(accounts)) {
			for (Account account : accounts)
				accountManager.deleteAccount(account);
			return true;
		}
		return false;
	}

	private boolean checkAllAccountPassword(ArrayList<Account> accounts) {
		for (Account account : accounts) {
			String password = inputString(account.getNumber() + " password");
			if (!account.equalsPassword(password))
				return false;
		}
		return true;
	}

	private void deposit() {
		Account account = findAccount();

		if (isValidAccount(account)) {
			int money = inputNumber("입금 금액");
			int balance = account.getBalance();

			if (money > 0) {
				accountManager.updateAccountBalance(account, balance + money);
				System.out.println("입금 완료");
			} else {
				System.out.println("입금 실패");
			}
		}
	}

	private void transfer() {
		Account startAccount = findAccount("내");
		String password = inputString("계좌 비밀번호");

		Account endAccount = findAccount("상대");
		int money = inputNumber("이체 금액");

		Account[] accounts = new Account[] { startAccount, endAccount };

		if (isValidAccount(accounts) && startAccount.equalsPassword(password)) {
			int balance = startAccount.getBalance();

			if (money > 0 && balance - money >= 0) {
				accountManager.updateAccountBalance(startAccount, balance - money);
				accountManager.updateAccountBalance(endAccount, endAccount.getBalance() + money);
				System.out.println("이체 완료");
			} else {
				System.out.println("이체 실패");
			}
		}
	}

	private void checkAccount() {
		Account account = findAccount();
		String password = inputString("계좌 비밀번호");

		if (account.equalsPassword(password)) {
			System.out.println(toStringMoney(account.getBalance()));
		}
	}

	private String toStringMoney(int money) {
		String message = dcf.format(money) + "원";
		return message;
	}

	private boolean isValidAccount(Account account) {
		return account.getNumber() != null;
	}

	private boolean isValidAccount(Account[] accounts) {
		for (Account account : accounts) {
			if (account.getNumber() == null)
				return false;
		}
		
		int code = accounts[0].getUserCode();
		if(accounts[1].getUserCode() == code)
			return false;
		
		return true;
	}

	private void openAccount() {
		User user = findUser();
		openAccount(user);
	}

	private void openAccount(User user) {
		int userCode = user.getCode();
		String password = inputString("계좌 비밀번호");
		Account account = accountManager.createAccount(userCode, password);
		if(isValidAccount(account))
			System.out.println(account.getNumber() + " 개설 완료");
	}

	private void withdrawAccount() {
		Account account = findAccount();
		String password = inputString("계좌 비밀번호");
		if(account.equalsPassword(password)) {
			accountManager.deleteAccount(account);
			System.out.println("계좌 철회 완료");
		} else {
			System.out.println("계좌 철회 실패");
		}
	}
	
	private User findUser() {
		String phone = inputString("phone (###-####-####)");
		User user = userManager.findUserByUserPhone(phone);
		return user;
	}
	
	private Account findAccount() {
		String accountNumber = inputString("계좌 번호 (####-####-####)");
		Account account = accountManager.findAccountByNumber(accountNumber);
		return account;
	}

	private Account findAccount(String specific) {
		String accountNumber = inputString(specific + " 계좌 번호 (####-####-####)");
		Account account = accountManager.findAccountByNumber(accountNumber);
		return account;
	}

	private void printStatus() {
		int userSize = userManager.getUserSize();
		int accountSize = accountManager.getAccountSize();
		String status = String.format("User size : %d\nAccount size : %d", userSize, accountSize);

		System.out.println("------------");
		System.out.println(status);
		
		printDataAll();
		printDataSummery();
	}
	
	private void printDataAll() {
		int accountSize = accountManager.getAccountSize();
		
		if(accountSize > 0) {
			System.out.println("------------");
		}
		
		for(int i=0; i<accountSize; i++) {
			Account account = accountManager.findAccountByIndex(i);
			String info = String.format("%d) %s : %s", account.getUserCode(), account.getNumber(), toStringMoney(account.getBalance()));
			System.out.println(info);
		}
	}
	
	private void printDataSummery() {
		// test01 김철수 는 계좌를 3개 가지고 있다.
		
		ArrayList<User> userList = userManager.findUserAll();
		
		System.out.println("------------");
		for(User user : userList) {
			int userCode = user.getCode();
			ArrayList<Account> accList = accountManager.findAccountAllByUserCode(userCode);
			
			String message = String.format("%d %s는 계좌를 %d개 가지고 있다.", userCode, user.getName(), accList.size());
			System.out.println(message);
		}
	}

	private int option() {
		return inputNumber("선택");
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return scanner.next();
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

public class Ex06 {
	public static void main(String[] args) {
		
		// Static 
		// ㄴ 메소드와 메소드 안에서 참조되는 변수들까지 -> 메모리 영역을 static으로 지정하여 해결 
		
		String userdata = "1001/pw1/김철수\n";
		userdata += "1002/pw2/이영희\n";
		userdata += "1003/pw3/신민수\n";
		userdata += "1004/pw4/최상민";
		
		String accountdata = "1001/1111-1111-1111/8000\n";
		accountdata += "1002/2222-2222-2222/5000\n";
		accountdata += "1001/3333-3333-3333/11000\n";
		accountdata += "1003/4444-4444-4444/9000\n";
		accountdata += "1001/5555-5555-5555/5400\n";
		accountdata += "1002/6666-6666-6666/1000\n";
		accountdata += "1003/7777-7777-7777/1000\n";
		accountdata += "1004/8888-8888-8888/1000";

		// 1) test01 김철수 는 계좌를 3개 가지고있다.
		// 2) test02 이영희 는 계좌를 2개 가지고있다.
		// 3) test03 신민수 는 계좌를 2개 가지고있다.
		// 4) test04 최상민 은 계좌를 1개 가지고있다. 
		
		UserManager.dataSetting(userdata);
		AccountManager.dataSetting(accountdata);
		
		Bank system = new Bank("MEGA");
		system.run();

	}
}
