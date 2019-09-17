package top.fksoft.syncPlayer.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import top.fksoft.syncPlayer.R
import top.fksoft.syncPlayer.utils.android.DisplayUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            var layoutParams = status.layoutParams
            layoutParams.height = DisplayUtils.getStatusBarHeight(this)
            status.layoutParams = layoutParams
            layoutParams = nav.layoutParams
            layoutParams.height = DisplayUtils.getNavigationBarHeight(this)
            nav.layoutParams = layoutParams
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.setting ->{

            }
            R.id.exit ->{

            }
        }
        return true
    }

}
