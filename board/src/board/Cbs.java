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
	private final int MARK_LOG = 2;
	private final int ALL_CONTENT = 3;
	private final int MY_PAGE = 4;
	
	private ArrayList<User> users;
	private Map <User, ArrayList<Board>> boards;
	
	
	private int log = -1;
	
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
		System.out.println(String.format("2)%s", log == -1 ? "로그인" : "로그아웃"));
		System.out.println("3)전체 게시물");
		System.out.println("4)마이페이지");
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
	
	private int findUserById(String id) {
		int index =-1;
		
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			if(user.getId().equals(id))
				index=i;
		}
		
		return index;
	}
	
	private String inputId() {
		String id = "";
		
		while(true) {
			id = inputString("id");
			
			if(findUserById(id) == -1)
				break;
			
			System.err.println("중복된 아이디입니다.");
		}
		
		return id;
	}
	
	
	private void join() {
		int code = createCode();
		String name = inputString("name");
		String id = inputId();
		String pw = inputString("pw");
		
		User user = new User (code, name, id, pw);
		users.add(user);
		
		System.out.println("회원가입 성공");
	}
	
	private void runMenu(int option) {
		switch (option) {
		case JOIN:
			join();
			break;
		case MARK_LOG:
			break;
		case ALL_CONTENT:
			break;
		case MY_PAGE:
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
