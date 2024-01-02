package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.dashboard.DashboardViewModel
import com.example.myapplication.ui.dashboard.ProfileEditActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class ProfileDetailActivity : AppCompatActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private var idx by Delegates.notNull<Int>()

    private val startForResultEdit =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val sendData = Intent()

                val recPhoto = data?.getStringExtra("photo")
                val recName = data?.getStringExtra("name")
                val recPhone = data?.getStringExtra("phone")

                sendData.putExtra("photo", recPhoto)
                sendData.putExtra("name", recName)
                sendData.putExtra("phone", recPhone)
                sendData.putExtra("idx", idx)


                if (data?.getBooleanExtra("del", false) == false) {
                    sendData.putExtra("del", false)
                } else {
                    sendData.putExtra("del", true)
                }
                setResult(Activity.RESULT_OK, sendData)
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_detail)
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        val detail_name = findViewById<TextView>(R.id.detail_name)
        val detail_phone = findViewById<TextView>(R.id.detail_phone)
        val detail_img = findViewById<ImageView>(R.id.detail_img)
        val backBtn = findViewById<ImageButton>(R.id.backButton)
        val editBtn = findViewById<FloatingActionButton>(R.id.editBtn)
        val phoneBtn = findViewById<FloatingActionButton>(R.id.phoneButton)
        val msgBtn = findViewById<FloatingActionButton>(R.id.messageButton)

        val data_img = intent.getStringExtra("data_img")
        val data_name = intent.getStringExtra("data_name")
        val data_phone = intent.getStringExtra("data_phone")
        idx = intent.getIntExtra("idx", -1)


        backBtn.setOnClickListener {
            val data = Intent()
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        editBtn.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            intent.putExtra("photo", data_img)
            intent.putExtra("name", data_name)
            intent.putExtra("phone", data_phone)
            startForResultEdit.launch(intent)
        }

        phoneBtn.setOnClickListener {
            val myUri = Uri.parse("tel:$data_phone")
            val intent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(intent)
        }

        msgBtn.setOnClickListener {
            val myUri = Uri.parse("smsto:$data_phone")
            val intent = Intent(Intent.ACTION_SENDTO, myUri)
            startActivity(intent)
        }

        Picasso.get().load(data_img).into(detail_img)
        detail_name.text = data_name
        detail_phone.text = data_phone

    }
}