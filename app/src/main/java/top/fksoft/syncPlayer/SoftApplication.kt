package top.fksoft.syncPlayer

import android.app.Application
import android.content.Context

/**
 * @author Explo
 */
class SoftApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = getApplicationContext()
    }

    companion object {
        var context: Context? = null
            private set
    }

}
