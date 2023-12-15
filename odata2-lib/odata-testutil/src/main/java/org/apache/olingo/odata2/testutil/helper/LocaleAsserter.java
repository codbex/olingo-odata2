package org.apache.olingo.odata2.testutil.helper;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class LocaleAsserter.
 */
public class LocaleAsserter {

    /**
     * Assert locale.
     *
     * @param actualLocale the actual locale
     * @param expectedLocale the expected locale
     */
    public static void assertLocale(Locale actualLocale, Locale expectedLocale) {
        assertLocale("Unexpected locale", actualLocale, expectedLocale);
    }

    /**
     * Assert locale.
     *
     * @param errorMessage the error message
     * @param actualLocale the actual locale
     * @param expectedLocale the expected locale
     */
    public static void assertLocale(String errorMessage, Locale actualLocale, Locale expectedLocale) {
        assertThat(errorMessage, actualLocale.toString(), containsString(expectedLocale.toString()));
    }
}
