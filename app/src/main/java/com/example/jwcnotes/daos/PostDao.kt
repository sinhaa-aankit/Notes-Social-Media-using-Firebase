package com.example.jwcnotes.daos

import com.example.jwcnotes.models.Post
import com.example.jwcnotes.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    private val postCollection = db.collection("posts")
    private val auth = Firebase.auth

    fun addPost(text:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {

            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text,user,currentTime)
            postCollection.document().set(post)
        }

    }
}