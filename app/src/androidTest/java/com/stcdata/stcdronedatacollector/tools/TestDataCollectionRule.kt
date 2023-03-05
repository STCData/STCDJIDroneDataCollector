package com.stcdata.stcdronedatacollector.tools

import androidx.annotation.WorkerThread
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException
import java.util.concurrent.TimeUnit


class TestDataCollectionRule : TestWatcher() {
    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    val SC_OUTPUT = "/sdcard/"

    fun screenCaptureFilename(testName: String): String {
       return "$SC_OUTPUT${testName}.mp4"
    }

    @WorkerThread
    fun startScreenRecord(fileName: String) {
        try {
            uiDevice.executeShellCommand("screenrecord $fileName")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @WorkerThread
    fun stopScreenRecord() {
        try {
            uiDevice.executeShellCommand("pkill -2 screenrecord")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Rule @JvmField
    var testName = TestName()

    private fun getTestName(): String {
        return "${testName.methodName}"
    }

    override fun starting(description: Description) {
        super.starting(description)
        object : Thread() {
            override fun run() {
                super.run()
                TimeUnit.SECONDS.sleep(13)
                val filename = screenCaptureFilename(description.methodName)
                print("SCREENRECORDING '$filename' is recording.")
                startScreenRecord(filename)
            }
        }.start()
    }

    override fun finished(description: Description) {
        super.finished(description)
        object : Thread() {
            override fun run() {
                super.run()
                val filename = screenCaptureFilename(description.methodName)

                stopScreenRecord()
                print("SCREENRECORDING '$filename' is finished.")


            }
        }.start()
    }
}