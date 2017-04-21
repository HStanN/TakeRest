package com.hug.takerest.shots.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.ui.UserActivity;

import java.util.List;

/**
 * Created by HStan on 2017/4/6.
 */

public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.ItemViewHolder> {
    private Context context;
    private List<Shot> list;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public interface OnItemClickLitener {
        void onItemClick(View avator, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ShotsAdapter(Context context, List<Shot> list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER){
            return;
        }
        final Shot shot = list.get(position);
        holder.title.setText(shot.getTitle());
        if (shot.getDescription() != null){
            CharSequence charSequence=Html.fromHtml(shot.getDescription());
            holder.desc.setText(charSequence);
        }else{
            holder.desc.setText(context.getString(R.string.no_description));
        }
        if (shot.isAnimated()){
            holder.gif.setVisibility(View.VISIBLE);
        }else{
            holder.gif.setVisibility(View.GONE);
        }
        holder.name.setText(shot.getUser().getName());
        holder.like_number.setText(shot.getLikes_count()+"");
        holder.view_number.setText(shot.getViews_count()+"");
        holder.comment_number.setText(shot.getComments_count()+"");
        holder.avator.setImageURI(shot.getUser().getAvatar_url());
        if (shot.getImages().getNormal() != null){
            holder.image.setImageURI(shot.getImages().getNormal());
        }else if (shot.getImages().getTeaser() != null){
            holder.image.setImageURI(shot.getImages().getTeaser());
        }else if (shot.getImages().getHidpi() != null){
            holder.image.setImageURI(shot.getImages().getHidpi());
        }
        holder.avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("id",list.get(position).getUser().getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity)context, holder.avator, "avator");
                    context.startActivity(intent, options.toBundle());
                }else {
                    context.startActivity(intent);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(holder.avator,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,name,like_number,view_number,comment_number,gif;
        SimpleDraweeView avator,image;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.shot_title);
            desc= (TextView) itemView.findViewById(R.id.description);
            name = (TextView) itemView.findViewById(R.id.user_name);
            like_number = (TextView) itemView.findViewById(R.id.like_number);
            view_number = (TextView) itemView.findViewById(R.id.view_number);
            comment_number = (TextView) itemView.findViewById(R.id.comments_number);
            avator = (SimpleDraweeView) itemView.findViewById(R.id.avator);
            image = (SimpleDraweeView) itemView.findViewById(R.id.shot_image);
            gif = (TextView) itemView.findViewById(R.id.gif_icon);
        }
    }
}