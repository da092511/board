package board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private String fileName = "Cbs.txt";
	private File file = new File(fileName);
	
	private FileWriter fw;
	private FileReader fr ;
	private BufferedReader br;
	
	public void saveData(String data) {
		try {
			fw = new FileWriter(file);
			fw.write(data);
			
			fw.close();
			System.out.println("save Complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String loadData() {
		String info = "";
		if(file.exists()) {
			
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				while(br.ready()) {
					info += br.readLine() +"\n";
				}
				
				fr.close();
				br.close();
				System.out.println("load complete");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("load failed");
			}
			
		}else 
			return null;
		
		
		return info;
	}
	
}
