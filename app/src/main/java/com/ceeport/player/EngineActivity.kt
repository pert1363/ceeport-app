package com.ceeport.player

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import java.io.File

class EngineActivity : Activity() {
    private var session: GeckoSession? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val path = intent.getStringExtra("file_path") ?: return finish()
        val file = File(path)

        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val geckoView = GeckoView(this)
        layout.addView(geckoView, LinearLayout.LayoutParams(-1, -1))
        setContentView(layout)

        val runtime = CeeportRuntime.get(applicationContext)
        val newSession = GeckoSession()
        
        newSession.navigationDelegate = object : GeckoSession.NavigationDelegate {
            override fun onLoadRequest(session: GeckoSession, request: GeckoSession.NavigationDelegate.LoadRequest): GeckoResult<GeckoSession.NavigationDelegate.LoadRequest.AllowOrDeny>? {
                return GeckoResult.allow() 
            }
        }

        newSession.open(runtime)
        geckoView.setSession(newSession)
        newSession.loadUri("file://${file.absolutePath}")
        session = newSession
    }

    override fun onDestroy() {
        session?.close()
        super.onDestroy()
    }
}
