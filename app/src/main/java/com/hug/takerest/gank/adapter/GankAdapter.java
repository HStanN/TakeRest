package com.hug.takerest.gank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.gank.model.Gank;
import com.hug.takerest.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HStan on 2017/3/30.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.ItemViewHolder> {
    protected List<Gank> mlist;
    protected Context context;
    private List<Integer> heights;
    LayoutInflater inflater;

    public interface OnItemClickLitener {
        void onItemClick(View view ,Gank gank);
        void onLongClick(View view ,Gank gank,int x,int y);
    }

    protected OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public GankAdapter(Context context, List<Gank> list) {
        this.mlist = list;
        this.context = context;
        heights = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            heights.add((int)(200+Math.random()*400));
        }
        inflater = LayoutInflater.from(context);
    }

    public void dataInsert(int position,List<Gank> list){
        for (int i =heights.size(); i < mlist.size(); i++) {
            heights.add(i,(int)(200+Math.random()*400));

        }
        this.mlist = list;
        notifyItemInserted(position);
    }

    public void dataChanged(List<Gank> list){
        this.mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizi_layout, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        String url = mlist.get(position).getUrl()+ "?imageView2/2/w/"+((ScreenUtils.getScreenWidth(context)-dp2px(context,12))/2);
        holder.meizi.getLayoutParams().height = heights.get(position);
        holder.meizi.setImageURI(url);
        holder.meizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener !=null){
                    mOnItemClickLitener.onItemClick(holder.meizi,mlist.get(position));
                }
            }
        });
        holder.meizi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();
                holder.meizi.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mOnItemClickLitener !=null){
                            mOnItemClickLitener.onLongClick(holder.meizi,mlist.get(position),x,y);
                        }
                        return true;
                    }
                });
                return false;
            }
        });
        if (holder.itemView.getTag() == null) {
            holder.itemView.setTag(position);
            holder.bind(position);
        }
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView meizi;
        CardView cardView;
        int position;
        public ItemViewHolder(View itemView) {
            super(itemView);
            meizi = (SimpleDraweeView) itemView.findViewById(R.id.iv_meizi);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
        public void bind(int position){
            this.position = position;
        }
    }
}
