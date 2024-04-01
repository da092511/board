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
	private SimpleDateFormat sdf = new SimpleDateFormat("MM.DD. hh:mm");
	
	private final int PAGE = 5;
	
	private final int JOIN = 1;
	private final int SET_LOG = 2;
	private final int ALL_CONTENT = 3;
	private final int WRITE_CONTENT = 4;
	private final int MY_PAGE = 5;
	private final int QUIT = 0;

	private final int BEFORE = 1;
	private final int NEXT = 2;
	private final int SELECT = 3;
	private final int BACK = 4;

	private final int MY_INFO = 1;
	private final int MY_CONTENTS =2;
	
	private final int UPDATE = 1;
	private final int DELECT = 2;
	
	private ArrayList<User> users;
	private ArrayList<Board> boards;
	
	private Map <String, ArrayList<Board>> owners;  // <id,ArrayList<Board>>
	
	private FileManager fm = new FileManager();
	
	private int log = -1;
	
	private boolean isRun;
	
	private Cbs() {
		setSystem();
		
		this.isRun = true;
	}
	
	private void setSystem() {
		users = new ArrayList<>();
		boards = new ArrayList<>();
		owners = new HashMap<String, ArrayList<Board>>();
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
		System.out.println("0)종료");
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
		
		users.get(log).openContent();
		
		Board board = boards.get(index);
		
		System.out.println(board);
		System.out.println();
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
		if(boards.size() >= 3) {
			isCheck = true;
		}
		
		System.out.println(user.accessible());
		if(isCheck && !user.accessible()) {
			System.err.println("아직 접근이 불가능합니다.");
			return;
		}
		
		String id = user.getId();
		String line = inputStringLine("게시글 제목");
		String title = line;
		String content = writeContent();
		String date = String.format(sdf.format(System.currentTimeMillis()));
		
		Board board = new Board(title, id, date, content);
		//user.record(board);
		boards.add(board);
		user.writeContent();
		
		//유저 보드 목록 수정하기
		if(owners.get(id) == null) {
			ArrayList<Board> myBoard = new ArrayList<Board>();
			myBoard.add(board);
			
			owners.put(id, myBoard);
			
			return;
		}
		
		ArrayList<Board> myBoard= owners.get(id);
		myBoard.add(board);
		
		owners.replace(id,myBoard);
	}
	
	private void showMyPageMenu() {
		System.out.println("1)내 정보");
		System.out.println("2)내 게시물");
	}

	private void showMyInformation() {
		User user = users.get(log);
		int openCnt = user.getOpenCount();
		
		System.out.println(user);
		System.out.println("작성한 게시물 : " + user.getWriteContentCount() + "개");
		System.out.println("열람한 게시물 : " + openCnt + "개");
		System.out.println(user.accessible() ? "게시물 작성 가능" : "게시물 작성 불가");
	}
	
	private ArrayList<Board> getOwnBoard(){
		User user = users.get(log);
		String userId = user.getId();
		
		ArrayList<Board> myBoard = owners.get(userId);
		
		return myBoard;
	}
	
	private void showMyContents() {
		ArrayList<Board> myBoard = getOwnBoard();
		
		int n =1;
		for(Board board : myBoard) {
			String title = board.getTitle();
			
			System.out.println(n + ". " + title);
			n++;
		}
	}
	
	private void showMyContentMenu() {
		System.out.println("1) 수정");
		System.out.println("2) 삭제");
	}
	
	private void openContents(int index) {
		ArrayList<Board> myBoard = getOwnBoard();
		System.out.println(myBoard.get(index));
		
		showMyContentMenu();
		
		int option = inputNumber("");
		runMyContentsMenu(option , index);
	}
	
	private String updateData(String data) {
		String line = inputStringLine("제목 수정할 부분 (없을 시 enter)");
		
		String updateInfo = inputStringLine("수정할 내용");
		
		String newTitle = "";
		
		for(int i=0;i<data.length()-line.length()+1;i++) {
			boolean isCheck = true;
			
			for(int j=0;j<line.length();j++) 
				if(!data.substring(i+j,i+j+1).equals(line.substring(j,j+1))) {
					isCheck = false;
					break;
				}
			
			if(isCheck) {
				newTitle += updateInfo;
				i += updateInfo.length();
				continue;
			}
				
			newTitle += data.substring(i,i+1);
		}
		
		System.out.println(newTitle);
		return newTitle;
	}
	
	private void updateContent(int index) {
		String userId = users.get(log).getId();
		
		ArrayList<Board> myBoard = getOwnBoard();
		
		if(myBoard == null)
			return;
		
		Board board = myBoard.get(index);
		System.out.println(board);
		
		String title = updateData(board.getTitle());
		board.setTitle(title);
		String contents = updateData(board.getContents());
		board.setContents(contents);
	}
	
	private void delectContent(int index) {
		String userId = users.get(log).getId();
		
		ArrayList<Board> myBoard = getOwnBoard();
		myBoard.remove(index);
		
		owners.put(userId, myBoard);
	}
	
	private void runMyContentsMenu(int option, int index) {
		switch(option) {
		case UPDATE : 
			updateContent(index);
			break;
		case DELECT : 
			delectContent(index);
			break;
		}
	}
	
	
	private void runMyPageMenu(int select) {
		switch(select) {
		case MY_INFO : 
			showMyInformation();
			break;
		case MY_CONTENTS : 
			showMyContents();
			int index = inputNumber("확인할 게시물 번호")-1;
			
			if(index == -1)
				return;
			
			openContents(index);
		}
	}
	
	private boolean checkLog() {
		return this.log == -1 ? false : true;
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
			if(!checkLog())
				break;
			showMyPageMenu();
			int select = inputNumber("");
			runMyPageMenu(select);
			break;
		case QUIT : 
			this.isRun = false;
		}
	}
	
	private void loadUserData(int userSize , String[] data) {
		for(int i=1;i<=userSize;i++) {
			String[] info = data[i].split("/");
			int code = Integer.parseInt(info[0]);
			String name = info[1];
			String id = info[2];
			String pw = info[3];
			
			User user = new User(code,name,id,pw);
			
			users.add(user);
		}
	}
	
	private void loadBoardData(String[] boardData) {
		String title = boardData[0];
		String id = boardData[1];
		String date = boardData[2];
		String contents = boardData[3];
		
		Board board = new Board(title,id,date,contents);
		boards.add(board);
	}
	
	private void loadOwner() {
		for(User user : users) {
			String userId = user.getId();
			
			ArrayList<Board> own = new ArrayList<>();
			for(Board board : boards) {
				System.out.println(board.getUserId());
				if(board.getUserId().equals(userId))
					own.add(board);
			}
			
			if(own.size() > 0)
				owners.put(userId, own);
		}
	}
	
	private void loadData() {
		/*  userSize
		 *  usercode/username/userId/userPw/
		 *  user2Code/...
		 *  title/id/date/contents
		 */
		
		setSystem();
		
		String info = fm.loadData();
		
		if(info == null || info.equals(""))
			return;
		
		String[] data = info.split("\n");
		int userSize = Integer.parseInt(data[0]);
		if(userSize == 0)
			return;
		
		loadUserData(userSize, data);
		
		if(data.length > userSize +1) {
			for(int i = userSize+1;i<data.length;i++) {
				String[] boardData = data[i].split("/");
				loadBoardData(boardData);
			}	
		}
		
		loadOwner();
		
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
		
		for(Board board : boards) {
			String title = board.getTitle();
			String id = board.getUserId();
			String boardDate = board.getDate();
			String contents = board.getContents();
			data += "\n" + title + "/" + id + "/" + boardDate + "/" + contents; 
		}
		
		return data ;
	}
	
	private void saveData() {
		/*  userSize
		 *  usercode/username/userId/userPw/
		 *  user2Code/...
		 *  title/id/date/contents
		 */
		
		String data = "";
		
		data += inputUserData();
		data += inputBoardData();
		
		fm.saveData(data);
		System.out.println("저장완료");
		
	}
	
	public void run() {
		// 콘솔 게시판
			//user 만 사용 가능
			// ㄴ Map 활용
			// ㄴ User CRUD
			// ㄴ Board CRUD				
			//		ㄴ 글 작성자만 권한 있습니다.
		while(isRun) {
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
