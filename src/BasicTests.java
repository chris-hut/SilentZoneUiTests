import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BasicTests extends UiAutomatorTestCase {

    private final static String SILENTZONE_PACKAGE_NAME = "ca.silentzone.silentzone";
    UiDevice uiDevice;

    // Logging variables
    private Log l;

    /**
     * The max number of times we're going to swipe while looking for the
     * SilentZone App.
     */
    private static final int MAX_APP_PAGES = 10;
    /**
     * Used by logging class to identify a TAG for logcat. Should be changed by
     * every method that uses logging
     */
    String TAG = "Basic Tests";

    // Some basic UiObjects
    /**
     * Pause button in action bar<br>
     * Won't be present if play button is
     */
    UiObject pause;
    /**
     * Play button in action bar.<br>
     * Won't be present if pause button is.
     */
    UiObject play;
    /** Refresh button in action bar */
    UiObject refresh;
    /** Add zone button in action bar */
    UiObject add;
    /** Options button in action bar */
    UiObject options;

    protected void setUp() {
        uiDevice = getUiDevice();
        l = Log.getInstance();
        try {
            openApp();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openApp() throws UiObjectNotFoundException {
        TAG = "Test Open App";
        // Make sure were on the home screen
        uiDevice.pressHome();

        // Open app drawer
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        allAppsButton.clickAndWaitForNewWindow();

        /*
         * Since were on 4.4 with Google Experience Launcher we don't have no
         * app drawer but you can press it just for fun
         */
        try {
            /*
             * UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
             * appsTab.click();
             */
            if (false)
                throw new UiObjectNotFoundException("k");
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Device was not using a launcher with an apps tab");
        }

        // Next, in the apps tabs, we can simulate a user swiping until
        // they come to the Settings app icon. Since the container view
        // is scrollable, we can use a UiScrollable object.
        UiScrollable appViews = new UiScrollable(new UiSelector()
                .scrollable(true));

        // Set the swiping mode to horizontal (the default is vertical)
        appViews.setAsHorizontalList();

        // Create a UiSelector to find the Settings app and simulate
        // a user click to launch the app.
        UiObject silentZoneApp = new UiObject(
                new UiSelector().className(android.widget.TextView.class.getName()).text(
                        "SilentZone"));
        /*
         * appViews.getChildByText(new UiSelector()
         * .className(android.widget.TextView.class.getName()), "SilentZone");
         */
        for (int i = 0; i < MAX_APP_PAGES; i++) {
            if (silentZoneApp.exists()) {
                silentZoneApp.clickAndWaitForNewWindow();
                break;
            }
            appViews.scrollForward();
        }

        // Validate that the package name is the expected one
        checkSilentZoneOpen(TAG);
    }

    /**
     * Checks if the silentzone application is currently open by looking for a
     * UiObject with the package name of "ca.silentzone.silentzone". Will fail
     * test if it cannot find one.
     * 
     * @param returnTAG the TAG to set upon completion of this method
     */
    private boolean checkSilentZoneOpen(String returnTAG) {
        TAG = "Check SilentZone Open";
        UiObject silentZoneValidation = new UiObject(
                new UiSelector().packageName(SILENTZONE_PACKAGE_NAME));
        if (!silentZoneValidation.exists()) {
            l.log(TAG, "Unable to detect a SilentZone object");
            return false;
        }
        return true;
        // assertTrue("Unable to detect SilentZone",
        // silentZoneValidation.exists());
    }

    /**
     * Checks if the action bar buttons exist. These buttons are pause, play,
     * refresh, add, and options<br>
     * Note that either pause or play buttons will exist, but not both
     * 
     * @param returnTag the TAG to reset upon completion of this method
     */
    private void checkActionBarButtonsExists(String returnTag) {
        TAG = "Check ActionBar Buttons";
        if ((pause == null) || !pause.exists()) {
            l.log(TAG, "Pause button doesn't exist");
        }

        if ((play == null) || !play.exists()) {
            l.log(TAG, "Play button doesn't exist");
        }

        if ((pause != null) && (play != null) && (play.exists() && pause.exists())) {
            l.log(TAG, "Both play and pause button exist...");
        }

        if ((refresh == null) || !refresh.exists()) {
            l.log(TAG, "Refresh button doesn't exist");
        }

        if ((add == null) || !add.exists()) {
            l.log(TAG, "Add button doesn't exist");
        }

        if ((options == null) || !options.exists()) {
            l.log(TAG, "Options button doesn't exist");
        }

        TAG = returnTag;
    }

    public void testButtons() {
        /*
         * Buttons available on home screen have content descriptions of: Pause,
         * Refresh Zone, Add Zone, and More options. and are located on the
         * action bar
         */

        // Set up that tag
        TAG = "Test All Buttons";

        // Make sure we're in the right app
        checkSilentZoneOpen(TAG);

        // Make some objects
        play = new UiObject(new UiSelector().description("Play"));
        pause = new UiObject(new UiSelector().description("Pause"));
        refresh = new UiObject(new UiSelector().description("Refresh Zone"));
        add = new UiObject(new UiSelector().description("Add Zone"));
        options = new UiObject(new UiSelector().description("More options"));

        // Make sure they exist
        checkActionBarButtonsExists(TAG);

        // Press pause/play button
        if (pause.exists()) {
            try {
                l.log(TAG, "About to click pause button");
                pause.click();
            } catch (UiObjectNotFoundException e) {
                l.log(TAG, "Couldn't find pause button");
            }
        } else if (play.exists()) {
            try {
                l.log(TAG, "About to click play button");
                pause.click();
            } catch (UiObjectNotFoundException e) {
                l.log(TAG, "Couldn't find play button");
            }
        } else {
            l.log(TAG, "Couldn't find a play or pause button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the refresh button
        try {
            l.log(TAG, "About to click refresh button");
            refresh.click();
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find refresh button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the add button
        try {
            l.log(TAG, "About to click add button");
            if (!add.clickAndWaitForNewWindow()) {
                l.log(TAG, "Nothing happened when we clicked add");
            }
            // In the add activity, press back button to go back to the main
            // activity
            uiDevice.pressBack();
            // Make sure we're still in silentzone
            if (!checkSilentZoneOpen(TAG)) {
                fail("We're no longer in the silentZone App");
            }
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find add button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the options button
        try {
            l.log(TAG, "About to click options menu button");
            options.click();
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find the options button");
        }

        // Check if menu pops up
        UiObject settings = new UiObject(new UiSelector().text("Settings"));
        UiObject quit = new UiObject(new UiSelector().text("Quit"));

        if ((settings == null) || !settings.exists()) {
            l.log(TAG, "Couldn't find settings button");
        }
        if ((quit == null) || !quit.exists()) {
            l.log(TAG, "Couldn't find Quit button");
        }

        // TODO: Put this in its own method
        // Enter settings
        try {
            if (!settings.clickAndWaitForNewWindow()) {
                l.log(TAG, "Nothing happened when we clicked settings");
            }
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find settings button");
        }

        // Return with back button
        uiDevice.pressBack();
        // Hopefully we're in the right place
        if (!checkSilentZoneOpen(TAG)) {
            fail("We're no longer in the silentZone App");
        }
        checkActionBarButtonsExists(TAG);

        // Enter settings
        try {
            options.click();
            if (!settings.clickAndWaitForNewWindow()) {
                l.log(TAG, "Nothing happened when we clicked settings");
            }
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find options or settings");
        }

        // Return with action bar thing
        UiObject returnWithActionBar = new UiObject(new UiSelector().text("SilentZone").resourceId(
                "android:id/action_bar_title"));
        // Return to main activity
        try {
            if (!returnWithActionBar.clickAndWaitForNewWindow()) {
                l.log(TAG, "Nothing happened when we clicked the silentzone actionbar");
            }
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't find Action bar return to main activity button");
        }

        // Make sure we're in the right place
        if (!checkSilentZoneOpen(TAG)) {
            fail("We're no longer in the silentZone App");
        }
        checkActionBarButtonsExists(TAG);

        // Quit now!
        try {
            options.click();
            quit.click();
        } catch (UiObjectNotFoundException e) {
            l.log(TAG, "Couldn't quit");
        }

        // Shouldn't be able to find any silentzone objects now
        if (checkSilentZoneOpen(TAG)) {
            fail("We didn't actually quit");
        }
    }

    private static class Log {

        private static Log l = null;

        public static Log getInstance() {
            if (l == null) {
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

        /**
         * Logs the message to system.out as well as logcat
         * 
         * @param tag the logcat tag
         * @param msg the message to log
         */
        public void log(String tag, String msg) {
            System.out.println(msg);
            android.util.Log.d(tag, msg);
        }
    }
}
