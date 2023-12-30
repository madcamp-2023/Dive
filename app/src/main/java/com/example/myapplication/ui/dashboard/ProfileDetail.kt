package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.ui.dashboard.Profile
import com.example.myapplication.databinding.ProfileDetailBinding

class ProfileDetailActivity : AppCompatActivity() {
    lateinit var datas : Profile
    private lateinit var binding: ProfileDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_detail)

        binding = ProfileDetailBinding.inflate(layoutInflater)
        val detail_name = binding.detailName
        val detail_img = binding.detailImg

        val data_img = intent.getStringExtra("data")
        val data_name = intent.getStringExtra("data_name")
//        val data_phone = intent.getStringExtra("data_phone")

        Glide.with(this).load(data_img!!.toInt()).into(detail_img)
        detail_name.text = data_name
//        pf_phone.text = data_phone

    }
}