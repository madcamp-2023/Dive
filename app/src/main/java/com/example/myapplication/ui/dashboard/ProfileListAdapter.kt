package com.example.myapplication.ui.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.bumptech.glide.Glide
import com.example.myapplication.ProfileDetailActivity


class ProfileListAdapter(val items: ArrayList<Profile>) :
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
//        val currentItem = items[position]
//        if (currentItem.pf_img != 0) {
//            holder.pf_img.setImageResource(currentItem.pf_img)
//        } else {
//            holder.pf_img.setImageResource(R.mipmap.ic_launcher)
//        }
//        holder.pf_name.text = currentItem.pf_name
//        holder.pf_phone.text = currentItem.pf_phone
//        holder.itemView.setOnClickListener {
//            Intent(, )
//        }
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pf_img: ImageView = itemView.findViewById(R.id.pfImg)
        private var pf_phone: TextView = itemView.findViewById(R.id.pfPhone)
        private var pf_name: TextView = itemView.findViewById(R.id.pfName)


        fun bind(item: Profile) {
            pf_phone.text = item.pf_phone
            pf_name.text = item.pf_name
            Glide.with(itemView).load(item.pf_img).into(pf_img)
//            if (item.pf_img == null) {
//                pf_img.setImageResource(R.mipmap.ic_launcher)
//            } else {
//                pf_img.setImageResource(item.pf_img)
//            }
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, item, pos)
                }
            }
//            itemView.setOnClickListener {
//                Intent(context, ProfileDetailActivity::class.java).apply {
//                    putExtra("data_img", item.pf_img.toString())
//                    putExtra("data_name", item.pf_name)
//                    putExtra("data_phone", item.pf_phone)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { context.startActivity(this)}
//            }
        }

//        init {
//            itemView.setOnClickListener(View.OnClickListener {
//                itemClickListener?.onItemClick(adapterPosition)
//            })
//        }

    }
}

