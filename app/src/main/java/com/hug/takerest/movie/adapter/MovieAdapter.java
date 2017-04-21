package com.hug.takerest.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hug.takerest.R;
import com.hug.takerest.WebActivity;
import com.hug.takerest.movie.model.Director;
import com.hug.takerest.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hu on 2016/5/19.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {
    private List<?> list;
    private Context context;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MovieAdapter(Context context ,List<?> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (list.get(0) instanceof Movie) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (list.get(position) instanceof Movie) {
            final Movie movie = (Movie) list.get(position);
            String img_url = "";
            if (movie.getImages().getLarge() != null){
                img_url = movie.getImages().getLarge();
            }else if (movie.getImages().getMedium() != null){
                img_url = movie.getImages().getMedium();
            }else if (movie.getImages().getSmall() != null){
                img_url = movie.getImages().getSmall();
            }
            Uri uri = Uri.parse(img_url);
            holder.images.setImageURI(uri);
            holder.title.setText(movie.getTitle());
            holder.collect.setText(movie.getCollect_count() + "人看过");
            holder.orig_title.setText(movie.getOriginal_title());
            holder.rating.setText(movie.getRating().getAverage() + "");
            StringBuilder mtype = new StringBuilder("");
            for (int i = 0; i < movie.getGenres().size(); i++) {
                if (i < movie.getGenres().size() - 1) {
                    StringBuilder str = new StringBuilder(movie.getGenres().get(i) + "、");
                    mtype.append(str);
                } else {
                    mtype.append(movie.getGenres().get(i));
                }
            }
            holder.type.setText(mtype);
            holder.root.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            holder.root.setHasFixedSize(true);
            final List<Director> list = new ArrayList<>();
            list.addAll(movie.getDirectors());
            list.addAll(movie.getCasts());
            DirectorAdapter adapter = new DirectorAdapter(context,DirectorAdapter.SMALL,list);
            holder.root.setAdapter(adapter);
            adapter.setOnItemClickLitener(new DirectorAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    String url = list.get(position).getAlt();
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url",url);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.images,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title, orig_title, rating, type, collect;
        private SimpleDraweeView images;
        private RecyclerView root;

        public ItemViewHolder(View itemView) {
            super(itemView);
            if (list.get(0) instanceof Movie) {
                title = (TextView) itemView.findViewById(R.id.title);
                orig_title = (TextView) itemView.findViewById(R.id.original_title);
                rating = (TextView) itemView.findViewById(R.id.rating);
                type = (TextView) itemView.findViewById(R.id.movie_type);
                images = (SimpleDraweeView) itemView.findViewById(R.id.image);
                collect = (TextView) itemView.findViewById(R.id.collect_number);
                root = (RecyclerView) itemView.findViewById(R.id.director_or_actor_list);
            }
        }
    }
}
