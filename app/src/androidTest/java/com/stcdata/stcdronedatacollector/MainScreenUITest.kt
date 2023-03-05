package com.stcdata.stcdronedatacollector

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress

import org.junit.runner.RunWith


import org.junit.Before
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.stcdata.stcdronedatacollector.tools.TestDataCollectionRule
import kotlinx.coroutines.delay
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

private const val PACKAGE_NAME = "com.stcdata.stcdronedatacollector"
private const val LAUNCH_TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class MainScreenUITest {
    @Rule @JvmField
    val testDataCollectionRule = TestDataCollectionRule()

    private lateinit var device: UiDevice
    @Test
    fun clickAndWaitForWhile() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals(PACKAGE_NAME, appContext.packageName)
        val buttonName = "map mode"
        TimeUnit.SECONDS.sleep(5)
        device.findObject(By.clickable(true)).click()
        TimeUnit.SECONDS.sleep(2)
        device.findObject(By.clickable(true)).click()
        TimeUnit.SECONDS.sleep(10)
        device.findObject(By.clickable(true)).click()
        TimeUnit.SECONDS.sleep(3)
        device.findObject(By.clickable(true)).click()
        TimeUnit.SECONDS.sleep(10)




    }
    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val intent = context.packageManager.getLaunchIntentForPackage(
            PACKAGE_NAME).apply {
            // Clear out any previous instances
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)),
            LAUNCH_TIMEOUT
        )
    }
}
