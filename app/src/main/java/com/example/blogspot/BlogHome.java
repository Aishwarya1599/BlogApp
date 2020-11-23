package com.example.blogspot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BlogHome extends AppCompatActivity {

    ListView blogs;
    DatabaseReference blogreference;
    List<Posts> plist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_home);

        blogs = findViewById(R.id.listviewposts);
        plist = new ArrayList<>();
        blogreference = FirebaseDatabase.getInstance().getReference().child("blogs");

        blogreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plist.clear();
                for(DataSnapshot plistsnapshot : dataSnapshot.getChildren()){
                    Posts post = plistsnapshot.getValue(Posts.class);
                    plist.add(post);
                }
                PostList adapter = new PostList(BlogHome.this, plist);
                blogs.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
