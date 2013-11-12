
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BasicTests extends UiAutomatorTestCase {

    private final static String SILENTZONE_PACKAGE_NAME = "ca.silentzone.silentzone";
    UiDevice uiDevice;
    Log l;

    protected void setUp() {
        uiDevice = getUiDevice();
        l = Log.getInstance();
    }

    public void stestOpenApp() throws UiObjectNotFoundException {
        // Make sure were on the home screen
        uiDevice.pressHome();

        // Open app drawer
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        allAppsButton.clickAndWaitForNewWindow();

        /*
         * Since were on 4.4 with Google Experience Launcher we don't have no
         * app drawer but let's press it just for fun
         */
        try {
            UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
            appsTab.click();
        } catch (UiObjectNotFoundException e) {
            l.log("Launcher", "Device was not using a launcher with an apps tab");
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
        UiObject settingsApp = appViews.getChildByText(new UiSelector()
                .className(android.widget.TextView.class.getName()),
                "SilentZone");
        settingsApp.clickAndWaitForNewWindow();

        // Validate that the package name is the expected one
        checkSilentZoneOpen();
    }

    /**
     * Checks if the silentzone application is currently open by looking for a
     * UiObject with the package name of "ca.silentzone.silentzone". Will fail
     * test if it cannot find one.
     */
    public void checkSilentZoneOpen() {
        UiObject silentZoneValidation = new UiObject(
                new UiSelector().packageName(SILENTZONE_PACKAGE_NAME));
        assertTrue("Unable to detect SilentZone", silentZoneValidation.exists());
    }

    /**
     * Goes through the array of UiObjects and makes sure they exist. Will fail
     * test if they don't exist
     * @param objects the array of objects to check
     */
    public void checkUiObjectsExist(UiObject[] objects) {
        for(UiObject o : objects){
            assertTrue("Unable to detect a UiObject", o.exists());
        }
    }

    public void testButtons() {
        /*
         * Buttons available on home screen have content descriptions of: Pause,
         * Refresh Zone, Add Zone, and More options. and are located on the
         * action bar
         */

        // Make sure we're in the right app
        checkSilentZoneOpen();

        // Make some objects
        UiObject play, pause, refresh, add, options;
        pause = new UiObject(new UiSelector().description("Pause"));
        refresh = new UiObject(new UiSelector().description("Refresh Zone"));
        add = new UiObject(new UiSelector().description("Add Zone"));
        options = new UiObject(new UiSelector().description("More options"));

        
        // Make sure they exist
        UiObject[] objectsPause = {pause, refresh, add, options};
        checkUiObjectsExist(objectsPause);
        
        // Press pause button
        try {
            pause.click();
        } catch (UiObjectNotFoundException e) {
           l.log("Test Buttons", "Couldn't find pause button");
        }
        
        play = new UiObject(new UiSelector().description("Play"));
        UiObject[] objectsPlay = {play, refresh, add, options};
        checkUiObjectsExist(objectsPlay);
    }
}
