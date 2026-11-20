package com.ceeport.player
import android.content.Context
import org.mozilla.geckoview.GeckoRuntime

object CeeportRuntime {
    @Volatile private var instance: GeckoRuntime? = null
    @Synchronized
    fun get(context: Context): GeckoRuntime {
        return instance ?: GeckoRuntime.create(context.applicationContext).also { instance = it }
    }
}
