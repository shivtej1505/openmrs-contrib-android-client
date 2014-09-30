package org.openmrs.client.test.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.CheckBox;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.FindPatientsActivity;
import org.openmrs.client.R;
import org.openmrs.client.activities.FindPatientsSearchActivity;
import org.openmrs.client.activities.LoginActivity;
import org.openmrs.client.databases.OpenMRSSQLiteOpenHelper;

public class FindPatientsDetailsTest extends
        ActivityInstrumentationTestCase2<FindPatientsActivity> {

    private Solo solo;
    private static final String PATIENT_EXIST = "Paul";
    private static final String PATIENT_NO_EXIST = "Enrique";
    private static final String MESSAGE = "Wrong activity. FindPatientsActivity expected";
    private static boolean isAuthenticated;

    public FindPatientsDetailsTest() {
        super(FindPatientsActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();
        if (!isAuthenticated) {
            getInstrumentation().getTargetContext().deleteDatabase(OpenMRSSQLiteOpenHelper.DATABASE_NAME);
        }
        solo = new Solo(getInstrumentation());
        getActivity();
        getInstrumentation().waitForIdleSync();
    }

    public void testPatientNotExist() throws Exception {
        if (!isAuthenticated) {
            if (!solo.waitForActivity(LoginActivity.class, WaitHelper.ACTIVITY_TIMEOUT)) {
                ((FindPatientsActivity) solo.getCurrentActivity()).moveUnauthorizedUserToLoginScreen();
            }
            assertTrue(solo.waitForActivity(LoginActivity.class, WaitHelper.ACTIVITY_TIMEOUT));

            LoginHelper.login(solo);
            isAuthenticated = true;
            Log.d(WaitHelper.TAG, "Login finished. Wait for FindPatientActivity.");
            assertTrue(solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT));
            Log.d(WaitHelper.TAG, "Waiting for FindPatientActivity finished.");
        }

       // solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        //SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_NO_EXIST, "Patient name");
        SearchHelper.doSearch(solo, PATIENT_NO_EXIST, "Patient name");

        assertTrue(solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT));
        //solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        //solo.waitForText("No results found for query \"" +  PATIENT_NO_EXIST + "\"", 1, WaitHelper.TIMEOUT);
        assertTrue(WaitHelper.waitForText(solo, "No results found for query \"" +  PATIENT_NO_EXIST + "\""));

        solo.goBackToActivity("FindPatientsActivity");
        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);
    }

    public void testSearchPatient() throws Exception {
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        //SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_EXIST, "Patient name");
        SearchHelper.doSearch(solo, PATIENT_EXIST, "Patient name");

        solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        //solo.waitForText(PATIENT_EXIST, 1, WaitHelper.TIMEOUT);
        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));

        solo.goBackToActivity("FindPatientsActivity");
        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

    }

    public void testSearchPatientAndSave() throws Exception {
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        //SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_EXIST, "Patient name");
        SearchHelper.doSearch(solo, PATIENT_EXIST, "Patient name");

        solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        //solo.waitForText(PATIENT_EXIST, 1, WaitHelper.TIMEOUT);
        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));

        CheckBox isPatientSave = (CheckBox) solo.getView(R.id.offlineCheckbox);
        //solo.waitForText("Download", 1, WaitHelper.TIMEOUT);
        assertTrue(WaitHelper.waitForText(solo, "Download"));
        assertFalse(isPatientSave.isChecked());

        solo.clickOnCheckBox(0);

        //solo.waitForText("Available offline", 1, WaitHelper.TIMEOUT);
        assertTrue(WaitHelper.waitForText(solo, "Available offline"));

        solo.goBackToActivity("FindPatientsActivity");
        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }

}
