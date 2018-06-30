package com.example.davidwhyte.blaudmagk

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI
import java.util.*

class CreateActivity : AppCompatActivity() {
    val BROWSE_GALLERY=190
    internal var storage:FirebaseStorage?=null
    internal var IMG_URI:Uri?=null
    internal var storageReference:StorageReference?=null
    var IMG_U=0
    var DATA_U=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val f_image=findViewById<ImageButton>(R.id.f_imagebtn)
        f_image.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,BROWSE_GALLERY)
        }
        val f_title=findViewById<EditText>(R.id.f_title)
        val f_content=findViewById<EditText>(R.id.f_content)
        val btn_create=findViewById<Button>(R.id.btn_create)
        btn_create.setOnClickListener {
            if(validate()){
                var post=Post()
                post.content=f_content.text.toString()
                post.title=f_title.text.toString()
                var img_=upload_image(IMG_URI!!)
                post.image=img_
                create(post)
                if(IMG_U==1&&DATA_U==1){
                    Toast.makeText(this,"Uploaded!",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"An error occurred!",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun validate():Boolean{
        val f_title=findViewById<EditText>(R.id.f_title)
        val f_content=findViewById<EditText>(R.id.f_content)
        if(f_content.text.isEmpty()){
            Toast.makeText(this,"Some content is needed",Toast.LENGTH_SHORT).show()
            return false
        }
        if(f_title.text.isEmpty()){
            Toast.makeText(this,"A title is required",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //display image for user
        if(requestCode==BROWSE_GALLERY){
            val img_v=findViewById<ImageView>(R.id.f_image)
            img_v.visibility= View.VISIBLE
            val imgUri=data?.data
            IMG_URI=imgUri
            img_v.setImageURI(imgUri)
        }
    }

    fun upload_image(uri:Uri):String{
        storage= FirebaseStorage.getInstance()
        storageReference=storage!!.reference
        val img_id=UUID.randomUUID().toString()
        var imageRef=storageReference!!.child("images/"+img_id)
        imageRef.putFile(uri).addOnSuccessListener {
            IMG_U=1
        }
        return img_id
    }
    fun create(post:Post){
        val ref= FirebaseDatabase.getInstance().getReference("post")
        val note_id=ref.push().key
        post.key=note_id
        ref.child(note_id).setValue(post).addOnSuccessListener {
            DATA_U=1
        }
    }
}
