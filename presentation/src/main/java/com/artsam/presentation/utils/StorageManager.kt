package com.artsam.presentation.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class StorageManager {

    companion object {
        fun workWithExternalStorage() {
            println("isExternalStorageReadable: ${isExternalStorageReadable()}")
            println("isExternalStorageWritable: ${isExternalStorageWritable()}")
        }

        // Checks if a volume containing external storage is available
        // for read and write.
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        // Checks if a volume containing external storage is available to at least read.
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in setOf(
                Environment.MEDIA_MOUNTED,
                Environment.MEDIA_MOUNTED_READ_ONLY
            )
        }

        fun createDirInternal(ctx: Context) {
            ctx.getDir("myfolder", Context.MODE_PRIVATE).run { if (!exists()) mkdir() }
            File(ctx.filesDir.path + File.separator + "myfolder").run { if (!exists()) mkdir() }

            println("AppSpecificFileStorage | dir \"myfolder\" created")

            File(ctx.filesDir.path + File.separator + "myfolder", "file_in_folder").run {
                if (!exists()) createNewFile()
                if (exists()) {
                    val fos = FileOutputStream(this, true)
                    fos.write("Hello world!\n".toByteArray())
                    fos.close()
                }
            }

            val filename = "myfile"
            val fileContents = "Hello world!"
            ctx.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
        }

        fun deleteDir(ctx: Context) {
            val myDir = ctx.getDir("myfolder", Context.MODE_PRIVATE)
            if (myDir.exists()) myDir.delete()
        }

        fun getPath(context: Context) = context.filesDir.path + File.separator + "myfolder"

    }
}