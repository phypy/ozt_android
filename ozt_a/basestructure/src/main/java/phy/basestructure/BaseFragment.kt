package ozt.phy

import android.support.annotation.Keep
import android.support.v4.app.Fragment
import android.view.View

open abstract class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        if (!isHidden) {
            onVisible()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            onVisible()
        }
    }

    open fun onVisible() {

    }

    /**
     * listener the action in ui which onClick attr is 'onBackPressed'
     * @param back
     */
    @Keep
    fun onBackPressed(back: View) {
        (activity as BaseActivity)!!.onBackPressed(back)
    }
}// Required empty public constructor
