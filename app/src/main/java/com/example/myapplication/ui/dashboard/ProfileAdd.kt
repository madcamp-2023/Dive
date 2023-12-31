package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class ProfileAddActivity : AppCompatActivity() {
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private val dashboardFragment = DashboardFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_add)

        val addName = findViewById<EditText>(R.id.add_name)
        val addContact = findViewById<EditText>(R.id.add_contact)
        val submitBtn = findViewById<Button>(R.id.btn_submit)

        submitBtn.setOnClickListener {
            val name = addName.text.toString()
            val contact = addContact.text.toString()

            addName.text.clear()
            addContact.text.clear()

            val data = Intent()
            data.putExtra("name", name)
            data.putExtra("phone", contact)
            data.putExtra(
                "photo",
                "https://github.com/madcamp-2023/w1/assets/79096116/607e534b-a534-450b-a25d-581b35c92159"
            )

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

}