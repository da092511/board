package board;

public class User implements Accessible{
	private int code;
	
	private String name ;
	private String id;
	private String pw;
	
	private int openCount;
	private int writeContentCount;
	
	public User(int code, String name, String id, String pw) {
		this.code = code;
		this.name = name;
		this.id = id;
		this.pw = pw;
	}
	public User(int code, String name, String id, String pw, int openCount, int writeContentCount) {
		this.code = code;
		this.name = name;
		this.id = id;
		this.pw = pw;
		
		this.openCount = openCount;
		this.writeContentCount = writeContentCount;
	}
	
	public String getId() {
		return this.id;
	}

	public String getPw() {
		return this.pw;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getOpenCount() {
		return this.openCount;
	}
	
	public void openContent() {
		this.openCount ++;
	}
	
	public void writeContent() {
		this.writeContentCount++;
	}
	public void setWriteContent(int count) {
		this.writeContentCount = count;
	}
	
	public int getWriteContentCount() {
		return this.writeContentCount;
	}
	
	@Override
	public boolean accessible() {
		if(this.openCount >= 3)
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		String info = this.name + "[ID : " + this.id + "/PW : " + this.pw + "] code : " + this.code;
		return info;
	}
}
