package fragments;

import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import utils.BaseFragment;
import utils.StackManager;
import views.NXHooldeView;
import wifipassword.Scanner;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private Button standard;
    private TextView singleTask;
    private ImageView addImg;
    // service
    public WifiManager wifiManager;

    public Scanner scanner;

    public Handler appendMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            singleTask.append(b.getString("message"));
        }
    };

    public Handler setMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            singleTask.setText(b.getString("message"));
        }
    };
    @Override
    public View initRootView() {
        rootView=mInflater.inflate(R.layout.home_layout,null);
        return rootView;
    }

    @Override
    public void setViews() {
        standard = ((Button) rootView.findViewById(R.id.standard));
        singleTask = ((TextView) rootView.findViewById(R.id.single_task));
        addImg = ((ImageView) rootView.findViewById(R.id.iv_goods_fits_add));
        standard.setOnClickListener(this);
        singleTask.setOnClickListener(this);

    }

    @Override
    public void initData() {
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NXHooldeView nxHooldeView = new NXHooldeView(getRoot());
                int position[] = new int[2];
                v.getLocationInWindow(position);
                nxHooldeView.setStartPosition(new Point(position[0], position[1]));
                ViewGroup rootView = (ViewGroup) getRoot().getWindow().getDecorView();
                rootView.addView(nxHooldeView);
                int endPosition[] = new int[2];
                singleTask.getLocationInWindow(endPosition);
                nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
                nxHooldeView.startBeizerAnimation();
            }
        });
    }


    @Override
    public boolean mainTabHide() {
        return true;
    }


    @Override
    public void onClick(View v) {
        Bundle args=new Bundle();
        args.putString("key","value");
        switch (v.getId()) {
            case R.id.standard:
//                open(new Fragment1(),args);
//                open(new Fragment1(), args, StackManager.STANDARD);
                wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                scanner = new Scanner(this);
                scanner.run();
                break;
            case R.id.single_task:
//                open(new Fragment1(),args);
//                open(new Fragment1(),args,StackManager.SINGLE_TASK);
                break;
        }
    }

}

