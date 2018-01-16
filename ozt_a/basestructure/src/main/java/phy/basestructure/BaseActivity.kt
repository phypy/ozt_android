package ozt.phy

import android.content.Context
import android.os.Bundle
import android.support.annotation.Keep
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import phy.basestructure.R
import java.util.*

/**
 * Created by phy on 2018/1/12.
 * BaseActivity,all activities should extends this class.
 */
open abstract class BaseActivity : AppCompatActivity() {
    //a list that keep the fragment tags.
    protected var fragmentList = LinkedList<String>()

    /**
     * init the activity content view with a empty FrameLayout
     * @param withBottomNav if need bottom navigation view.
     *
     */
    protected fun initContainerView(withBottomNav: Boolean): View {
        when (withBottomNav) {
            true -> return LayoutInflater.from(this).inflate(R.layout.fm_container_nav_bottoom, null)
            false -> return LayoutInflater.from(this).inflate(R.layout.fm_container, null)
        }
    }

    /**
     * init the activity content view with a custom father layout,the layout should contain a StubView which id is R.id.stub_fm_container otherwise a crash will occur.
     * @param withBottomNav if need bottom navigation view.
     */
    protected fun initContainerView(fatherLayoutResId: Int, withBottomNav: Boolean): View {
        var father = LayoutInflater.from(this).inflate(fatherLayoutResId, null)
        var stub = father.findViewById<ViewStub>(R.id.stub_fm_container)
        when (withBottomNav) {
            true -> stub.layoutResource = R.layout.fm_container_nav_bottoom
            false -> stub.layoutResource == R.layout.fm_container
        }
        stub.inflate()
        return father
    }

    protected fun setBottomNavigation(mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener): BottomNavigationView? {
        var nav_bottom = findViewById<BottomNavigationView>(R.id.nav_bottom)
        nav_bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        return nav_bottom
    }

    protected open fun show(clazz: Class<out BaseFragment>, extra: Bundle): BaseFragment? {
        return show(clazz, extra, true)
    }

    protected open fun show(clazz: Class<out BaseFragment>, extra: Bundle, hideLast: Boolean): BaseFragment? {
        var next = supportFragmentManager.findFragmentByTag(clazz.name) as BaseFragment
        if (next == null) {
            next = Fragment.instantiate(this, clazz.name) as BaseFragment
            next.arguments = extra
        }
        var transaction = supportFragmentManager.beginTransaction()
        //hide the last fragment
        if (hideLast && !fragmentList.isEmpty()) {
            var last = supportFragmentManager.findFragmentByTag(fragmentList.last)
            last?.let { transaction.hide(last) }
        }
        //show the new fragment
        if (!next.isAdded) {
            transaction.add(R.id.fm_container, next, clazz.name)
        } else {
            transaction.show(next)
        }
        //add this fragment into back stack,so it can roll back after a while.
        transaction.addToBackStack(null)
        transaction.commit()
        //add next fragment into the fragmentList,now the next fragment is the top fragment.
        fragmentList.addLast(clazz.name)
        return next
    }

    private fun getFromBackStack(): BaseFragment? {
        //if just one or no element,it means that we have no a behind fragment to rollback.
        if (fragmentList.size < 2) return null
        //remove the current show fragment tag.
        fragmentList.removeLast()
        //show the last fragment.
        var last = supportFragmentManager.findFragmentByTag(fragmentList.last) as BaseFragment
        last?.let {
            //there leaved some problem,i'm not sure the fragment that pop back stack is the last one that in the fragmentList.
            //so,if find some error after a while,we can just use the show method to rollback a fragment replace popBackStack.
            supportFragmentManager.popBackStack()
        }
        return last
    }

    override fun onBackPressed() {
        var last = getFromBackStack()
        //if we can not find a fragment,we have no page to show,so we finish the activity.
        if (last == null) {
            finish()
        } else {
            //hide the input method if it showing.
            hideSoftInput()
        }
        super.onBackPressed()
    }

    /**
     *  listener the action in ui which onClick attr is 'onBackPressed'
     * @param back
     */
    @Keep
    fun onBackPressed(back: View) {
        onBackPressed()
    }

    fun hideSoftInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null && imm.isActive && currentFocus != null && currentFocus!!.windowToken != null) {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            //            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    fun showSoftInput(view: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.showSoftInput(view, SHOW_IMPLICIT)
    }
}