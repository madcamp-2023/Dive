package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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

    // Function to handle button click and apply selected effect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_edit)

        val dataName = intent.getStringExtra("name")
        val dataPhone = intent.getStringExtra("phone")
        val dataImg = intent.getStringExtra("photo")
        val idx = intent.getIntExtra("idx", -1)

        editName = findViewById(R.id.edit_name)
        editPhone = findViewById(R.id.edit_contact)
        editPhoto = findViewById(R.id.edit_img)
        submitBtn = findViewById(R.id.edit_btn_submit)

        // initialize displayed data with original data
        editName.setText(dataName)
        editPhone.setText(dataPhone)
        Picasso.get().load(dataImg).into(editPhoto)

        submitBtn.setOnClickListener {
            val name = editName.text.toString()
            val contact = editPhone.text.toString()

//            editName.text.clear()
//            editPhone.text.clear()

            val data = Intent()
            data.putExtra("name", name)
            data.putExtra("phone", contact)
            data.putExtra("photo", dataImg)
            data.putExtra("idx", idx)

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}