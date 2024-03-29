package board;

public class Board {
	private int id;
	
	private String title;
	private String date;
	private String contents;
	
	public Board(String title, String date, String contents) {
		this.contents = contents;
		this.title = title;
		this.date = date;
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
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
