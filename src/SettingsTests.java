import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class SettingsTests extends UiAutomatorTestCase {

    private static final String TAG = "SettingsTest";

    @Override
    protected void setUp() {
        try {
            Utils.openApp(getUiDevice());
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void testSmokeSettingsButtons() {
        UiObject options = new UiObject(new UiSelector().description("More options"));

        try {
            options.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open drop down options");
        }

        UiObject settings = new UiObject(new UiSelector().text("Settings"));
        try {
            settings.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open settings");
        }

        // At this point we're in settings now
        UiObject chooseDefaultSettings, polling, dissmissableNotification, startAtBootup, redoTutorial, deleteZones, feedback, version;
        UiScrollable settingsScroller = new UiScrollable(new UiSelector().scrollable(true));
        settingsScroller.setAsVerticalList();

        try {
            settingsScroller.scrollToBeginning(10);
        } catch (UiObjectNotFoundException e) {
            // We could just have a huge screen/small DPI and there is nothing
            // to scroll
            Log.log(TAG, "Nothing to scroll in settings");
        }

        chooseDefaultSettings = new UiObject(new UiSelector().text("Choose Default Settings")
                .resourceId("android:id/title"));

        try {
            chooseDefaultSettings.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Done").resourceId(
                    "ca.silentzone.silentzone:id/doneButton")).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open default settings by title");
        }

        chooseDefaultSettings = new UiObject(new UiSelector().textContains("Your phone settings")
                .resourceId("android:id/summary"));
        try {
            chooseDefaultSettings.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Done").resourceId(
                    "ca.silentzone.silentzone:id/doneButton")).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open default settings by summary");
        }

        polling = new UiObject(new UiSelector().text("Polling Rate").resourceId("android:id/title"));
        try {
            polling.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Cancel")).click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open polling settings dialog by title");
        }

        polling = new UiObject(new UiSelector().textContains("How often SilentZone").resourceId(
                "android:id/summary"));
        try {
            polling.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Cancel")).click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't open polling settings dialog by summary");
        }

        dissmissableNotification = new UiObject(new UiSelector().text("Dismissible Notification")
                .resourceId("android:id/title"));

        try {
            dissmissableNotification.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on dissmissableNotification checkbox from title");
        }

        dissmissableNotification = new UiObject(new UiSelector().textContains(
                "SilentZone will start automatically").resourceId("android:id/summary"));

        try {
            dissmissableNotification.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on dissmissableNotification checkbox from summary");
        }

        startAtBootup = new UiObject(new UiSelector().text("Start at Bootup").resourceId(
                "android:id/title"));
        try {
            startAtBootup.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on start at bootup checkbox from title");
        }

        startAtBootup = new UiObject(new UiSelector().textContains(
                "SilentZone will start automatically").resourceId("android:id/summary"));

        try {
            startAtBootup.click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on start at bootup checkbox from summary");
        }

        redoTutorial = new UiObject(new UiSelector().text("Redo Tutorial").resourceId(
                "android:id/title"));

        try {
            redoTutorial.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on tutorial from title");
        }
        Utils.performTutorial(getUiDevice());
        try {
            options.click();
            settings.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't get back to settings after tutorial");
        }

        redoTutorial = new UiObject(new UiSelector().text("Press here to redo the tutorial.")
                .resourceId("android:id/summary"));

        try {
            redoTutorial.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't click on tutorial from summary");
        }

        Utils.performTutorial(getUiDevice());

        try {
            options.click();
            settings.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't get back to settings after tutorial");
        }

        deleteZones = new UiObject(new UiSelector().text("Delete Zones").resourceId(
                "android:id/title"));

        try {
            deleteZones.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Ok")).click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't delete our zones from title");
        }

        deleteZones = new UiObject(new UiSelector().text("Delete all your existing zones")
                .resourceId("android:id/summary"));

        try {
            deleteZones.clickAndWaitForNewWindow();
            new UiObject(new UiSelector().text("Ok")).click();
        } catch (UiObjectNotFoundException e) {
            fail("Couldn't delete our zones from summary");
        }

        feedback = new UiObject(new UiSelector().text("Send Feedback").resourceId(
                "android:id/title"));

        try {
            settingsScroller.scrollIntoView(feedback);
        } catch (UiObjectNotFoundException e) {
            Log.log(TAG, "No settings scroll we could still be alright");
        }
        try {
            feedback.click();
            getUiDevice().pressBack();
        } catch (UiObjectNotFoundException e) {
            fail("Find any feedback!");
        }

        version = new UiObject(new UiSelector().text("SilentZone version").resourceId(
                "android:id/title"));
        try{
            settingsScroller.scrollIntoView(version);
        } catch(UiObjectNotFoundException e){
            Log.log(TAG, "No settings scrolly found we still could recover");
        }
        
        try{
            version.click();
            // Nothing happens haha
        } catch(UiObjectNotFoundException e){
            fail("Couldn't find silentZone Version I guess.");
        }
        
        getUiDevice().pressBack();

    }
}
