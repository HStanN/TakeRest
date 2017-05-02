package com.hug.takerest.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.movie.model.Director;

import java.util.List;

/**
 * Created by HStan on 2017/3/13.
 */

public class DirectorAdapter extends RecyclerView.Adapter<DirectorAdapter.ItemViewHolder> {
    protected List<?> list;
    protected Context context;
    LayoutInflater inflater;
    private int type = 1;

    public static int SMALL = 1;
    public static int NORMAL = 2;
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    protected OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public DirectorAdapter(Context context, int type,List<?> list) {
        this.list = list;
        this.context = context;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (type == SMALL){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.director_actor_item, parent, false);
        }
        if (type == NORMAL){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direcotr_actor_normal_item, parent, false);
        }
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
            Director directorsBean = (Director) list.get(position);
            String director_name = directorsBean.getName();
        if (type == SMALL){
            if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getSmall() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getSmall());
                holder.avator.setImageURI(director_avator);
            }else if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getMedium() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getMedium());
                holder.avator.setImageURI(director_avator);
            }else if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getLarge() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getLarge());
                holder.avator.setImageURI(director_avator);
            }else{
            }
        }else if(type == NORMAL){
            if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getMedium() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getMedium());
                holder.avator.setImageURI(director_avator);
            }else if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getSmall() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getSmall());
                holder.avator.setImageURI(director_avator);
            }else if (directorsBean.getAvatars() != null && directorsBean.getAvatars().getLarge() != null){
                Uri director_avator = Uri.parse(directorsBean.getAvatars().getLarge());
                holder.avator.setImageURI(director_avator);
            }else{
            }
        }
            if (position == 0){
                holder.type.setText("导演");
            }else if (position == 1){
                holder.type.setText("主演");
            }else{
                holder.type.setText("");
            }
            holder.name.setText(director_name);
            holder.avator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLitener!= null){
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name, type;
        private SimpleDraweeView avator;

        public ItemViewHolder(View itemView) {
            super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                type = (TextView) itemView.findViewById(R.id.director_or_actor);
                avator = (SimpleDraweeView) itemView.findViewById(R.id.avator);
        }
    }
}
