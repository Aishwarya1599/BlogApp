package com.example.blogspot;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PostList extends ArrayAdapter <Posts> {

    private Activity context;
    private List<Posts> plist;

    public PostList(Activity context, List<Posts> plist){

        super(context, R.layout.list_layout, plist);
        this.context=context;
        this.plist=plist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.list_layout, null, true);
        TextView name = listviewitem.findViewById(R.id.title);
        TextView content = listviewitem.findViewById(R.id.desc);

        Posts post = plist.get(position);
        name.setText(post.getPostname());
        content.setText(post.getPostdesc());
        return listviewitem;

    }
}
