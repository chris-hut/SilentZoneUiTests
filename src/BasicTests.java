import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BasicTests extends UiAutomatorTestCase {

    UiDevice uiDevice;

    protected void setUp() {
        uiDevice = getUiDevice();
    }

    public void testOpenApp() throws UiObjectNotFoundException {
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
            Log.d("Launcher", "Device was not using a launcher with an apps tab");
            System.out.println("Device was not using a launcher with an apps tab");
        }

     // Next, in the apps tabs, we can simulate a user swiping until
        // they come to the Settings app icon.  Since the container view 
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
        UiObject settingsValidation = new UiObject(new UiSelector()
           .packageName("ca.silentzone.silentzone"));
        assertTrue("Unable to detect SilentZone", 
           settingsValidation.exists());
    }
}
