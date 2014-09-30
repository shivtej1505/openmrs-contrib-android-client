package org.openmrs.client.test.robotium;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public final class LoginHelper {

    //public static final String LOGIN = "test1";
    //public static final String PASSWORD = "Testuser1";
    //public static final String SERVER_URL = "http://openmrs-ac-ci.soldevelo.com:8081/openmrs-standalone";
    public static final String LOGIN = "admin";
    public static final String PASSWORD = "Admin123";
    public static final String SERVER_URL = "http://demo.openmrs.org/openmrs";
    public static final String LOGIN_BUTTON = "Login";
    public static final String DONE_BUTTON = "Done";

    private LoginHelper() {
    }

    public static boolean login(Solo solo) throws java.lang.Exception {
        //Write login
        EditText loginUsernameField = (EditText) solo.getView(org.openmrs.client.R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(org.openmrs.client.R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, PASSWORD);

        //Click on Login button
        //solo.waitForText(LOGIN_BUTTON, 1, WaitHelper.TIMEOUT);
        View loginButton = solo.getView(org.openmrs.client.R.id.loginButton);
        //assertTrue(WaitHelper.waitForText(solo, LOGIN_BUTTON));
        //solo.clickOnButton(LOGIN_BUTTON);
        WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        //Write url
        EditText urlField = (EditText) solo.getView(org.openmrs.client.R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, SERVER_URL);

        //Click on Done button
        //solo.waitForText(DONE_BUTTON, 1, WaitHelper.TIMEOUT);
        View doneButton = solo.getView(org.openmrs.client.R.id.dialogFormButtonsSubmitButton);
        WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON);
        //assertTrue(WaitHelper.waitForText(solo, DONE_BUTTON));
        //solo.clickOnButton(DONE_BUTTON);
        solo.clickOnView(doneButton);

        Log.d(WaitHelper.TAG, "Done Button clicked.");

        return WaitHelper.waitForText(solo, "Login successful");
    }
}
