package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import gnu.io.CommPortIdentifier;
import static gnu.io.CommPortIdentifier.getPortIdentifiers;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Enumeration;
import java.util.TooManyListenersException;


public final class SerialReader implements SerialPortEventListener {
    SerialPort serialPort;

    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
    };

    private BufferedReader input;
    private static final int SLEEP_TIME = 1000; // ms
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 230400;
    private static final int ATTEMPTS = 3;

    public void initialize() {
        serialPort = null;
        CommPortIdentifier portId = null;
        Enumeration portEnum = getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
           return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    
    public SerialReader() throws InterruptedException, SerialReaderError {
        int turn = 1;
        while(turn++ <= ATTEMPTS) {
            for(Integer portNumber = 3; portNumber <= 15; portNumber++){
                // looking for valid COM port - Windows only
                PORT_NAMES[2] = "COM" + portNumber.toString();        
                try {
                    initialize();
                    if(serialPort != null)
                        break;
                } catch (Exception e){

                }
            }
            if(serialPort != null) {
                System.out.println("Serial COM intiated");
                return;
            } else {
                System.out.printf("Serial COM could not be intiated... attempt: %d\n", turn);
            }
            sleep(SLEEP_TIME);
        }
        //throw new SerialReaderError();
    }
 
     public synchronized String readLine(){
        if (input == null)
            return null;
        try {
            return input.readLine();
        } catch(IOException e){
            return "";
        }
    }
}

