package com.example.davidwhyte.blaudmagk

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
//        create()
        viewManager= LinearLayoutManager(this)
        val noteslist=ArrayList<Post>()
        val nlist=read()
        for (x in noteslist){
            Log.v("android-list",noteslist.toString())
        }
        viewAdapter=NoteAdapter(nlist,this)
        recyclerView=findViewById<RecyclerView>(R.id.post_recycler).apply {
            layoutManager=viewManager
            adapter=viewAdapter
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun openCreate(){
        val intent= Intent(this,CreateActivity::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
               openCreate()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun read():ArrayList<Post> {
        val posts = ArrayList<Post>()
        val ref= FirebaseDatabase.getInstance().getReference("post")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override public fun onDataChange(snapshot: DataSnapshot?){
                val children = snapshot!!.children
                children.forEach {
                    val post=it.getValue(Post::class.java)
                    posts.add(post!!)
                    Log.v("dfb",it.toString())
                }
            }

            override public fun onCancelled(p0: DatabaseError?) {
                Log.v("dfb","An error dbfirebase")
            }
        })
        var post=Post()
        post.content="this is id jskdj sjd"
        post.title="Endowen bowen life"
        posts.add(post)
        return posts
    }

    fun create(){
        val ref= FirebaseDatabase.getInstance().getReference("post")
        val note_id=ref.push().key
        var post=Post()

        post.title="Terram"
        post.content="This the shit its no lie"
        ref.child(note_id).setValue(post)
    }
}
