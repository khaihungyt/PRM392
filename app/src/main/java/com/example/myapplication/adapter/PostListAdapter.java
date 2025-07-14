package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bean.PostBean;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    private List<PostBean> postlist;
    private Context context;
    private TextView tvPostId;
    private TextView tvPostName;
    private TextView tvPostTitle;
    private TextView tvBody;
    private TextView tvPostDelete;
    private TextView tvPostView;
    public PostListAdapter(List<PostBean> postlist, Context context) {
        this.postlist = postlist;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_items,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostBean post = postlist.get(position);
        tvPostId.setText(String.valueOf(post.getId()));
       // tvPostName.setText(post.getBody());
        // tvPostTitle.setText(post.getTitle());
        //tvBody.setText(post.getBody());

        tvPostDelete.setOnClickListener(v -> {
            // Xử lý xóa bài viết
        });

        tvPostView.setOnClickListener(v -> {
            // Xử lý xem chi tiết
        });
    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public PostViewHolder(@NonNull View itemview) {
            super(itemview);
            tvPostId = itemview.findViewById(R.id.tvPostId);
            tvPostName=itemview.findViewById(R.id.tvPostName);
            tvPostTitle = itemview.findViewById(R.id.tvPostTitle);
            tvBody = itemview.findViewById(R.id.tvBody);
            tvPostDelete = itemview.findViewById(R.id.tvPostDelete);
            tvPostView = itemview.findViewById(R.id.tvPostView);
        }

        @Override
        public void onClick(View v) {

        }
    }


}
