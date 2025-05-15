package com.example.project_3final;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var tvProfileName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUid: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBack: TextView
    private lateinit var btnProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var btnUpdateName: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        tvProfileName = findViewById(R.id.tvProfileName)
        tvEmail = findViewById(R.id.tvEmail)
        tvUid = findViewById(R.id.tvUid)
        tvName = findViewById(R.id.tvName)

        val database = FirebaseDatabase.getInstance("https://project-3-1ed87-default-rtdb.asia-southeast1.firebasedatabase.app")

        val user = auth.currentUser
        if (user != null) {
            tvEmail.text = "${user.email}"
            tvUid.text = "${user.uid}"
        } else {
            tvEmail.text = "Chưa đăng nhập"
            tvUid.text = "???"
        }

        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = database.getReference("users").child(uid)
            userRef.child("displayName").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(String::class.java)
                    tvName.text = name ?: "Chưa có tên"
                    tvProfileName.text = name ?: "Chưa có tên"
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ProfileActivity, "Lỗi khi tải tên: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            tvName.text = "Chưa đăng nhập"
            tvProfileName.text = "Chưa đăng nhập"
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

//        btnProfile = findViewById(R.id.imgProfile)
//        btnProfile.setOnClickListener{
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        btnUpdateName = findViewById(R.id.imgEdit)
        btnUpdateName.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.layout_dialog_editname, null)
            val newName = dialogView.findViewById<EditText>(R.id.edtName)

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                val name = newName.text.toString()
                if (name.isNotBlank()) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
                    val databaseRef = database.getReference("users").child(uid)
                    databaseRef.child("displayName").setValue(name)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Cập nhật tên thành công ($name)!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this,"Lỗi lưu database: ${e.message}",Toast.LENGTH_SHORT).show()
                        }
                    dialog.dismiss()
                } else {
                    Toast.makeText(this,"Vui lòng không để trống!",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}