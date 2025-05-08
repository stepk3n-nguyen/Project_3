package com.example.project_3final;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var tvEmail: TextView
    private lateinit var tvUid: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBack: TextView
    private lateinit var btnProfile: ImageView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        tvEmail = findViewById(R.id.tvEmail)
        tvUid = findViewById(R.id.tvUid)


        val user = auth.currentUser

        if (user != null) {
            tvEmail.text = "${user.email}"
            tvUid.text = "${user.uid}"
        } else {
            tvEmail.text = "Chưa đăng nhập"
            tvUid.text = "???"
        }

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnProfile = findViewById(R.id.imgProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}