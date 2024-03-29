package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Cbs {
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();

	private final int PAGE = 5;
	
	private final int JOIN = 1;
	private final int SET_LOG = 2;
	private final int ALL_CONTENT = 3;
	private final int WRITE_CONTENT = 4;
	private final int MY_PAGE = 5;
	
	private ArrayList<User> users;
	private ArrayList<Board> boards;
	
	private Map <User, ArrayList<Board>> owners;
	
	
	private int log = -1;
	
	private Cbs() {
		users = new ArrayList<>();
		boards = new ArrayList<>();
		owners = new HashMap<User, ArrayList<Board>>();
	}
	
	private static Cbs instance = new Cbs();
	
	public static Cbs getInstance() {
		return instance;
	}
	
	private void showMenu() {
		System.out.println("1) 회원가입");
		System.out.println(String.format("2)%s", log == -1 ? "로그인" : "로그아웃"));
		System.out.println("3)전체 게시물");
		System.out.println("4)게시물 작성");
		System.out.println("5)마이페이지");
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
	
	private void setLog() {
		//로그아웃
		if(log != -1) {
			this.log = -1;
			System.out.println("로그아웃 완료");
			return;
		}
		
		//로그인
		login();
	}
	
	private void login() {
		String id = inputString("id");
		String pw = inputString("pw");
		
		int index = findUserById(id);
		if(index == -1) {
			System.err.println("해당 아이디는 존재하지 않습니다.");	
			return;
		}else if(!users.get(index).getPw().equals(pw)) {
			System.err.println("비밀번호가 틀립니다.");	
			return;
		}
		
		log = index;
		System.out.println("로그인 성공");
	}
	
	private void showContent(int firstIndex) {
		int lastIndex = firstIndex + PAGE;
		
		if(lastIndex >= boards.size())
			lastIndex = boards.size();
		
		for(int i= firstIndex;i<=lastIndex;i++) {
			Board board = boards.get(i);
			String title = board.getTitle();
			
			System.out.println(title);
		}
		
	}
	
	private void showContentMenu() {
		System.out.println("1)이전 페이지");
		System.out.println("2)다음 페이지");
		System.out.println("3)게시물 선택");
		System.out.println("0)뒤로가기");
	}
	
	private void runContentMenu(int option) {
		
	}
	
	private void openContent() {
		User user = users.get(log);
		
		
	}
	
	private void content() {
		int firstIndex = 0;
		
		
		while(true) {
			showContent(firstIndex);
			showContentMenu();
			int option = inputNumber("");
			
			if(option == 0)
				break;
			
		//	runContentMenu(option);
			
		}
	}
	
	
	private String writeContent() {
		String info = "";
		
		while(true) {
			String line = inputString("줄바꿈[;] 글쓰기 완료[1] 뒤로가기[0]");
			
			if(line.equals("0")) 
				break;
			else if (line.equals("1"))
				break;
			
			info += line;
		}
		
		return info;
	}
	
	private void write() {
		User user = users.get(log);
		
		boolean isCheck = false;
		if(boards.size() > 3) {
			isCheck = true;
		}
		
		if(isCheck && !user.accessible()) {
			System.err.println("아직 접근이 불가능합니다.");
			return;
		}
		
		String id = user.getId();
		String title = inputString("게시글 제목");
		String content = writeContent();
	}
	
	private void runMenu(int option) {
		switch (option) {
		case JOIN:
			join();
			break;
		case SET_LOG:
			setLog();
			break;
		case ALL_CONTENT:
			content();
			break;
		case WRITE_CONTENT:
			write();
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
		
		return scanner.nextLine();
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
