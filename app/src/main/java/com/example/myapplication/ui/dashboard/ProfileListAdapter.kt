package com.example.myapplication.ui.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.bumptech.glide.Glide
import com.example.myapplication.ProfileDetailActivity
import com.squareup.picasso.Picasso
import java.text.Collator


class ProfileListAdapter(var items: ArrayList<Profile>, val context: Context) :
    RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: Profile, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    val initialItem = ArrayList<Profile>().apply {
        items.let { addAll(it) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: ArrayList<Profile> = ArrayList()
                if (constraint.isNullOrEmpty()) {
                    initialItem.let { filteredList.addAll(it) }
                } else {
                    val query = constraint.toString().trim()
                    initialItem.forEach {
                        if (it.name!!.contains(query)) {
                            filteredList.add(it)
                        }
                        if (it.phone!!.contains(query)) {
                            filteredList.add(it)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is ArrayList<*>) {
                    items.clear()
                    items.addAll(results.values as ArrayList<Profile>)
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun updateList(newItems: ArrayList<Profile>) {
        items = newItems
        Log.e("Adapter", "${items.size}^^^")
        notifyDataSetChanged()
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
        Log.e("Adapter", "${items.size}^^^^^")

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pf_img: ImageView = itemView.findViewById(R.id.pfImg)
        private var pf_phone: TextView = itemView.findViewById(R.id.pfPhone)
        private var pf_name: TextView = itemView.findViewById(R.id.pfName)


        fun bind(item: Profile) {
            pf_phone.text = item.phone
            pf_name.text = item.name

            if (item.photo == null) {
                Picasso.get()
                    .load("https://github.com/madcamp-2023/w1/assets/79096116/c8bad84c-98e7-491c-88ba-e6fb692728cc")
                    .into(pf_img)
            } else {
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

