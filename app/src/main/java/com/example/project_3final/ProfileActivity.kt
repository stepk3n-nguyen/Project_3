package com.example.project_3final;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var tvEmail: TextView
    private lateinit var tvUid: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBack: TextView
    private lateinit var btnProfile: ImageView
    private lateinit var edtName: EditText
    private lateinit var btnUpdateName: ImageButton
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
            val dialogView = layoutInflater.inflate(R.layout.layout_dialog_logout, null)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()
            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Đã đăng xuất tài khoản", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        btnProfile = findViewById(R.id.imgProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnUpdateName = findViewById(R.id.imgEdit)
        btnUpdateName.setOnClickListener{
            val newName = edtName.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Ghi tên mới vào Realtime Database
                        val uid = user.uid
                        val databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
                        databaseRef.child("displayName").setValue(newName)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Cập nhật tên thành công!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Lỗi lưu database: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        Toast.makeText(this, "Cập nhật tên thành công!", Toast.LENGTH_SHORT).show()
                    } else {
                        val error = task.exception?.message ?: "Lỗi không xác định"
                        Toast.makeText(this, "Thất bại: $error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val edtName = findViewById<EditText>(R.id.edtName)
        edtName.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.toString().uppercase()
        })
    }
}