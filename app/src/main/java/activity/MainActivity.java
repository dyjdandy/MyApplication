package activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.myapplication.R;

import fragments.HomeFragment;
import utils.StackManager;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */

public  class MainActivity extends FragmentActivity{

    private FrameLayout containerLL;
    public static StackManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        containerLL = ((FrameLayout) findViewById(R.id.framLayoutId));
        manager = new StackManager(this);
        manager.setFragment(new HomeFragment());
        setFragmentAnim();
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                manager.onBackPressed();
                return true;
            default:

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setFragmentAnim() {
//        manager.setAnim();
    }

}
