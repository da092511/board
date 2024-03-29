package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Cbs {
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();

	private final int JOIN = 1;
	private final int LOGIN = 2;
	
	private ArrayList<User> users;
	private Map <User, ArrayList<Board>> boards;
	
	private Cbs() {
		users = new ArrayList<>();
		boards = new HashMap<User, ArrayList<Board>>();
	}
	
	private static Cbs instance = new Cbs();
	
	public static Cbs getInstance() {
		return instance;
	}
	
	private void showMenu() {
		System.out.println("1) 회원가입");
		System.out.println("2) 로그인");
	}
	
	private int checkUserByCode(int code) {
		int index =-1;
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			if(user.getCode() == code)
				index=i;
		}
		
		return index;
	}
	
	private int createCode() {
		int code = 0;
		while(true) {
			code = random.nextInt(9000)+1000;
			
			if(checkUserByCode(code) != -1)
				break;
		}
		
		return code;
	}
	
	
	private void join() {
		int code = createCode();
	}
	
	private void runMenu(int option) {
		switch (option) {
		case (JOIN):
			join();
			break;

		case (LOGIN):
			break;
		}
	}
	
	public void run() {
		// 콘솔 게시판
			//user 만 사용 가능
			// ㄴ Map 활용
			// ㄴ User CRUD
			// ㄴ Board CRUD				
			//		ㄴ 글 작성자만 권한 있습니다.
		while(true) {
			showMenu();
			int option = inputNumber("메뉴");
			runMenu(option);
		}
	}
	
	private String inputString(String message) {
		message = String.format("%s : ", message);
		System.out.println(message);
		
		return scanner.next();
	}
	
	private int inputNumber(String message) {
		int number = -1;
		message = String.format("%s : ", message);
		System.out.println(message);
		try {
			String input = scanner.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자만 입력");
		}
		
		return number;
	}
}
