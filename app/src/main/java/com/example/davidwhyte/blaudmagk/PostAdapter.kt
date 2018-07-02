package com.example.davidwhyte.blaudmagk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.support.annotation.ColorRes
import android.support.annotation.MenuRes
import android.support.v7.view.menu.MenuAdapter
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase


class NoteAdapter(val items:ArrayList<Post>,val context:Context):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val postItemTitle=view.findViewById<TextView>(R.id.post_title)
        val postLikeBtn=view.findViewById<ImageButton>(R.id.like_btn)
        val postLikes=view.findViewById<TextView>(R.id.likes)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tview = LayoutInflater.from(parent?.context).inflate(R.layout.post_item, parent, false) as LinearLayout
        return ViewHolder(tview)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postItemTitle.text= items[position].title
        var post:Post=items[position]
        val user_id=1
        var user_liked=post.users_liked
        if(user_liked.contains(user_id)){
            post.liked=true
        }
        if(post.liked){
            holder.postLikeBtn.setImageDrawable(context.resources.getDrawable(R.drawable.likered))
            holder.postLikes.text=(post.likes).toString()
        }
        else{
            holder.postLikes.text=(post.likes).toString()
        }
        holder.postLikeBtn.setOnClickListener {
            if(post.liked){
                //change color of like btn
                holder.postLikeBtn.setImageDrawable(context.resources.getDrawable(R.drawable.likebtn))
                //set post as unliked
                post.liked=false
                //remove user from list of users that liked post
                user_liked.remove(user_id)
                //update the database
                var likes=post.likes--
                holder.postLikes.text=(likes).toString()
                Sync().likePost(post)
            }
            else{
                //update post that u liked
                post.liked=true
                user_liked.add(user_id)
                //make button red
                holder.postLikeBtn.setImageDrawable(context.resources.getDrawable(R.drawable.likered))
                //increase no of likes
                var likes=post.likes++
                holder.postLikes.text=(likes).toString()
                items[position]=post
                //update database
                Sync().likePost(post)
            }
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}