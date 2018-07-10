package komandor.signallamp.icse;

import cashclient.devices.CommPort;
import komandor.signallamp.Driver;
import komandor.signallamp.LED;
import komandor.signallamp.utils.Utils;
import org.apache.log4j.Logger;

/**
 * Реле ICSE012A.
 * @author krsktilos
 */
public class ICSE012A implements Driver {
    private final static byte controlByte = (byte) 0x50;
    private final static byte enterByte = (byte) 0x51;
    private final static byte icse012a = (byte) 0xAB;
    private final static byte RELAY_1 = 0x0E;
    private final static byte RELAY_2 = 0x0D;
    private final static byte RELAY_3 = 0x0B;
    private final static byte RELAY_4 = 0x07;
    private final static byte OFF = 0x0F;
    private final CommPort port;
    private Logger logger;

    public ICSE012A(CommPort port) {
        this.port = port;
    }

    @Override
    public void controlMode() {
        info("enter control mode");
        try {
            info("open port");
            port.open();
            info("send contol byte...");
            port.getOutputStream().write(controlByte);
            Thread.sleep(500);
            byte answer = 0x00;
            for (int tries=0; tries<3; tries++) {
                if (port.getInputStream().available()>0) {
                    answer = (byte) port.getInputStream().read();
                    info("<< received: " + Utils.toHexString(answer));
                    break;
                } else Thread.sleep(100);
            }

            if (answer == icse012a) {
                info("relay detected, send enter byte");
                port.getOutputStream().write(enterByte);
                info("ok");
            }
            Thread.sleep(200);
            off();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void on(LED led) {
        try {
            byte relay = relay(led);
            info("Включение цвета " + led.name());
            port.getOutputStream().write(relay);
            Thread.sleep(150);
        } catch (Exception ex) {
            info("Ошибка включения цветовой индикации! " + ex);
        }
    }

    private byte relay(LED led) {
        switch (led) {
            case RED: return RELAY_1;
            case GREEN: return RELAY_2;
            case BLUE: return RELAY_3;
            case YELLOW: return RELAY_4;
            default: return OFF;
        }
    }

    @Override
    public void off() {
        try {
            info("Сброс всех реле");
            port.getOutputStream().write(OFF);
            Thread.sleep(150);
        } catch (Exception ex) {
            info("Ошибка выключения индикации! " + ex);
        }
    }

    private void info(String message) {
        if (logger != null) {
            logger.info("ICSE012A: " + message);
        } else System.out.println("ICSE012A: " + message);
    }
}
