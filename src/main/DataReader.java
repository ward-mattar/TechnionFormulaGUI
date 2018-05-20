package main;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @since 04/11/2017
 * @author wardm@campus.technion.ac.il
 */
public class DataReader {
	public static final String DELIM = ",";
	public DataReader(String fileName) throws InterruptedException, SerialReaderError {
                FileWriter temp = null;
                try {
                    temp = new FileWriter(fileName);
                    System.out.printf("File %s succesfuly opened\n", fileName);
                } catch(IOException  ex){
                    System.out.printf("Could not open file %s for logging\n", fileName);
                    System.out.printf("Exception: \n%s\n", ex.getMessage());
                    Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
                }   
                fw = temp;
                dr = new SerialReader();
		currentLine = new LinkedList<>();
	}
	
	public List<String> getNextLineList(){
		List<String> next = new LinkedList<>();
		currentLine = next;
		String line = dr.readLine();
                if (line == null)
                        return next;
                for(String item : line.split(DELIM)){
                        next.add(item);
                }
		 
		return next;
	}
	
	public String getNextLineString(){
		String result = "";
                result = currentLine.stream().map((item) -> item + " ").reduce(result, String::concat);
		return result.trim();
	}
	
	public void done() throws IOException{
		fw.close();
	}
        public List<String> getCurrentLine(){
            return currentLine;
        }
        
	private List<String> currentLine;
	private final FileWriter fw;
	private final SerialReader dr;
}
