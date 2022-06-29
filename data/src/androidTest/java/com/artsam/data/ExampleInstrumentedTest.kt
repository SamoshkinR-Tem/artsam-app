package com.artsam.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.artsam.data.test", appContext.packageName)
    }

    @Test
    fun create_dir(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // appContext.fileList().forEach { println(it) }
        println(appContext.fileList().size.toString())
        println(appContext.filesDir.absolutePath)


    }

    @Test
    fun store_file(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        fun getPath(context: Context) = context.filesDir.path + File.separator + "myfolder"
        File(getPath(appContext), "file_in_folder")

        val filename = "myfile"
        val fileContents = "Hello world!"
        appContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }
}