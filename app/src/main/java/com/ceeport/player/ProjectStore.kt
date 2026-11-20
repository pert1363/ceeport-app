package com.ceeport.player
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException

class ProjectStore(private val context: Context) {
    private val root = File(context.filesDir, "ceeport/projects").apply { mkdirs() }

    data class Project(val id: String, val name: String, val htmlFile: File)

    @Throws(IOException::class)
    fun importHtml(uri: Uri, resolver: ContentResolver): Project {
        val id = System.currentTimeMillis().toString()
        val projectDir = File(root, id).apply { mkdirs() }
        val destination = File(projectDir, "project.html")
        resolver.openInputStream(uri)?.use { input ->
            destination.outputStream().use { output -> input.copyTo(output) }
        } ?: throw IOException("خطا در خواندن فایل")
        return Project(id, "پروژه $id", destination)
    }

    fun listProjects(): List<Project> {
        return root.listFiles()?.filter { it.isDirectory }?.mapNotNull { dir ->
            val html = File(dir, "project.html")
            if (html.exists()) Project(dir.name, "پروژه ${dir.name}", html) else null
        } ?: emptyList()
    }
}
