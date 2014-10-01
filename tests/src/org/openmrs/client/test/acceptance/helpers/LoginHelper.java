package org.openmrs.client.test.acceptance.helpers;

import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.R;

public final class LoginHelper {

    public static final String LOGIN = "test1";
    public static final String PASSWORD = "Testuser1";
    public static final String SERVER_URL = "http://openmrs-ac-ci.soldevelo.com:8081/openmrs-standalone";
    public static final String LOGIN_BUTTON = "Login";
    public static final String DONE_BUTTON = "Done";

    private LoginHelper() {
    }

    public static boolean login(Solo solo) throws java.lang.Exception {
        //Write login
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, PASSWORD);

        //Click on Login button
        View loginButton = solo.getView(org.openmrs.client.R.id.loginButton);
        WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        //Write url
        EditText urlField = (EditText) solo.getView(R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, SERVER_URL);

        //Click on Done button
        View doneButton = solo.getView(org.openmrs.client.R.id.dialogFormButtonsSubmitButton);
        WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON);
        solo.clickOnView(doneButton);

        return WaitHelper.waitForText(solo, "Login successful");
    }
}
