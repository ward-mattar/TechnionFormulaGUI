package main;
import java.lang.String;
import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.List;
import java.lang.NullPointerException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;

/*
 * @since 04/11/2017
 * @author wardm@campus.technion.ac.il
 */
public class CVSReader {
	public static final String DELIM = ",";
	public CVSReader(String fileName) throws FileNotFoundException {
		if(fileName == null)
			throw new NullPointerException(); // TODO: write to log
		try {
			fr = new FileReader(fileName);
		} catch(FileNotFoundException  e){
			// TODO: to log (cannot read from file)
			throw e;
		}
		try {
			fb = new BufferedReader(fr);
		} catch(Exception e){ 
			// TODO: to log and change exception
			throw e;
		}
		lines = fb.lines();
		// log on success
		currentLine = new LinkedList<>();
	}
	

	public List<String> getNextRecordLine(){
		List<String> next = new LinkedList<>();
		currentLine = next;
		try {
			String line = fb.readLine();
			if (line == null)
				return next;
			for(String item : line.split(DELIM)){
				next.add(item);
			}
		} catch(IOException e){
			e.printStackTrace();
			// TODO: log 
		}
		return next;
	}
	
	public String getNextRecordString(){
		String result = "";
		for(String item : currentLine){
			result += item + " ";
		}
		return result.trim();
	}
	
	public void done() throws IOException{
		fr.close();
	}
	private List<String> currentLine;
	private final FileReader fr;
	private final BufferedReader fb;
	private final Stream<String> lines;
}
