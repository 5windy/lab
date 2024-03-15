package lv08;

import java.util.ArrayList;

// ATM

class User {
}

class UserManager {
	private ArrayList<User> list;

	// 기능
	// ㄴ User CRUD
}

class Account {
}

class AccountManager {
	private ArrayList<Account> list;

	// 기능
	// ㄴ Account CRUD
}

class Bank {

	private UserManager userManager = new UserManager();
	private AccountManager accountManager = new AccountManager();

	public void run() {
		// 시스템 기능
		// ㄴ (1)회원가입
		// ㄴ (2)입금
		// ㄴ (3)이체
		// ㄴ (4)계좌조회
		// ㄴ (5)계좌개설 (1~3까지)
		//    ㄴ ####-####-####
		// ㄴ (6)회원탈퇴 (계좌 전체 철회)
		// ㄴ (7)계좌철회 
		// ㄴ (0)종료
	}
}

public class Ex06 {
	public static void main(String[] args) {
		Bank system = new Bank("MEGA");
		system.run();

	}
}
