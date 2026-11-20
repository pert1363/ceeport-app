package com.ceeport.player

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var store: ProjectStore
    private val IMPORT_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = ProjectStore(this)
        
        val layout = LinearLayout(this).apply { 
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val title = TextView(this).apply {
            text = "CEEPORT PLAYER\nمحیط تست آفلاین پروژه‌ها"
            textSize = 20f
            setPadding(0, 0, 0, 50)
        }
        layout.addView(title)

        val importBtn = Button(this).apply {
            text = "➕ ورود فایل HTML"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "*/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                startActivityForResult(intent, IMPORT_CODE)
            }
        }
        layout.addView(importBtn)

        val listLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 50, 0, 0)
        }

        store.listProjects().forEach { project ->
            val btn = Button(this).apply {
                text = "▶ اجرای ${project.name}"
                setOnClickListener {
                    val intent = Intent(this@MainActivity, EngineActivity::class.java)
                    intent.putExtra("file_path", project.htmlFile.absolutePath)
                    startActivity(intent)
                }
            }
            listLayout.addView(btn)
        }

        layout.addView(listLayout)
        setContentView(layout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMPORT_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                try {
                    val p = store.importHtml(uri, contentResolver)
                    Toast.makeText(this, "فایل ${p.name} وارد شد", Toast.LENGTH_SHORT).show()
                    recreate()
                } catch (e: Exception) {
                    Toast.makeText(this, "خطا: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
