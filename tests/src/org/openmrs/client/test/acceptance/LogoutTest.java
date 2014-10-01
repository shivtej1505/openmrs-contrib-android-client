package org.openmrs.client.test.acceptance;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.DashboardActivity;
import org.openmrs.client.activities.LoginActivity;
import org.openmrs.client.activities.SettingsActivity;
import org.openmrs.client.test.acceptance.helpers.LoginHelper;
import org.openmrs.client.test.acceptance.helpers.WaitHelper;

public class LogoutTest extends ActivityInstrumentationTestCase2<DashboardActivity> {

    private Solo solo;

    public LogoutTest() {
        super(DashboardActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();

        solo = new Solo(getInstrumentation());
        getActivity();
        getInstrumentation().waitForIdleSync();
        if (WaitHelper.waitForActivity(solo, LoginActivity.class)) {
            LoginHelper.login(solo);
        }
    }

    public void testLogout() throws Exception {
        solo.assertCurrentActivity("Wrong activity. DashboardActivity expected", DashboardActivity.class);

        //open menu
        solo.sendKey(Solo.MENU);
        assertTrue(WaitHelper.waitForText(solo, "Settings"));
        solo.clickOnText("Settings");

        assertTrue(WaitHelper.waitForActivity(solo, SettingsActivity.class));
        solo.assertCurrentActivity("Wrong activity. SettingsActivity expected", SettingsActivity.class);

        solo.clickInList(3);

        //wait for Logout dialog
        assertTrue(WaitHelper.waitForText(solo, "Logging out"));

        //Click on Cancel button
        View cancelButton = solo.getView(org.openmrs.client.R.id.dialogFormButtonsCancelButton);
        WaitHelper.waitForText(solo, "Cancel");
        solo.clickOnView(cancelButton);

        solo.assertCurrentActivity("Wrong activity. SettingsActivity expected", SettingsActivity.class);

        solo.clickInList(3);

        //wait for Logout dialog
        assertTrue(WaitHelper.waitForText(solo, "Logging out"));

        //Click on Logout button
        View logoutButton = solo.getView(org.openmrs.client.R.id.dialogFormButtonsSubmitButton);
        WaitHelper.waitForText(solo, "Logout");
        solo.clickOnView(logoutButton);

        assertTrue(solo.waitForActivity(LoginActivity.class, WaitHelper.TIMEOUT_THIRTY_SECOND));
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
