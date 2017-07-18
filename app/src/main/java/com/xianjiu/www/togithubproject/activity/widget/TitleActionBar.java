package com.xianjiu.www.togithubproject.activity.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xianjiu.www.togithubproject.R;
import com.xianjiu.www.togithubproject.activity.utile.PxToDp;

/**
 * 自定义ActionBar
 *
 * @author hl
 */
public class TitleActionBar extends RelativeLayout implements View.OnClickListener {

    private LayoutInflater mInflater;
    private View           view;
    private ImageView      iv_action_left;
    private TextView       tv_action_title;
    private LinearLayout   layout_operate;
    private View           layout_title;
    /**
     * 回退按钮点击Action
     */
    private Action         leftBtnAction;
    private Action         locationAction;

    public TitleActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TitleActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleActionBar(Context context) {
        super(context);
        init(context);
    }

    @Override
    public boolean hasOnClickListeners() {
        return super.hasOnClickListeners();
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        super.setOnHierarchyChangeListener(listener);
    }

    private void init(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.actionbar, null);
        layout_title = view.findViewById(R.id.layout_title);
        iv_action_left = (ImageView) view.findViewById(R.id.iv_action_left);
        tv_action_title = (TextView) view.findViewById(R.id.tv_action_title);
        layout_operate = (LinearLayout) view.findViewById(R.id.layout_operate);
        iv_action_left.setOnClickListener(this);
        addView(view);
    }

    /**
     * 修改背景颜色
     *
     * @param id
     */
    public void setTitleBackgroundColor(int id) {
        layout_title.setBackgroundColor(id);
    }

    /**
     * 修改标题颜色
     *
     * @param id
     */
    public void setTitleColor(int id) {
        tv_action_title.setTextColor(id);
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_action_title.setText(title);
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void setTitle(int title) {
        tv_action_title.setText(title);
    }

    /**
     * 是否隐藏左边的回退按钮
     *
     * @param isShow
     */
    public void setBackViewShow(boolean isShow) {
        iv_action_left.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 回退按钮是否可见
     *
     * @return
     */
    public boolean isBackViewShow() {
        return iv_action_left.getVisibility() == View.VISIBLE;
    }

    /**
     * 增加回退事件
     *
     * @param action
     */
    public void addLeftButtonAction(Action action) {
        leftBtnAction = action;
    }

    public void addLocationAction(Action action) {
        locationAction = action;
    }

    /**
     * 在右边增加一个操作按钮
     *
     * @param item
     */
    public View addActionItem(final ActionItem item) {
        if (item.getTitle() == null) {
            ImageView iv = new ImageView(getContext());
            iv.setLayoutParams(new LayoutParams(PxToDp.dip2px(getContext(), 40), LayoutParams.MATCH_PARENT));
            iv.setScaleType(ScaleType.CENTER_INSIDE);
            iv.setImageResource(item.getDrawable());
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.getAction().action(v);
                }
            });
            layout_operate.addView(iv);
            MarginLayoutParams params = (MarginLayoutParams) iv.getLayoutParams();
            params.setMargins(PxToDp.dip2px(getContext(), 2), 0, PxToDp.dip2px(getContext(), 15), 0);
            return iv;
        } else {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv_title_item, null);
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            tv.setText(item.getTitle());
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.getTextRightDrawable(), 0);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.getAction().action(v);
                }
            });
            layout_operate.addView(tv);
            MarginLayoutParams params = (MarginLayoutParams) tv.getLayoutParams();
            params.setMargins(PxToDp.dip2px(getContext(), 2), 0, PxToDp.dip2px(getContext(), 15), 0);
            return tv;
        }
    }

    /**
     * 新增布局
     *
     * @param v
     */
    public void addActionItem(View v) {
        if (v != null) {
            layout_operate.addView(v);
            MarginLayoutParams params = (MarginLayoutParams) v.getLayoutParams();
            params.setMargins(PxToDp.dip2px(getContext(), 2), 0, PxToDp.dip2px(getContext(), 5), 0);
        }
    }

    public void removeActionItem(int i) {
        layout_operate.removeViewAt(i);
    }

    public void removeAllActionItem() {
        layout_operate.removeAllViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_action_left:
                if (leftBtnAction != null) {
                    leftBtnAction.action(v);
                }
                break;
        }
    }

    /**
     * 元素对象
     *
     * @author Administrator
     */
    public static class ActionItem {
        private String title;
        private int    textRightDrawable;
        private int    drawable;
        private Action action;

        public ActionItem(int drawable, Action action) {
            this.drawable = drawable;
            this.action = action;
        }

        public ActionItem(String title, Action action) {
            this.title = title;
            this.action = action;
        }

        public ActionItem(String title, int textRightDrawable, Action action) {
            this.title = title;
            this.textRightDrawable = textRightDrawable;
            this.action = action;
        }

        public String getTitle() {
            return title;
        }

        public int getDrawable() {
            return drawable;
        }

        public int getTextRightDrawable() {
            return textRightDrawable;
        }

        public Action getAction() {
            return action;
        }
    }

    public interface Action {
        void action(View v);
    }

}
