package org.unallocatedspace.uasapp;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class org.unallocatedspace.uasapp.InfoPageTest \
 * org.unallocatedspace.uasapp.tests/android.test.InstrumentationTestRunner
 */
public class InfoPageTest extends ActivityInstrumentationTestCase2<InfoPage> {

    public InfoPageTest() {
        super("org.unallocatedspace.uasapp", InfoPage.class);
    }

}
