package prototype;

import com.fazecast.jSerialComm.*;
import java.util.Scanner;

/**
 * Communication between Java and Arduino. In this class there are three methods
 * which are mainly used to get the Angle and the distance from Arduino.
 *
 * @author Wahab Meskinyar
 * @see UI.java;
 * @see<a doc="https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html">
 * to have a clear understanding of this library;
 * @see jSerialComm
 * @version 1.0
 * @since 12.27.2020
 *
 */
public class Serial implements Runnable {

    private static int distance, angle;
    private static boolean portConnection = false;
    private String Data = "";
    private SerialPort serialPort;

    public Serial() {
        serialPort = SerialPort.getCommPort("COM3");
        serialPort.openPort();
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
    }

    /**
     * this method is the heart of this class, since it delimits and separates
     * the angle from the distance printed into the port.
     * <p>
     * Please do not change anything in this method, since parsing might cause
     * an issue.
     *
     */
    @Override
    public void run() {
        try (Scanner data = new Scanner(serialPort.getInputStream())) {
            while (data.hasNext()) {
                Data += data.nextLine();
                if (Data.contains(".")) {
                    try {
                        distance = Integer.parseInt(Data.substring(0, Data.indexOf(",")));
                        angle = Integer.parseInt(Data.substring(Data.indexOf(",") + 1, Data.indexOf(".")));

                    } catch (Exception ex) {
                    }
                    Data = Data.substring(Data.indexOf(".") + 1);
                }
            }
            data.close();
        }
    }

    /**
     * @return returns the angle from Servo
     */
    public static int getAngle() {
        return angle;
    }

    /**
     * @return distance measured by Sonar-sensor
     */
    public static int getDistance() {
        return distance;
    }

}
