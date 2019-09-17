package top.fksoft.syncPlayer.utils.android

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager

object DisplayUtils {

    /**
     *
     * 得到屏幕的宽度
     *
     * @param context 上下文
     * @return 屏幕的宽度
     */
    fun getDisplayWidth(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x
    }

    /**
     *
     * 得到导航栏高度
     *
     *
     * 通过反射得到导航栏的高度
     *
     * @param context  程序上下文
     * @return 导航栏高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        var width = 0
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val dm = DisplayMetrics()
        try {
            val c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            width = dm.heightPixels - display.height
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return width
    }

    /**
     *
     * 获取手机状态栏高度
     *
     *
     * @param context 程序上下文
     * @return 手机状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result

    }

    /**
     * dp转 px
     * @param context 上下文
     * @param dipValue dip值
     * @return px值
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}