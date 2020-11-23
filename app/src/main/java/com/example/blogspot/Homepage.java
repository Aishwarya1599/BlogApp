package com.example.blogspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;



public class Homepage extends AppCompatActivity {

    EditText postname;
    EditText desc;

    Button Post;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;
    ListView blogs;
    List<Posts> plist;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        postname = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        Post = findViewById(R.id.post);
        databaseReference = FirebaseDatabase.getInstance().getReference("blogs");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        blogs = findViewById(R.id.listviewblog);
        plist = new ArrayList<>();

        blogs.setVisibility(View.INVISIBLE);
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", postname.getText().toString().trim());
             addPost();
             startActivity(new Intent(Homepage.this, BlogHome.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plist.clear();
                for(DataSnapshot plistsnapshot : dataSnapshot.getChildren()){
                    Posts post = plistsnapshot.getValue(Posts.class);
                    plist.add(post);
                }
                PostList adapter = new PostList(Homepage.this, plist);
                blogs.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addPost(){
        String posttitle = postname.getText().toString().trim();
        String postdesc = desc.getText().toString().trim();

        if(!TextUtils.isEmpty(posttitle) && (!TextUtils.isEmpty(postdesc))){

            String id = databaseReference.push().getKey();
            String userid = mCurrentUser.getUid();
            Posts post = new Posts(id, posttitle, postdesc, userid);
            databaseReference.child(id).setValue(post);

            Toast.makeText(this, "Post added!", Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(this, "Please enter the fields", Toast.LENGTH_SHORT).show();
        }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.item2:
            mAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
            break;

            case R.id.item1:
                startActivity(new Intent(this, BlogHome.class));
                break;

        }
        return true;
    }




}





