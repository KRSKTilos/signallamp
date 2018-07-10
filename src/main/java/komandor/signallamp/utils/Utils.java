package komandor.signallamp.utils;

/**
 * @author krsktilos
 */
public class Utils {

    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(String.format("%02x ", b & 0xff));
            }
            return sb.toString().trim();
        }
    }

    public static String toHexString(byte b) {
        return toHexString(new byte[]{b});
    }
}
