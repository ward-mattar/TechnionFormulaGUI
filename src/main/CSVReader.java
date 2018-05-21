package main;

import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.util.Arrays.asList;

/*
 * @since 04/11/2017
 * @author wardm@campus.technion.ac.il
 */
public class CSVReader {
	public static final String DELIM = ",";
	public CSVReader(String fileName) throws FileNotFoundException {
		if(fileName == null)
			throw new NullPointerException(); 
		try {
			fr = new FileReader(fileName);
		} catch(FileNotFoundException  e){
			throw e;
		}
		try {
			fb = new BufferedReader(fr);
		} catch(Exception e){ 
			throw e;
		}
		lines = fb.lines();
		currentLine = new LinkedList<>();
	}
	

	public List<String> getNextRecordLine() throws IOException {
		List<String> next = new LinkedList<>();
		currentLine = next;
                String line = fb.readLine();
                if (line == null)
                    return next;
                next.addAll(asList(line.split(DELIM)));
		return next;
	}
	
	public String getNextRecordString(){
		return currentLine == null ? "" : 
                        currentLine.stream()
                        .map((item) -> item + " ")
                        .reduce("", String::concat)
                        .trim();
	}
	
        public Stream<String> getAllLines(){
            return lines;
        }
        
	public void done() throws IOException{
		if(fr != null)
                    fr.close();
	}
	private List<String> currentLine;
	private final FileReader fr;
	private final BufferedReader fb;
	private final Stream<String> lines;
}
