package com.example.myapplication.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.bumptech.glide.Glide
import com.example.myapplication.ProfileDetailActivity
import com.squareup.picasso.Picasso


class ProfileListAdapter(val items: List<Profile>, val context: Context) :
    RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: Profile, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.profile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pf_img: ImageView = itemView.findViewById(R.id.pfImg)
        private var pf_phone: TextView = itemView.findViewById(R.id.pfPhone)
        private var pf_name: TextView = itemView.findViewById(R.id.pfName)


        fun bind(item: Profile) {
            pf_phone.text = item.phone
            pf_name.text = item.name

            if (item.photo == null) {
//                Glide.with(itemView).load(R.mipmap.ic_launcher_round).into(pf_img)
                Picasso.get().load("https://github.com/madcamp-2023/w1/assets/79096116/c8bad84c-98e7-491c-88ba-e6fb692728cc").into(pf_img)
            } else {
//                Glide.with(itemView).load(item.photo).into(pf_img)
                Picasso.get().load(item.photo).into(pf_img)
            }
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
            itemView.setOnClickListener {
                Intent(context, ProfileDetailActivity::class.java).apply {
                    putExtra("data_img", item.photo)
                    putExtra("data_name", item.name)
                    putExtra("data_phone", item.phone)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }

    }
}

