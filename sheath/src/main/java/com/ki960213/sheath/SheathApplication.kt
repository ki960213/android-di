package com.ki960213.sheath

import android.app.Application
import android.util.Log
import org.reflections.Reflections
import java.io.File

abstract class SheathApplication(basePackage: String? = null) : Application() {

    private val components: MutableList<SingletonComponent> = mutableListOf()

//    init {
//        val currentPackageName = rootPackageName ?: this.javaClass.`package`!!.name
//        val currentPackage = Paths.get(currentPackageName).toAbsolutePath().toString()
//        Log.d(THOMAS, "현재 패키지명 = $currentPackage")
//        val interfaceName = "com.ki960213.sheath.SingletonComponent"
//
//        try {
//            val directory = File(currentPackage)
//            Log.d(THOMAS, "directory is exist: ${directory.exists()}")
//            findClassesInDirectory(directory, currentPackage, interfaceName)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    init {
        val packageName = this.javaClass.`package`?.name
            ?: throw IllegalStateException("해당 클래스는 패키지 안에 존재해야 합니다.")
        Log.d(THOMAS, packageName)
        val reflections = Reflections(basePackage ?: packageName)

        val classes = reflections.getSubTypesOf(Any::class.java)

        for (clazz in classes) {
            Log.d(THOMAS, "찾은 클래스: ${clazz.name}")
        }
    }

    private fun findClassesInDirectory(
        directory: File,
        packageName: String,
        interfaceName: String,
    ) {
        Log.d(THOMAS, "탐색 중인 패키지명 : $packageName")
        val files = directory.listFiles() ?: return
        Log.d(THOMAS, "${files.size}")

        for (file in files) {
            Log.d("THOMAS", "file = ${file.name}")
            if (file.isDirectory) {
                findClassesInDirectory(file, "$packageName.${file.name}", interfaceName)
            } else if (file.name.endsWith(".class")) {
                val className = packageName + '.' + file.name.substring(0, file.name.length - 6)
                checkIfClassImplementsInterface(className, interfaceName)
            }
        }
    }

    private fun checkIfClassImplementsInterface(className: String, interfaceName: String) {
        try {
            val clazz = Class.forName(className)
            val interfaces = clazz.interfaces
            for (intf in interfaces) {
                if (intf.name == interfaceName) {
                    Log.d("THOMAS", "$className implements $interfaceName")
                    break
                }
            }
        } catch (e: ClassNotFoundException) {
        }
    }

    companion object {
        private const val THOMAS = "THOMAS"
    }
}
