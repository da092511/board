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
	
	public String getDate() {
		return this.date;
	}
	
	
	@Override
	public String toString() {
		String info = title;
		info += "\n----------------";
		info += String.format("\n%s\t\t%s", this.date, this.id);
		
		String[] content = contents.split(";");
		for(String line : content)
			info += "\n" + line;

		return info;
	}
	
}
