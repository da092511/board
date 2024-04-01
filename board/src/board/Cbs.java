package board;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Cbs {
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/DD hh:mm");
	
	private final int PAGE = 5;
	
	private final int JOIN = 1;
	private final int SET_LOG = 2;
	private final int ALL_CONTENT = 3;
	private final int WRITE_CONTENT = 4;
	private final int MY_PAGE = 5;

	private final int BEFORE = 1;
	private final int NEXT = 2;
	private final int SELECT = 3;
	private final int BACK = 4;
 	
	private ArrayList<User> users;
	private ArrayList<Board> boards;
	
	private Map <User, ArrayList<Board>> owners;
	
	private FileManager fm = new FileManager();
	
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
		System.out.println("1)회원가입");
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
			
			if(checkUserByCode(code) == -1)
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
		int pageSize = firstIndex + PAGE;
		
		if(pageSize >= boards.size())
			pageSize = boards.size();
		
		int n = 1;
		for(int i=firstIndex;i<pageSize;i++) {
			Board board = boards.get(i);
			String title = board.getTitle();
			
			System.out.println(n +". "+ title);
			n++;
		}
		
	}
	
	private void showContentMenu() {
		System.out.println("1)이전 페이지");
		System.out.println("2)다음 페이지");
		System.out.println("3)게시물 선택");
		System.out.println("0)뒤로가기");
	}
	
	private int setPageBefore(int firstIndex) {
		firstIndex -= PAGE; 
		
		if(firstIndex < 0) {
			System.err.println("처음 페이지입니다.");
			firstIndex = 0;
		}
		
		return firstIndex;
	}
	
	private int setPageNext(int firstIndex) {
		firstIndex += PAGE; 
		
		if(firstIndex >= boards.size()) {
			System.err.println("마지막 페이지입니다.");
			firstIndex -= PAGE;
		}
		
		return firstIndex;
	}
	
	private void openContent(int select, int firstIndex) {
		int index = firstIndex + select - 1;
		Board board = boards.get(index);
		
		System.out.println(board);
	}
	
	private int runContentMenu(int option, int firstIndex) {
		switch (option) {
		case BEFORE:
			firstIndex = setPageBefore(firstIndex);
			break;
		case NEXT:
			firstIndex = setPageNext(firstIndex);
			break;
		case SELECT:
			int select = inputNumber("열람할 게시물 번호");
			openContent(select, firstIndex);
			break;
		case BACK :
			firstIndex = -1;
			break;
		}
		
		return firstIndex;
	}
	
	private void content() {
		int firstIndex = 0;
		
		while(true) {
			showContent(firstIndex);
			showContentMenu();
			int option = inputNumber("");
			
			if(option == 0)
				break;
			
			firstIndex = runContentMenu(option, firstIndex);
			
			if(firstIndex == -1)
				break;
		}
	}
	
	
	private String writeContent() {
		String info = "";
		
		while(true) {
			String line = inputStringLine("줄바꿈[enter] 글쓰기 완료[0]]");
			
			String data = "";
			String last = "";
			
			if(!line.equals("")) {
				data = line.substring(0, line.length()-1);
				last = line.substring(line.length()-1, line.length());
			}
			else 
				continue;
			
			info += data;
			
			if(last.equals("0")) {
				break;
			}
			
			info += last + "<br>";
		}
		
		return info;
	}
	
	private void write() {
		if(log == -1)
			return ;
		
		User user = users.get(log);
		int userCode = user.getCode();
		
		boolean isCheck = false;
		if(boards.size() > 3) {
			isCheck = true;
		}
		
		if(isCheck && !user.accessible()) {
			System.err.println("아직 접근이 불가능합니다.");
			return;
		}
		
		String id = user.getId();
		String line = inputStringLine("게시글 제목");
		String title = line;
		String content = writeContent();
		String date = String.format(sdf.format(System.currentTimeMillis()));
		
		Board board = new Board(title, date, id, content);
		//user.record(board);
		boards.add(board);
		
		//유저 보드 목록 수정하기
		if(owners.get(user) == null) {
			ArrayList<Board> myBoard = new ArrayList<Board>();
			myBoard.add(board);
			
			owners.put(user, myBoard);
			
			return;
		}
		
		ArrayList<Board> myBoard= owners.get(user);
		myBoard.add(board);
		
		owners.replace(user,myBoard);
	}
	
	private void showMyPageMenu() {
		System.out.println("1)내 정보");
		System.out.println("2)내 게시물");
	}
	
	private void runMyPageMenu(int select) {
		
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
			showMyPageMenu();
			int select = inputNumber("");
			runMyPageMenu(select);
			break;
		}
	}
	
	private void loadData() {
		String data = fm.loadData();
		
		if(data.equals(null) || data.equals(""))
			return;
		
		
		
	}
	
	private String inputUserData() {
		String data = "";
		
		data += users.size();
		
		for(User user : users) {
			int userCode = user.getCode();
			String userName = user.getName();
			String userId = user.getId();
			String userPw = user.getPw();
			
			data += "\n" + userCode + "/" + userName +"/" + userId +"/" +userPw;
		}
		
		return data;
	}
	
	private String inputBoardData() {
		String data = "";
		
		
		
		return data ;
	}
	
	private void saveData() {
		/*  userSize
		 *  usercode/username/userId/userPw/
		 *  user2Code/...
		 *  board1
		 *  board2
		 */
		
		String data = "";
		
		data += inputUserData();
		data += inputBoardData();
	}
	
	public void run() {
		// 콘솔 게시판
			//user 만 사용 가능
			// ㄴ Map 활용
			// ㄴ User CRUD
			// ㄴ Board CRUD				
			//		ㄴ 글 작성자만 권한 있습니다.
		while(true) {
			loadData();
			showMenu();
			int option = inputNumber("메뉴");
			runMenu(option);
			saveData();
		}
	}
	
	private String inputStringLine(String message) {
		scanner.nextLine();
		
		message = String.format("%s :", message);
		System.out.print(message);
		String input = scanner.nextLine();
		
		return input.toString();
	}
	
	private String inputString(String message) {
		message = String.format("%s : ", message);
		System.out.print(message);
		
		return scanner.next();
	}
	
	private int inputNumber(String message) {
		int number = -1;
		message = String.format("%s : ", message);
		System.out.print(message);
		try {
			String input = scanner.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자만 입력");
		}
		
		return number;
	}
}
