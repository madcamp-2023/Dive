package com.example.myapplication.ui.home

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ImageAdapter(private var images: List<ImageData>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val cellSize = Resources.getSystem().displayMetrics.widthPixels / 2 // 3은 column count
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.layoutParams.width = cellSize
        imageView.layoutParams.height = cellSize
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.imageView.setImageURI(image.uri)
        // 이미지 클릭 이벤트 설정

        holder.imageView.setOnClickListener {
            val intent = Intent(holder.imageView.context, ImageDetail::class.java)
            intent.putExtra("imageUri", image.uri)
            intent.putExtra("imageDate", image.date)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = images.size

    fun updateImages(newImages: List<ImageData>) {
        images = newImages
        notifyDataSetChanged()
    }
}