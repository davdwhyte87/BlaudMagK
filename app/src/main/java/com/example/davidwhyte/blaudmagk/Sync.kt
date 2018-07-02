package com.example.davidwhyte.blaudmagk

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Sync(){
    fun likePost(post:Post){
        //this function gets a post key. and adds a user id to the userlikes list
        val ref= FirebaseDatabase.getInstance().getReference("post")
        ref.child(post.key).setValue(post)
    }
}