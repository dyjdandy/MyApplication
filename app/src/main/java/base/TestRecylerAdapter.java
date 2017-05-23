package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.List;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/1/3.
 * version:1.0
 */

public class TestRecylerAdapter extends BaseRecylerAdapter<User, TestRecylerAdapter.MyViewHolder> {

    public TestRecylerAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public void onBindView(MyViewHolder holder, List datas, int position) {

    }

    @Override
    protected MyViewHolder getViewHolder() {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment2, null));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
