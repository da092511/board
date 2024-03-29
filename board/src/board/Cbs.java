package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Cbs {
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();
	
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
	
	public void run() {
		// 콘솔 게시판
			//user 만 사용 가능
			// ㄴ Map 활용
			// ㄴ User CRUD
			// ㄴ Board CRUD				
			//		ㄴ 글 작성자만 권한 있습니다.
		while(true) {
			
			int option = inputNumber("메뉴");
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
