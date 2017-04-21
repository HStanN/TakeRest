package com.hug.takerest.gank.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hug.takerest.R;
import com.hug.takerest.WebActivity;
import com.hug.takerest.gank.model.Gank;
import com.hug.takerest.gank.model.GankSortData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hu on 2016/7/12.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ItemViewHolder> {
    private GankSortData gankSort;
    private Context context;
    private View view;
    private List<String> category;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public DailyAdapter(Context context, GankSortData gankSort, List<String> category) {
        this.context = context;
        this.gankSort = gankSort;
        this.category = category;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    class MyClickListener implements View.OnClickListener{
        private List<Gank> list;
        private int position;
        public MyClickListener(List<Gank> list,int position){
            this.list = list;
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url",list.get(position).getUrl());
            context.startActivity(intent);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ItemViewHolder(mHeaderView);
        }
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER){
            return;
        }else {
            final int pos = getRealPosition(holder);
            List<Gank> list = new ArrayList<>();
            String tag = category.get(pos).toString();
            if (tag.equals("福利")) {
                return;
            } else {
                if (tag.equals("Android")) {
                    list = gankSort.getAndroid();
                }
                if (tag.equals("iOS")) {
                    list = gankSort.getIos();
                }
                if (tag.equals("拓展资源")) {
                    list = gankSort.getExtraResource();
                }
                if (tag.equals("前端")) {
                    list = gankSort.getHtml();
                }
                if (tag.equals("瞎推荐")) {
                    list = gankSort.getRecommend();
                }
                if (tag.equals("App")) {
                    list = gankSort.getApp();
                }
                if (tag.equals("休息视频")){
                    return;
                }
            }
            setDailyItem(list, tag, holder);
        }
    }


    private void setDailyItem(List<Gank> list, String type, ItemViewHolder holder) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView tv = new TextView(context);
            tv.setTextSize(25);
            tv.setTextColor(context.getResources().getColor(R.color.textPrimary));
            tv.setPadding(8, 8, 8, 8);
            LinearLayout.LayoutParams tvp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tvp.setMargins(10, 0, 5, 10);
            tv.setLayoutParams(tvp);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setText(type);
            linearLayout.addView(tv);

            for (int i = 0; i < list.size(); i++) {
                TextView items = new TextView(context);
                items.setPadding(15, 5, 5, 10);
                items.setTextSize(14);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 0, 5, 10);
                items.setLayoutParams(lp);
                items.setTextColor(context.getResources().getColor(R.color.textSecondary));
                String who = "";
                if (list.get(i).getWho()!= null){
                    who =  list.get(i).getWho();
                }else{
                    who = "匿名";
                }
                items.setText("*\b" + list.get(i).getDesc() + "\b\b\b" + "-----via." +who);
                linearLayout.addView(items);
                items.setOnClickListener(new MyClickListener(list,i));
            }
            holder.card.addView(linearLayout);
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? category.size() : category.size() + 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public CardView card;

        public ItemViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}

