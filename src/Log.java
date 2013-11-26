
public class Log {

    /**
     * Logs the message to system.out
     * 
     * @param msg the message to log
     */
    public static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * Logs the message to system.out as well as logcat
     * 
     * @param tag the logcat tag
     * @param msg the message to log
     */
    public static void log(String tag, String msg) {
        System.out.println(msg);
        android.util.Log.d(tag, msg);
    }
}
