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
import com.hug.takerest.shots.model.Comments;
import com.hug.takerest.shots.ui.UserActivity;
import com.hug.takerest.util.TimeUtil;

import java.text.ParseException;
import java.util.List;

/**
 * Created by HStan on 2017/4/12.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ItemViewHolder> {
    private Context context;
    private List<Comments> list;

    public interface OnItemClickLitener {
        void onItemClick(View avator, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public CommentsAdapter(Context context, List<Comments> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final Comments comments = list.get(position);
        holder.avator.setImageURI(comments.getUser().getAvatar_url());
        holder.name.setText(comments.getUser().getName());
        holder.likes.setText(comments.getLikes_count()+"");
        String system_time = TimeUtil.getCurrentTime_Today();
        String update_time = comments.getUpdated_at().replace("T"," ").replace("Z","");
        String differ = "";
        try {
            differ = TimeUtil.DifferTime(update_time,system_time);
        } catch (ParseException e) {
            differ = "Time Error";
        }
        holder.time.setText(differ);
        if (comments.getBody() != null) {
            CharSequence charSequence = Html.fromHtml(comments.getBody());
            holder.description.setText(charSequence);
        } else {
            holder.description.setText(context.getString(R.string.no_description));
        }
        holder.avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserActivity.class);
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
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView avator;
        TextView name, description, time, likes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            avator = (SimpleDraweeView) itemView.findViewById(R.id.comment_avator);
            name = (TextView) itemView.findViewById(R.id.comment_name);
            description = (TextView) itemView.findViewById(R.id.comment);
            time = (TextView) itemView.findViewById(R.id.comment_time);
            likes = (TextView) itemView.findViewById(R.id.comment_like_count);
        }
    }
}
