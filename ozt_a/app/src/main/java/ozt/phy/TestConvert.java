package ozt.phy;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by phy on 2018/1/15.
 */

public class TestConvert {
    ArrayList<BaseFragment> fragmentList=new ArrayList<>();

    public Object testReturn() {
        return null;
    }
    void testIntent(Context context){
        Intent intent=new Intent(context,TestButtomNavigationActivity.class);

    }
}
