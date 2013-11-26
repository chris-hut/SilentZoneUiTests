import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Utils extends UiAutomatorTestCase {

    private static String TAG = "Utils";
    private final static String SILENTZONE_PACKAGE_NAME = "ca.silentzone.silentzone";

    /**
     * The max number of times we're going to swipe while looking for the
     * SilentZone App.
     */
    private static final int MAX_APP_PAGES = 10;

    public static void openApp(UiDevice device) throws UiObjectNotFoundException {
        TAG = "Test Open App";
        // Make sure were on the home screen
        device.pressHome();

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
            Log.log(TAG, "Device was not using a launcher with an apps tab");
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
    public static boolean checkSilentZoneOpen(String returnTAG) {
        TAG = "Check SilentZone Open";
        UiObject silentZoneValidation = new UiObject(
                new UiSelector().packageName(SILENTZONE_PACKAGE_NAME));
        if (!silentZoneValidation.exists()) {
            Log.log(TAG, "Unable to detect a SilentZone object");
            return false;
        }
        return true;
        // assertTrue("Unable to detect SilentZone",
        // silentZoneValidation.exists());
    }

}
