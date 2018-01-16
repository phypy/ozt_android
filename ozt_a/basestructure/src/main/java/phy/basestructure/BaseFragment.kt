package ozt.phy

import android.content.Context
import android.support.annotation.Keep
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment : Fragment() {
    protected var mActivity: BaseActivity? = null

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    /**
     * listener the action in ui which onClick attr is 'onBackPressed'
     * @param back
     */
    @Keep
    fun onBackPressed(back: View) {
        mActivity!!.onBackPressed(back)
    }
}// Required empty public constructor
