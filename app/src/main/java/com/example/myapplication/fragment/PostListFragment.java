package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ProductListActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.PostListAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.PostBean;
import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.webservice.MyApiEndpointInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostListFragment newInstance(String param1, String param2) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private final List<PostBean> postList = new ArrayList<>();
    private PostListAdapter postListAdapter;
    public Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        postListAdapter = new PostListAdapter(postList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postListAdapter);
        getPosts();

        return view;


    }

    private void getPosts() {
        String baseUrl = "https://jsonplaceholder.typicode.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiEndpointInterface myApiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);
        Call<List<PostBean>> call = myApiEndpointInterface.getPosts();

        call.enqueue(new retrofit2.Callback<List<PostBean>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostBean>> call,
                                   @NonNull retrofit2.Response<List<PostBean>> response) {

                    List<PostBean> posts = response.body();
                    postList.clear();
                    assert posts != null;
                    postList.addAll(posts);
                    postListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PostBean>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }); // <-- sửa lại dấu đóng ngoặc ở đây
            }
        });
    }
}