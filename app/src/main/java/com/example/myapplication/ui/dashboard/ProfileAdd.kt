package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.SubMenu
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class ProfileAddActivity : AppCompatActivity() {
    private lateinit var group: RadioGroup
    private lateinit var addName: EditText
    private lateinit var addContact: EditText
    private lateinit var submitBtn: Button

    // Function to handle button click and apply selected effect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_add)

        group = findViewById(R.id.memoji_group)
        addName = findViewById(R.id.add_name)
        addContact = findViewById(R.id.add_contact)
        submitBtn = findViewById(R.id.btn_submit)
        var url =
            "https://github.com/madcamp-2023/w1/assets/79096116/607e534b-a534-450b-a25d-581b35c92159"
//        url =
//            "https://github.com/madcamp-2023/w1/assets/79096116/ed170c2a-bd80-452d-9f81-d7ffe3e61435"

        group.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.btn_memoji_m -> url = "https://github.com/madcamp-2023/w1/assets/79096116/607e534b-a534-450b-a25d-581b35c92159"
                R.id.btn_memoji_fm -> url = "https://github.com/madcamp-2023/w1/assets/79096116/ed170c2a-bd80-452d-9f81-d7ffe3e61435"
            }
        }
        submitBtn.setOnClickListener {
            val name = addName.text.toString()
            val contact = addContact.text.toString()

            addName.text.clear()
            addContact.text.clear()

            val data = Intent()
            data.putExtra("name", name)
            data.putExtra("phone", contact)
            data.putExtra("photo", url)

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

}