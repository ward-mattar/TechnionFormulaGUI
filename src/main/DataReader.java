package main;

import java.util.LinkedList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


/*
 * @since 04/11/2017
 * @author wardm@campus.technion.ac.il
 */
public class DataReader {
	public static final String DELIM = ",";

	public DataReader(String fileName) throws InterruptedException, SerialReaderException {
            FileWriter temp = null;
            try {
                temp = new FileWriter(fileName);
                System.out.printf("File %s successfully opened for data loging\r\n", fileName);
            } catch(IOException ex){
                System.out.printf("Could not open file %s for logging\r\n", fileName);
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
                next.addAll(Arrays.asList(line.split(DELIM)));
		return next;
	}
	
	public String getNextLineString(){
		String result = "";
                result = currentLine.stream().map((item) -> item + " ").reduce(result, String::concat);
		return result.trim();
	}
        
	public void log(final String m)  {
            if (fw == null)
                return;
            try {
                fw.write(m);
                fw.flush();
            } catch (IOException ex) {
            }
        }
	public void done() throws IOException{
            fw.flush();
            fw.close();
	}
        public List<String> getCurrentLine(){
            return currentLine;
        }
        public String getPortName(){
            return dr != null ? dr.getWindowsPortName() : "";
        }
	private List<String> currentLine;
	private final FileWriter fw;
	private final SerialReader dr;
}
