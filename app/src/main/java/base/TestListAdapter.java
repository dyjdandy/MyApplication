package base;

import android.content.Context;
import android.view.View;

import com.example.myapplication.R;

import java.util.List;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/1/3.
 * version:1.0
 */

public class TestListAdapter extends BaseListAdapter {

    public TestListAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public int getItemId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void convertView(List datas, int position, MyViewHolder holder) {

    }
}
