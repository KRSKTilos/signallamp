package komandor.signallamp;

import org.apache.log4j.Logger;

/**
 * @author krsktilos
 */
public interface Driver {

    /**
     * Перевод реле в режим управления.
     */
    void controlMode();

    /**
     * Выставление логгера.
     * @param logger логгер
     */
    void setLogger(Logger logger);

    /**
     * Включение цветовой индикации.
     * @param led цвет
     */
    void on(LED led);

    /**
     * Выключение всех цветовых индикаций.
     */
    void off();
}
