package org.openmrs.client.test.robotium;

import android.app.Instrumentation;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public final class SearchHelper {

    private SearchHelper() {
    }

    public static void doSearch(Solo solo, Instrumentation instrumentation, String query, String searchHint) throws java.lang.Exception {
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_SEARCH);

        //solo.sendKey(KeyEvent.KEYCODE_SEARCH);
        WaitHelper.waitForText(solo, searchHint);
        KeyCharacterMap keymap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
        for (KeyEvent key : keymap.getEvents(query.toCharArray())) {
            instrumentation.sendKeySync(key);
        }

        instrumentation.sendCharacterSync(KeyEvent.KEYCODE_ENTER);
        //solo.sendKey(Solo.ENTER);
    }

    public static void doSearch(Solo solo, String query, String searchHint) throws java.lang.Exception {
        solo.clickOnActionBarItem(org.openmrs.client.R.id.action_search);
        WaitHelper.waitForText(solo, searchHint);
        EditText search = solo.getEditText(searchHint);
        solo.enterText(search, query);
        solo.sendKey(Solo.ENTER);
    }
}
