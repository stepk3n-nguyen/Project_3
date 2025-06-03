package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.NumberFormat
import java.util.Locale

class DeliveryActivity : AppCompatActivity() {
    private lateinit var edRecipientName : EditText
    private lateinit var edPhoneNumber : EditText
    private lateinit var tvPrice : TextView
    private lateinit var btnConfirm : Button
    private lateinit var btnBack : TextView
    private lateinit var spinnerCity : Spinner
    private lateinit var spinnerDistrict : Spinner
    private lateinit var spinnerWard : Spinner
    private lateinit var auth : FirebaseAuth

    private val locationData = mapOf(
        "Hà Nội" to mapOf(
            "Cầu Giấy" to listOf("Dịch Vọng", "Yên Hòa"),
            "Hoàn Kiếm" to listOf("Hàng Bạc", "Hàng Buồm")
        ),
        "Hồ Chí Minh" to mapOf(
            "Quận 1" to listOf("Bến Nghé", "Bến Thành"),
            "Quận 3" to listOf("Phường 6", "Phường 7")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        edRecipientName = findViewById(R.id.edRecipientName)
        edPhoneNumber = findViewById(R.id.edPhoneNumber)
        spinnerCity = findViewById(R.id.spnCity)
        spinnerDistrict = findViewById(R.id.spnDistrict)
        spinnerWard = findViewById(R.id.spnWard)

        auth = FirebaseAuth.getInstance()
        setupCitySpinner()

        //total price format
        val totalPrice = CartManager.getTotalPrice()
        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
        tvPrice = findViewById(R.id.tvPrice)
        tvPrice.text = "Tổng tiền: ${formattedPrice}₫"

        //regex phone number + confirm giao hàng
        btnConfirm = findViewById(R.id.btnConfirm)
        btnConfirm.setOnClickListener{
            val phone = edPhoneNumber.text.toString().replace("\\s".toRegex(), "").trim()
            val phonePattern = Regex("^(0|\\+84)(3|5|7|8|9)[0-9]{8}$")
            if (phone == "") {
                edPhoneNumber.error = "Vui lòng nhập số điện thoại"
            } else if (phone.matches(phonePattern)) {
                Toast.makeText(this, "Đặt hàng thành công! Tổng tiền: ${formattedPrice}₫", Toast.LENGTH_LONG).show()
                CartManager.clearCart()
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                edPhoneNumber.error = "Số điện thoại không hợp lệ"
                Log.d("DEBUG_PHONE", "Phone nhập: '${phone}'")
            }
        }

        //user name auto place
        val database = FirebaseDatabase.getInstance("https://project-3-1ed87-default-rtdb.asia-southeast1.firebasedatabase.app")
//        val user = auth.currentUser
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = database.getReference("users").child(uid)
            userRef.child("displayName").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(String::class.java)
                    edRecipientName.setText(name)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DeliveryActivity, "Lỗi khi tải tên: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            edRecipientName.setText("Chưa đăng nhập")
        }

        //back button
        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            val intent = Intent(this, CartListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupCitySpinner() {
        val cities = locationData.keys.toList()
        spinnerCity.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCity = cities[position]
                val districts = locationData[selectedCity]?.keys?.toList() ?: emptyList()
                    spinnerDistrict.adapter = ArrayAdapter(this@DeliveryActivity, android.R.layout.simple_spinner_dropdown_item, districts)
                    spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                            val selectedDistrict = districts[pos]
                            val wards = locationData[selectedCity]?.get(selectedDistrict) ?: emptyList()
                            spinnerWard.adapter = ArrayAdapter(this@DeliveryActivity, android.R.layout.simple_spinner_dropdown_item, wards)
                        }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

}