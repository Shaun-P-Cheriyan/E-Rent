package com.erent.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.erent.application.Services;
import com.erent.logic.IPostLogic;
import com.erent.logic.PostLogic;
import com.erent.R;
import com.erent.objects.Post;
import com.erent.persistence.stubs.PostPersistence;

import java.util.List;

public class HomepageFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    IPostLogic postLogic;
    List<Post> posts;
    ImageButton createPostButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.home_recycler_view);
        postLogic = Services.getPostLogic();
        createPostButton = (ImageButton) getView().findViewById(R.id.buttonCreatePost);
        posts = postLogic.getPostNotByUser(Services.getCurrentUser());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new HomepageAdapter(getContext().getApplicationContext(), posts, this));

        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        int postID = posts.get(position).getPostID();

        Intent switchActivityIntent = new Intent(getActivity(), PostActivity.class);

        switchActivityIntent.putExtra("postID", postID);

        startActivity(switchActivityIntent);
    }
}