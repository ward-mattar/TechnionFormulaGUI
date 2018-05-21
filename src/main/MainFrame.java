package main;

import static java.awt.EventQueue.invokeLater;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Double.parseDouble;
import static java.lang.System.exit;
import java.util.List;
import static java.util.logging.Logger.getLogger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;


public final class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        setUpOutputStream();
        initComponents();
        inialize();
    }

    public void issueErrorMessage(final String msg){
        System.err.print(msg);
        showMessageDialog(errorPanel, msg, "Error", JOptionPane.OK_OPTION);
    }
    
    public void setUpOutputStream(){
        try {
            PrintStream s = makeStream(LOG_PATH);
            System.setOut(s);
            System.setErr(s);
        } catch (FileNotFoundException ex) {
            System.out.printf("Could not open %s for logging\r\n", LOG_PATH);
        }
    }
    
    public void inialize(){
        setSize(1360,830);
        setResizable(false);
        try {
            // Uncomment following line to simulate reading data from a file 
            // presentationReader = new CSVReader("presentation.csv");
            dataReader = new DataReader(DATA_LOG_PATH);
        } catch (SerialReaderException | InterruptedException e){
            issueErrorMessage("Could not detect COM port!\r\n" + e.toString());
            exit(0);
        } catch(Exception e){
            issueErrorMessage("Unexpected exception caught!\r\n" + e.toString());
            exit(0);
        } catch (Error er){
            issueErrorMessage("Error: " + er.toString());
            exit(0);
        }
        
        System.out.printf("Initializing COM port was successful, using: %s\r\n", dataReader.getPortName());
        System.out.printf("File %s used for logging data captured\r\n", DATA_LOG_PATH);
        System.out.printf("Started listening to data\r\n");
        
        timer = new Timer(DELAY, (ActionEvent e) -> {
            List<String> currentInfo;
            if (presentationReader != null){
                try {
                    currentInfo = presentationReader.getNextRecordLine();
                } catch (IOException ex) {
                    issueErrorMessage("Could not read from presentation file!\r\n" + ex.getMessage());
                    timer.stop();
                    return;
                }
            } else if (dataReader != null){
                currentInfo = dataReader.getNextLineList();
            } else {
                timer.stop();
                return;
            }
    
            if(currentInfo == null)
                return;
            
            if(currentInfo.size() != FIELDS_NUMBER){
                System.out.println(dataReader.getNextLineString());
                return;
            }
            
            dataReader.log(dataReader.getNextLineString());
            
            susRF.setText(currentInfo.get(0));
            susRR.setText(currentInfo.get(1));
            susLF.setText(currentInfo.get(2));
            susLR.setText(currentInfo.get(3));
            wheelAng.setValue(parseDouble(currentInfo.get(4)));
            frontBrake.setText(currentInfo.get(5));
            rearBrake.setText(currentInfo.get(6));
            acceleration.setToolTipText("ax: " + currentInfo.get(7) +
                    ", ay: " + currentInfo.get(8));
            double rpmValue = parseDouble(currentInfo.get(9));
            rpmValue += parseDouble(currentInfo.get(10));
            rpmValue += parseDouble(currentInfo.get(11));
            rpmValue += parseDouble(currentInfo.get(12));
            rpmValue /= 4.0;
            rpm.setValue(rpmValue);
            velocity.setValue(rpmValue);
        });
        timer.start();
    }
    
    private static final String LOG_PATH = "log.txt";
    private static final String DATA_LOG_PATH = "data.txt";
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // checking that the SteelSeries, Lightbulb and RXTX classes are found
        try {
            Class.forName("eu.hansolo.steelseries.gauges.Radial1Vertical");
        } catch(ClassNotFoundException e) {
            issueErrorMessage("Could not find SteelSeries library!\r\n" + e.getMessage());
            exit(0);
        }
        try {
            Class.forName("eu.hansolo.lightbulb.LightBulb");
        } catch(ClassNotFoundException e) {
            issueErrorMessage("Could not find LightBulb library!\r\n" + e.getMessage());
            exit(0);
        }
    
        try {
            Class.forName("gnu.io.RXTXVersion");
        } catch(ClassNotFoundException e) {
            issueErrorMessage("Could not find RXTX library!\r\n" + e.getMessage());
            exit(0);
        }
        
        try {
            System.loadLibrary("rxtxSerial");
        } catch (UnsatisfiedLinkError e) {
            if(!e.getMessage().contains("already")) {
                issueErrorMessage("Native code for rxtxSerial.dll failed to load!\r\n" + e + 
                        "\r\nPlease make sure rxtxSerial.dll is found in environmental variable PATH:"
                          + Pattern.compile(";").splitAsStream(System.getProperty("java.library.path"))
                                                .map(l -> l +"\r\n")
                                                .reduce("", String::concat));
                exit(0);
            }
        } 

        sWTToolkitHandler1 = new org.pushingpixels.trident.swt.SWTToolkitHandler();
        velocity = new eu.hansolo.steelseries.gauges.Radial4Lcd();
        digitalRadialLcd1 = new eu.hansolo.steelseries.gauges.DigitalRadialLcd();
        rpm = new eu.hansolo.steelseries.gauges.Radial1Vertical();
        acceleration = new eu.hansolo.steelseries.gauges.FrameRectangular();
        jLabel1 = new javax.swing.JLabel();
        wheelAng = new eu.hansolo.steelseries.gauges.Level();
        susLF = new java.awt.Label();
        susRF = new java.awt.Label();
        susRR = new java.awt.Label();
        susLR = new java.awt.Label();
        jLabel2 = new javax.swing.JLabel();
        gear = new javax.swing.JTextField();
        oilAlert = new eu.hansolo.lightbulb.LightBulb();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        frontBrake = new java.awt.Label();
        rearBrake = new java.awt.Label();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        velocity.setDoubleBuffered(true);
        velocity.setName("velocity"); // NOI18N
        velocity.setTitle("Avg Speed");
        velocity.setUnitString("Km/h");

        digitalRadialLcd1.setName("engineTemp"); // NOI18N
        digitalRadialLcd1.setTitle("Engine Temprature");

        rpm.setName("rpmAvg"); // NOI18N
        rpm.setTitle("Engine RPM");

        acceleration.setToolTipText("");
        acceleration.setBackgroundColor(eu.hansolo.steelseries.tools.BackgroundColor.LIGHT_GRAY);
        acceleration.setName("acc"); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon("resources\\f1_up.png")); // NOI18N

        wheelAng.setName("wheelAng"); // NOI18N

        susLF.setName("susLF"); // NOI18N
        susLF.setText("0");

        susRF.setName("susRF"); // NOI18N
        susRF.setText("0");

        susRR.setName("susRR"); // NOI18N
        susRR.setText("0");

        susLR.setName("susLR"); // NOI18N
        susLR.setText("0");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gear");

        gear.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gear.setText("1");
        gear.setName("gear"); // NOI18N

        oilAlert.setGlowColor(new java.awt.Color(255, 0, 0));
        oilAlert.setName("oilAlert"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Oil Presure");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Wheels Suspension");

        frontBrake.setName("frontBrake"); // NOI18N
        frontBrake.setText("0");

        rearBrake.setName("rearBrake"); // NOI18N
        rearBrake.setText("0");

        label1.setText("Brakes:");

        label2.setName(""); // NOI18N
        label2.setText("Rear");

        label3.setName(""); // NOI18N
        label3.setText("Front");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rpm, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(gear, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acceleration, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wheelAng, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(digitalRadialLcd1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(velocity, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(156, 156, 156)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(oilAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(59, 59, 59)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(susLF, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                    .addComponent(susLR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(4, 4, 4)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(susRR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(susRF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rearBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(frontBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)))
                        .addGap(486, 486, 486))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(639, 639, 639)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(acceleration, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(velocity, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(148, 148, 148))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(rpm, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(158, 158, 158)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(oilAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(gear)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(25, 25, 25)
                                            .addComponent(wheelAng, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(digitalRadialLcd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(94, 94, 94))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel1)))
                            .addGap(5, 5, 5))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(201, 201, 201)
                            .addComponent(susLF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(susLR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(88, 88, 88)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(susRF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(susRR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rearBrake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frontBrake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jLabel2.getAccessibleContext().setAccessibleDescription("");
        label2.getAccessibleContext().setAccessibleName("Rear");

        pack();
    }// </editor-fold>                        
                                    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }  
    protected PrintStream makeStream(String name) throws FileNotFoundException {
       return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)), true);
   }
   public void windowClosed(WindowEvent e) {
       try {
           dataReader.done();
       } catch(IOException e2){
           
       }
       exit(0);
   }
    final JPanel errorPanel = new JPanel();
    private CSVReader presentationReader;
    private DataReader dataReader;
    private Timer timer;
    public final static int FIELDS_NUMBER = 13;
    final static int DELAY = 500;
    // Variables declaration - do not modify                     
    private eu.hansolo.steelseries.gauges.FrameRectangular acceleration;
    private eu.hansolo.steelseries.gauges.DigitalRadialLcd digitalRadialLcd1;
    private java.awt.Label frontBrake;
    private javax.swing.JTextField gear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private eu.hansolo.lightbulb.LightBulb oilAlert;
    private java.awt.Label rearBrake;
    private eu.hansolo.steelseries.gauges.Radial1Vertical rpm;
    private org.pushingpixels.trident.swt.SWTToolkitHandler sWTToolkitHandler1;
    private java.awt.Label susLF;
    private java.awt.Label susLR;
    private java.awt.Label susRF;
    private java.awt.Label susRR;
    private eu.hansolo.steelseries.gauges.Radial4Lcd velocity;
    private eu.hansolo.steelseries.gauges.Level wheelAng;
    // End of variables declaration                   
}
