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
import android.widget.LinearLayout
import android.widget.TextView


class NoteAdapter(val items:ArrayList<Post>,val context:Context):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val postItemTitle=view.findViewById<TextView>(R.id.post_title);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tview = LayoutInflater.from(parent?.context).inflate(R.layout.post_item, parent, false) as LinearLayout
        return ViewHolder(tview)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postItemTitle.text= items[position].title
    }

    override fun getItemCount(): Int {
        return items.size
    }
}