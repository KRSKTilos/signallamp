package komandor.signallamp.test;

import cashclient.devices.CommPort;
import komandor.signallamp.Driver;
import komandor.signallamp.LED;
import komandor.signallamp.icse.ICSE012A;

/**
 * @author krsktilos
 */
class TestSignalLamp {

    public static void main(String[] args) {
        CommPort port = null;
        try {
            port = new CommPort("/dev/ttyUSB0", 9600, 8, 1, 0, 0, 5000);
            info("test LED app start...");

            info("open port...");
            port.open();

            info("port open: " + port.isOpened());

            Driver driver = new ICSE012A(port);

            info("enter control mode");
            driver.controlMode();

            Thread.sleep(200);
            for (LED led : LED.values()) {
                driver.on(led);
                Thread.sleep(1000);
                driver.off();
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            info("!ERROR!");
            ex.printStackTrace();
        } finally {
            info("test LED app stopped");
            if (port!=null && port.isOpened()) {
                port.close();
            }
        }
    }

    private static void info(String message) {
        System.out.println(message);
    }
}
