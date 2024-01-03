package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editPhone: EditText
    private lateinit var editPhoto: ImageView
    private lateinit var submitBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var backBtn: ImageButton

    fun createIntent(name: String?, phone:String?, photo: String?, del: Boolean): Intent {
        val data = Intent()
        data.putExtra("name", name)
        data.putExtra("phone", phone)
        data.putExtra("photo", photo)
        data.putExtra("del", del)
        return data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_edit)

        val dataName = intent.getStringExtra("name")
        val dataPhone = intent.getStringExtra("phone")
        val dataImg = intent.getStringExtra("photo")

        editName = findViewById(R.id.edit_name)
        editPhone = findViewById(R.id.edit_contact)
        editPhoto = findViewById(R.id.edit_img)
        submitBtn = findViewById(R.id.edit_btn_submit)
        deleteBtn = findViewById(R.id.delete_btn_submit)
        backBtn = findViewById(R.id.backButtonEdit)

        // initialize displayed data with original data
        editName.setText(dataName)
        editPhone.setText(dataPhone)
        Picasso.get().load(dataImg).into(editPhoto)

        submitBtn.setOnClickListener {
            val name = editName.text.toString()
            val contact = editPhone.text.toString()

            val data = createIntent(name, contact, dataImg, false)

            setResult(Activity.RESULT_OK, data)
            finish()
        }
        deleteBtn.setOnClickListener {
            val name = editName.text.toString()
            val contact = editPhone.text.toString()

            val data = createIntent(name, contact, dataImg, true)

            setResult(Activity.RESULT_OK, data)
            finish()
        }
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}