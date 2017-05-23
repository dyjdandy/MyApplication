package base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/1/3.
 * version:1.0
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    public Context context;
    public List<T> datas = new ArrayList<>();

    public BaseListAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            int itemId = getItemId();
            convertView = LayoutInflater.from(context).inflate(itemId, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = ((MyViewHolder) convertView.getTag());
        }
        convertView(datas, position, myViewHolder);
        return convertView;
    }

    public abstract int getItemId();

    public abstract void convertView(List<T> datas, int position, MyViewHolder holder);

    public class MyViewHolder {
        public View rootView;
        public Map<Integer, View> map = new HashMap<>();

        public MyViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getView(int id) {
            View view = map.get(id);
            if (view == null) {
                view = rootView.findViewById(id);
                map.put(id, view);
            }
            return view;
        }
    }


//    public class MyViewHolder {
//        View mRootView;
//        Map<Integer,View> mViews = new HashMap<Integer,View>();
//
//        public MyViewHolder(View view) {
//            this.mRootView = view;
//        }
//
//        public View get(int viewId) {
//            View childView = mViews.get(viewId);
//            if (childView == null) {
//                childView = mRootView.findViewById(viewId);
//                mViews.put(viewId, childView);
//            }
//            return childView;
//        }
//    }
}
