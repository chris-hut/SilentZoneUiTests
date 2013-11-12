public class Log {

    private static Log l = null;
    
    public static Log getInstance(){
        if(l == null){
            l = new Log();
        }
        return l;
    }
    
    /**
     * Logs the message to system.out
     * 
     * @param msg the message to log
     */
    public void log(String msg) {
        System.out.println(msg);
    }

    /** Logs the message to system.out as well as logcat 
     * @param tag the logcat tag
     * @param msg the message to log*/
    public void log(String tag, String msg) {
        System.out.println(msg);
        android.util.Log.d(tag, msg);
    }
}
