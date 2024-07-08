package com.example.loginapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapi.R

class GalleryAdapter(private val imageUrls: List<String>, private val listener: OnImageClickListener) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    interface OnImageClickListener {
        fun onImageClick(imageUrl: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.imgGallery)
        holder.itemView.setOnClickListener {
            listener.onImageClick(imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGallery: ImageView = itemView.findViewById(R.id.imgGallery)
    }
}
