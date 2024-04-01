package board;

public class User implements Accessible{
	private int code;
	
	private String name ;
	private String id;
	private String pw;
	
	private int openContent;
	
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
	
	public String getName() {
		return this.name;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void openContent() {
		this.openContent ++;
	}
	
	@Override
	public boolean accessible() {
		if(this.openContent >= 3)
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		String info = this.name + "[" + this.id + "/" + this.pw + "]" + this.code;
		return info;
	}
}
