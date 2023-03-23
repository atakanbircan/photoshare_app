package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.squareup.picasso.Picasso

class HaberRecyclerAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<HaberRecyclerAdapter.PostHolder>() {


    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recycler_row_kullanici_email:TextView = itemView.findViewById<TextView?>(R.id.recycler_row_kullanici_email)
        val recycler_row_yorum_text:TextView =itemView.findViewById(R.id.recycler_row_yorum_text)
        val recycler_row_imageview:ImageView = itemView.findViewById(R.id.recycler_row_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recycler_row_kullanici_email.text = postList[position].kullaniciEmail
        holder.recycler_row_yorum_text.text = postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.recycler_row_imageview)
    }

    override fun getItemCount(): Int {
        return  postList.size
    }
}