package com.example.myapplication.ui.notifications

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class BlogPagerAdapter(private var blogList: List<Blog>): RecyclerView.Adapter<BlogPagerAdapter.BlogViewHolder>() {
    class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val techItemBackground: ImageView = itemView.findViewById(R.id.tech_item_background)
        val techItemImage: ImageView = itemView.findViewById(R.id.tech_item_image)
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val contentText: TextView = itemView.findViewById(R.id.content_text)
        val diveButton: Button = itemView.findViewById(R.id.dive_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_page_item, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogList[position]

        Picasso.get().load(blog.url).fit().centerCrop().into(holder.techItemBackground)
        Picasso.get().load(blog.url).fit().centerInside().into(holder.techItemImage)
        holder.titleText.text = blog.title
        holder.contentText.text = blog.content
        holder.diveButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse((blog.page)))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}