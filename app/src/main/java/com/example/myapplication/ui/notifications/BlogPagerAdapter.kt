package com.example.myapplication.ui.notifications

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class BlogPagerAdapter(
    private var blogList: List<Blog>,
    private val onBtn1Clicked: () -> Unit,
    private val onBtn2Clicked: () -> Unit
) :
    RecyclerView.Adapter<BlogPagerAdapter.BlogViewHolder>() {
    var isMenuOpen = false

    class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val techItemBackground: ImageView = itemView.findViewById(R.id.tech_item_background)
        val techItemImage: ImageView = itemView.findViewById(R.id.tech_item_image)
        val titleText: TextView = itemView.findViewById(R.id.title_text)
        val contentText: TextView = itemView.findViewById(R.id.content_text)
        val diveButton: Button = itemView.findViewById(R.id.dive_button)
        val menuButton: Button = itemView.findViewById(R.id.menu_button)
        val btn1: Button = itemView.findViewById(R.id.action_btn1)
        val btn2: Button = itemView.findViewById(R.id.action_btn2)
        val btn3: Button = itemView.findViewById(R.id.action_btn3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.blog_page_item, parent, false)
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
        holder.menuButton.setOnClickListener { v ->
            v.animate()
                .rotationBy(360f)
                .setDuration(500)
                .withEndAction {
                    v.rotation = 0f
                    if (isMenuOpen) {
                        holder.btn1.visibility = View.INVISIBLE
                        holder.btn2.visibility = View.INVISIBLE
                        holder.btn3.visibility = View.INVISIBLE
                        isMenuOpen = false
                    } else {
                        holder.btn1.visibility = View.VISIBLE
                        holder.btn2.visibility = View.VISIBLE
//                        holder.btn3.visibility = View.VISIBLE
                        isMenuOpen = true
                    }
                }
        }

        holder.btn1.setOnClickListener {
            onBtn1Clicked()
            isMenuOpen = false
        }

        holder.btn2.setOnClickListener {
            onBtn2Clicked()
            isMenuOpen = false
        }

        if (isMenuOpen) {
            holder.btn1.visibility = View.VISIBLE
            holder.btn2.visibility = View.VISIBLE
        } else {
            holder.btn1.visibility = View.INVISIBLE
            holder.btn2.visibility = View.INVISIBLE
        }
    }

    //    private operator fun <T> LiveData<T>.get(position: Int): Blog {
//        return blogList.get(position)
//    }
    override fun getItemCount(): Int {
        return blogList.size
    }

    fun hideButtons() {
        isMenuOpen = false
        notifyDataSetChanged()
    }

    fun updateBlogs(newBlogList: List<Blog>) {
        blogList = newBlogList
        notifyDataSetChanged()
    }
}
