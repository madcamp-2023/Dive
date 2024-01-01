package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.ui.dashboard.Profile
import com.squareup.picasso.Picasso

class ProfileDetailActivity : AppCompatActivity() {
    lateinit var datas : Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_detail)

        val detail_name = findViewById<TextView>(R.id.detail_name)
        val detail_phone = findViewById<TextView>(R.id.detail_phone)
        val detail_img = findViewById<ImageView>(R.id.detail_img)
        val backBtn = findViewById<ImageButton>(R.id.backButton)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        val data_img = intent.getStringExtra("data_img")
        val data_name = intent.getStringExtra("data_name")
        val data_phone = intent.getStringExtra("data_phone")

        Picasso.get().load(data_img).into(detail_img)
        detail_name.text = data_name
        detail_phone.text = data_phone

    }
}