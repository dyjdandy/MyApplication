package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/1/3.
 * version:1.0
 */

public abstract class BaseRecylerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public List<T> datas = new ArrayList<>();
    public Context context;
    public ViewGroup parent;
    public BaseRecylerAdapter(Context context, List<T> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        VH holder = getViewHolder();
        return holder;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindView(holder, datas, position);
    }

    public abstract void onBindView(VH holder, List<T> datas, int position);

    protected abstract VH getViewHolder();

}
