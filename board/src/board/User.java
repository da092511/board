package board;

public class User {
	private int code;
	
	private String name ;
	private String id;
	private String pw;
	
	public User(int code, String name, String id, String pw) {
		this.code =code;
		this.name =name;
		this.id = id;
		this.pw = pw;
	}
	
	public String getId() {
		return this.id;
	}

	public String getPw() {
		return this.pw;
	}
}
