package com.example.myapplication.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


class ProfileListAdapter(val items: ArrayList<Profile>): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.profile, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.pf_name.text = currentItem.pf_name
        holder.pf_phone.text = currentItem.pf_phone
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        lateinit var pf_img: ImageView
        lateinit var pf_phone: TextView
        lateinit var pf_name: TextView
        init {
//            pf_img = itemView.findViewById(R.id.pfImg)
            pf_phone = itemView.findViewById(R.id.pfPhone)
            pf_name = itemView.findViewById(R.id.pfName)
        }
    }
}

