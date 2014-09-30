package org.openmrs.client.test.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.LoginActivity;
import org.openmrs.client.R;

public class LoginActivityTest extends
        ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    private static final String WRONG_SERVER_URL = "http://openmrs-ac-ci.soldevelo.com:8080/openmrs-standalone";
    private static final String WRONG_PASSWORD = "Testuser";
    private static final String EMPTY_FIELD = "Login and password can not be empty.";

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();

        solo = new Solo(getInstrumentation());
        getActivity();
        getInstrumentation().waitForIdleSync();
    }

    public void testEmptyFields() throws Exception {
        assertTrue(solo.waitForActivity(LoginActivity.class, 60000));

        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        View loginButton = solo.getView(R.id.loginButton);

        //Empty both fields
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);

        //Click on Login button
        WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON);
        //solo.clickOnButton(LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));

        //Empty password field
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Click on Login button
        //solo.clickOnButton(LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));

        //Empty login field
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, LoginHelper.PASSWORD);

        //Click on Login button
        //solo.clickOnButton(LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));
    }

    public void testLoginFailed() throws Exception {
        assertTrue(solo.waitForActivity(LoginActivity.class, 60000));

        //Write login
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, WRONG_PASSWORD);

        //Click on Login button
        View loginButton = solo.getView(org.openmrs.client.R.id.loginButton);
        //assertTrue(WaitHelper.waitForText(solo, LOGIN_BUTTON));
        //solo.clickOnButton(LOGIN_BUTTON);
        WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        //Write url
        EditText urlField = (EditText) solo.getView(R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, LoginHelper.SERVER_URL);

        //Click on Done button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON));
        solo.clickOnButton(LoginHelper.DONE_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, "Your user name or password may be incorrect. Please try again."));
    }

    public void testWrongUrl() throws Exception {
        assertTrue(solo.waitForActivity(LoginActivity.class, 60000));

        //Write login
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, LoginHelper.PASSWORD);

        //Click on Login button
        View loginButton = solo.getView(org.openmrs.client.R.id.loginButton);
        //assertTrue(WaitHelper.waitForText(solo, LOGIN_BUTTON));
        //solo.clickOnButton(LOGIN_BUTTON);
        WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON);
        solo.clickOnView(loginButton);

        //Write wrong url
        EditText urlField = (EditText) solo.getView(R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, WRONG_SERVER_URL);

        //Click on Done button
        View doneButton = solo.getView(org.openmrs.client.R.id.dialogFormButtonsSubmitButton);
        WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON);
        //assertTrue(WaitHelper.waitForText(solo, DONE_BUTTON));
        //solo.clickOnButton(DONE_BUTTON);
        solo.clickOnView(doneButton);

        assertTrue(WaitHelper.waitForText(solo, "Cancel"));
    }

    public void testLogin() throws Exception {
        assertTrue(solo.waitForActivity(LoginActivity.class, 60000));
        assertTrue(LoginHelper.login(solo));
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
