package com.example.blogspot;

public class Posts {

   String postid;
   String postname;
   String postdesc;
   String uid;

   public Posts()
   {

   }

    public Posts(String postid, String postname, String postdesc, String uid) {
        this.postid = postid;
        this.postname = postname;
        this.postdesc = postdesc;
        this.uid = uid;
    }

    public String getPostid() {
        return postid;
    }

    public String getPostname() {
        return postname;
    }

    public String getPostdesc() {
        return postdesc;
    }
    public String getUid() {
        return uid;
    }
}
