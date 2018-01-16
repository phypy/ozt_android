package ozt.phy

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView

class TestButtomNavigationActivity : BaseActivity() {
    var mHandler = Handler()
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
        show(TestFragmentA::class.java, null)
        mHandler.postDelayed({
            show(TestFragmentB::class.java, null)
        }, 3000)
        mHandler.postDelayed({
            show(TestFragmentC::class.java, null)
        }, 6000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandler.removeCallbacksAndMessages(null)
    }
}
