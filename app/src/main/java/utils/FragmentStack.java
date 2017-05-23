package utils;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public class FragmentStack {
    private ArrayList<ArrayList<Fragment>> stackList = new ArrayList<>();
    private ArrayList<Fragment> stack;
    private CloseFragment listener;

    public FragmentStack() {
        if (stack == null) {
            stack = new ArrayList<>();
        }
        stackList.add(stack);
    }


    /**
     * standard mode,Directly add to the current task stack
     *
     * @param fragment Added fragment
     */
    public void putStandard(Fragment fragment) {
        stackList.get(stackList.size() - 1).add(fragment);
    }

    /**
     * SingleTop mode ,If the top is not created
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTop(Fragment fragment) {
        ArrayList<Fragment> lastList = stackList.get(stackList.size() - 1);
        if (lastList.isEmpty()) {
            lastList.add(fragment);
            return false;
        } else {
            Fragment last = lastList.get(lastList.size() - 1);
            if (last.getClass().getName().equals(fragment.getClass().getName())) {
//                fragment.onNewIntent();
                BaseFragment rootFragment = (BaseFragment) fragment;
                rootFragment.onNewIntent();
                return true;
            } else {
                lastList.add(fragment);
                return false;
            }
        }


    }

    /**
     * singTask mode ,If the current task stack does not create and empty all of the upper instance
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean putSingleTask(Fragment fragment) {
        boolean isClear = false;
        ArrayList<Fragment> lastList = stackList.get(stackList.size() - 1);
        if (lastList.isEmpty()) {
            lastList.add(fragment);
        } else {
            int tempIndex = 0;
            for (int x = 0; x <= lastList.size() - 1; x++) {
                if (lastList.get(x).getClass().getName().equals(fragment.getClass().getName())) {
                    //clear all instance
                    isClear = true;
                    tempIndex = x;
                    break;
                }
            }
            if (!isClear) {
                lastList.add(fragment);
            } else {
                if (listener != null) {
                    listener.show(lastList.get(tempIndex));
                    StackManager.isFirstClose = true;
                    for (int i = lastList.size() - 1; i > tempIndex; i--) {
                        listener.close(lastList.get(i));
                    }
                    for (int j = lastList.size() - 1; j > tempIndex; j--) {
                        lastList.remove(j);
                    }
                }

            }
        }
        return isClear;

    }

    /**
     * singleInstance mode,Create a new task stack at a time.
     *
     * @param fragment 加入的fragment
     */
    public void putSingleInstance(Fragment fragment) {
        ArrayList<Fragment> frags = new ArrayList<>();
        frags.add(fragment);
        stackList.add(frags);
    }

    public void onBackPressed() {
        int i = stackList.size() - 1;
        if (i >= 0) {
            ArrayList<Fragment> lastStack = stackList.get(i);
            if (lastStack != null && (!lastStack.isEmpty())) {
                lastStack.remove(lastStack.size() - 1);
                if (lastStack.isEmpty()) {
                    stackList.remove(lastStack);
                }
            } else {
                stackList.remove(lastStack);
            }
        } else {
            stackList.clear();
        }
    }

    protected void setCloseFragmentListener(CloseFragment listener) {
        this.listener = listener;
    }

    protected Fragment[] getLast() {
        Fragment[] fagArr = new Fragment[2];
        boolean hasFirst = false;
        for (int x = stackList.size() - 1; x >= 0; x--) {
            ArrayList<Fragment> list = stackList.get(x);
            if (list != null && (!list.isEmpty())) {
                if (hasFirst) {
                    fagArr[1] = list.get(list.size() - 1);
                    break;
                } else {
                    hasFirst = true;
                    fagArr[0] = list.get(list.size() - 1);
                    if (list.size() > 1) {
                        fagArr[1] = list.get(list.size() - 2);
                    }
                }

            }
        }
        return fagArr;
    }
}
