package top.fksoft.syncPlayer.utils.android

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import top.fksoft.syncPlayer.R

object ThemeUtils {
    /**
     *
     * 获取ColorPrimary的颜色值
     * @param activity 需要获取的活动
     * @return 十六进制颜色值
     */
    fun getColorPrimary(activity: Activity): Int {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }

    /**
     *
     * 获取DarkColorPrimary的颜色值
     * @param activity 需要获取的活动
     * @return 十六进制颜色值
     */
    fun getDarkColorPrimary(activity: Activity): Int {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        return typedValue.data
    }

    /**
     *
     * 获取ColorAccent的颜色值
     * @param activity 需要获取的活动
     * @return 十六进制颜色值
     */
    fun getColorAccent(activity: Activity): Int {
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.data
    }

    /**
     *
     * UI 沉浸，对于Android 5.0 以上有效
     * @param activity 需要沉浸的Activity
     */
    fun immersive(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = activity.window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            activity.window.navigationBarColor = Color.TRANSPARENT
            activity.window.statusBarColor = Color.TRANSPARENT
        }

    }

    /**
     *
     * 隐藏导航栏和状态栏，实现完全全屏 支持 4.4 及以上
     * @param activity 需要全屏的Activity
     */
    fun fullscreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.navigationBarColor = Color.TRANSPARENT
            activity.window.statusBarColor = Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorView = activity.window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

}