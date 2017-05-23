package fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import utils.BaseFragment;
import utils.StackManager;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public class Fragment1 extends BaseFragment implements View.OnClickListener {
    private Button fragment2;
    private Button fragment3;
    private Button fragment4;

    @Override
    public View initRootView() {
        rootView = mInflater.from(getActivity()).inflate(R.layout.fragment1, null);
        return rootView;
    }

    @Override
    public void setViews() {
        fragment2 = ((Button) rootView.findViewById(R.id.fragment2));
        fragment3 = ((Button) rootView.findViewById(R.id.fragment3));
        fragment4 = ((Button) rootView.findViewById(R.id.fragment4));
        fragment2.setOnClickListener(this);
        fragment3.setOnClickListener(this);
        fragment4.setOnClickListener(this);
        fragment3.setVisibility(View.GONE);
        fragment4.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            String key = getArguments().getString("key");
        }
    }

    @Override
    public boolean mainTabHide() {
        return false;
    }

    @Override
    public void onClick(View v) {
        Bundle args=new Bundle();
        args.putString("key1","value1");
        switch (v.getId()){
            case R.id.fragment2:
                open(new Fragment2(),args,StackManager.STANDARD);
                break;
            case R.id.fragment3:
//                open(new Fragment3());
                break;
            case R.id.fragment4:
//                open(new Fragment3());
                break;
        }
    }

}
