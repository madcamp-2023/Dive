package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.ui.dashboard.Profile

class ProfileDetailActivity : AppCompatActivity() {
    lateinit var datas : Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_detail)

        val detail_name = findViewById<TextView>(R.id.detail_name)
        val detail_phone = findViewById<TextView>(R.id.detail_phone)
        val detail_img = findViewById<ImageView>(R.id.detail_img)

        val data_img = intent.getIntExtra("data_img", R.mipmap.ic_launcher_round)
        val data_name = intent.getStringExtra("data_name")
        val data_phone = intent.getStringExtra("data_phone")

        Glide.with(this).load(data_img!!.toInt()).into(detail_img)
        detail_name.text = data_name
        detail_phone.text = data_phone

    }
}