import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BasicTests extends UiAutomatorTestCase {

    UiDevice uiDevice;

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
        try {
            Utils.openApp(getUiDevice());
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the action bar buttons exist. These buttons are pause, play,
     * refresh, add, and options<br>
     * Note that either pause or play buttons will exist, but not both
     * 
     * @param returnTag the TAG to reset upon completion of this method
     */
    public void checkActionBarButtonsExists(String returnTag) {
        TAG = "Check ActionBar Buttons";
        if ((pause == null) || !pause.exists()) {
            Log.log(TAG, "Pause button doesn't exist");
        }

        if ((play == null) || !play.exists()) {
            Log.log(TAG, "Play button doesn't exist");
        }

        if ((pause != null) && (play != null) && (play.exists() && pause.exists())) {
            Log.log(TAG, "Both play and pause button exist...");
        }

        if ((refresh == null) || !refresh.exists()) {
            Log.log(TAG, "Refresh button doesn't exist");
        }

        if ((add == null) || !add.exists()) {
            Log.log(TAG, "Add button doesn't exist");
        }

        if ((options == null) || !options.exists()) {
            Log.log(TAG, "Options button doesn't exist");
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
        Utils.checkSilentZoneOpen(TAG);

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
                Log.log(TAG, "About to click pause button");
                pause.click();
            } catch (UiObjectNotFoundException e) {
                Log.log(TAG, "Couldn't find pause button");
            }
        } else if (play.exists()) {
            try {
                Log.log(TAG, "About to click play button");
                pause.click();
            } catch (UiObjectNotFoundException e) {
                Log.log(TAG, "Couldn't find play button");
            }
        } else {
            Log.log(TAG, "Couldn't find a play or pause button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the refresh button
        try {
            Log.log(TAG, "About to click refresh button");
            refresh.click();
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find refresh button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the add button
        try {
            Log.log(TAG, "About to click add button");
            if (!add.clickAndWaitForNewWindow()) {
                Log.log(TAG, "Nothing happened when we clicked add");
            }
            // In the add activity, press back button to go back to the main
            // activity
            uiDevice.pressBack();
            // Make sure we're still in silentzone
            if (!Utils.checkSilentZoneOpen(TAG)) {
                fail("We're no longer in the silentZone App");
            }
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find add button");
        }

        checkActionBarButtonsExists(TAG);

        // Press the options button
        try {
            Log.log(TAG, "About to click options menu button");
            options.click();
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find the options button");
        }

        // Check if menu pops up
        UiObject settings = new UiObject(new UiSelector().text("Settings"));
        UiObject quit = new UiObject(new UiSelector().text("Quit"));

        if ((settings == null) || !settings.exists()) {
            Log.log(TAG, "Couldn't find settings button");
        }
        if ((quit == null) || !quit.exists()) {
            Log.log(TAG, "Couldn't find Quit button");
        }

        // TODO: Put this in its own method
        // Enter settings
        try {
            if (!settings.clickAndWaitForNewWindow()) {
                Log.log(TAG, "Nothing happened when we clicked settings");
            }
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find settings button");
        }

        // Return with back button
        uiDevice.pressBack();
        // Hopefully we're in the right place
        if (!Utils.checkSilentZoneOpen(TAG)) {
            fail("We're no longer in the silentZone App");
        }
        checkActionBarButtonsExists(TAG);

        // Enter settings
        try {
            options.click();
            if (!settings.clickAndWaitForNewWindow()) {
                Log.log(TAG, "Nothing happened when we clicked settings");
            }
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find options or settings");
        }

        // Return with action bar thing
        UiObject returnWithActionBar = new UiObject(new UiSelector().text("SilentZone").resourceId(
                "android:id/action_bar_title"));
        // Return to main activity
        try {
            if (!returnWithActionBar.clickAndWaitForNewWindow()) {
                Log.log(TAG, "Nothing happened when we clicked the silentzone actionbar");
            }
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't find Action bar return to main activity button");
        }

        // Make sure we're in the right place
        if (!Utils.checkSilentZoneOpen(TAG)) {
            fail("We're no longer in the silentZone App");
        }
        checkActionBarButtonsExists(TAG);

        // Quit now!
        try {
            options.click();
            quit.click();
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "Couldn't quit");
        }

        // Shouldn't be able to find any silentzone objects now
        if (Utils.checkSilentZoneOpen(TAG)) {
            fail("We didn't actually quit");
        }
    }

}
