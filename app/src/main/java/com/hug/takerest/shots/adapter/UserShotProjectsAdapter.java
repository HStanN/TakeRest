package com.hug.takerest.shots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;

import java.util.List;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserShotProjectsAdapter extends RecyclerView.Adapter<UserShotProjectsAdapter.ItemViewHolder> {
    private Context context;
    private List<?> list;

    public interface OnItemClickLitener {
        void onItemClick(View avator, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public UserShotProjectsAdapter(Context context, List<?> list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (list.get(0) instanceof Shot){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_shot, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_project, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (list.get(0) instanceof Shot){
            Shot shot = (Shot)list.get(position);
            holder.shot_title.setText(shot.getTitle());
            holder.shot_image.setImageURI(shot.getImages().getNormal());
            if (shot.isAnimated()){
                holder.gif_icon.setVisibility(View.VISIBLE);
            }else{
                holder.gif_icon.setVisibility(View.GONE);
            }
            if (shot.getDescription() != null){
                holder.shot_description.setText(Html.fromHtml(shot.getDescription()));
            }else{
                holder.shot_description.setText(context.getString(R.string.no_description));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLitener != null){
                        mOnItemClickLitener.onItemClick(v,position);
                    }
                }
            });
        }else{
            Project project = (Project)list.get(position);
            holder.pro_name.setText(project.getName());
            holder.pro_create.setText("Create At  :  " + project.getCreated_at().substring(0,10));
            holder.pro_update.setText("Update At  :  " + project.getUpdated_at().substring(0,10));
            holder.pro_shotcount.setText("Shots  :  " + project.getShots_count());
            if (project.getDescription() != null){
                holder.pro_description.setText(Html.fromHtml(project.getDescription()));
            }else{
                holder.pro_description.setText(context.getString(R.string.no_description));
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView shot_image;
        TextView shot_title,shot_description,gif_icon;

        TextView pro_name,pro_description,pro_create,pro_update,pro_shotcount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            if (list.get(0) instanceof Shot){
                shot_image = (SimpleDraweeView) itemView.findViewById(R.id.shot_image);
                shot_title = (TextView) itemView.findViewById(R.id.shot_title);
                shot_description = (TextView) itemView.findViewById(R.id.description);
                gif_icon = (TextView) itemView.findViewById(R.id.gif_icon);
            }else{
                pro_name = (TextView) itemView.findViewById(R.id.project_name);
                pro_description = (TextView) itemView.findViewById(R.id.project_description);
                pro_create = (TextView) itemView.findViewById(R.id.create_time);
                pro_update = (TextView) itemView.findViewById(R.id.update_time);
                pro_shotcount = (TextView) itemView.findViewById(R.id.shot_count);
            }
        }
    }
}
