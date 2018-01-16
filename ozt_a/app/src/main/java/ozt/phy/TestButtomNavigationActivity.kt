package ozt.phy

import android.os.Bundle
import android.support.design.widget.BottomNavigationView

class TestButtomNavigationActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initContainerView(true))
        var bottom_nav = setBottomNavigation(mOnNavigationItemSelectedListener)
        bottom_nav!!.setBackgroundColor(resources.getColor(R.color.colorAccent))
    }
}
