package komandor.signallamp.test;

import komandor.signallamp.Driver;
import komandor.signallamp.LED;
import org.apache.log4j.Logger;

/**
 * @author krsktilos
 */
public class TestDriver implements Driver {
    private Logger logger;

    public TestDriver() {
        controlMode();
    }

    @Override
    public void controlMode() {
        info("Переход в режим управления");
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void on(LED led) {
        info("Включение цвета: " + led.name());
    }

    @Override
    public void off() {
        info("Выключение цветовой индикации");
    }

    private void info(String message) {
        if (logger != null) {
            logger.info("SignalLamp (TEST): " + message);
        } else System.out.println("SignalLamp (TEST): " + message);
    }
}
