package board;

public class Board {
	private String id;
	
	private String title;
	private String date;
	private String contents;
	
	public Board(String title, String id, String date, String contents) {
		this.contents = contents;
		this.title = title;
		this.date = date;
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getUserId() {
		return this.id;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		String info = title;
		info += "\n---------------------------";
		info += String.format("\n%s\t%s", this.date, this.id);
		info += "\n---------------------------";
		String[] content = contents.split("<br>");
		for(String line : content)
			info += "\n" + line;

		return info;
	}
	
}
