package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_3final.databinding.ActivityDeliveryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
//import retrofit2.Callback
//import retrofit2.Call
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.Locale
//import kotlin.io.encoding.ExperimentalEncodingApi

class DeliveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryBinding
    private lateinit var edRecipientName : EditText
    private lateinit var edPhoneNumber : EditText
    private lateinit var tvQRPrice : TextView
    private lateinit var btnConfirm : Button
    private lateinit var btnBack : TextView
    private lateinit var spinnerCity : Spinner
    private lateinit var spinnerDistrict : Spinner
    private lateinit var spinnerWard : Spinner
//    private lateinit var vietQRApi: VietQRApi
    private lateinit var imgVietQR : ImageView
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
//        setContentView(R.layout.activity_delivery)
        binding = ActivityDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edRecipientName = binding.edRecipientName
        edPhoneNumber = binding.edPhoneNumber
        spinnerCity = binding.spnCity
        spinnerDistrict = binding.spnDistrict
        spinnerWard = binding.spnWard
        imgVietQR = binding.imgVietQR
        auth = FirebaseAuth.getInstance()
        setupCitySpinner()

        //total price format----------------------------------------//
        val totalPrice = CartManager.getTotalPrice()
        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
        tvQRPrice = binding.tvQRPrice
        tvQRPrice.text = "Số tiền: ${formattedPrice}₫"
        
        //regex phone number + confirm giao hàng--------------------//
        btnConfirm = binding.btnConfirm
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
        
        //username auto place----------------------------------------//
        val database = FirebaseDatabase.getInstance("https://project-3-1ed87-default-rtdb.asia-southeast1.firebasedatabase.app")
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

        // Back button------------------------------------------------//
        btnBack = binding.btnBack
        btnBack.setOnClickListener{
            val intent = Intent(this, CartListActivity::class.java)
            startActivity(intent)
            finish()
        }
        //tạo QR code thanh toan------------------------------------------------//
        val qrUrl = "https://img.vietqr.io/image/970422-9566917032003-compact.png?amount=${totalPrice}&addInfo=thanh%20toan%20don%20hang%20Temu&accountName=NGUYEN%20ANH%20TUAN"
        Picasso.get().load(qrUrl).into(binding.imgVietQR)

        // Tạo Retrofit client------------------------------------------------//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.vietqr.io/v2/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        vietQRApi = retrofit.create(VietQRApi::class.java)
//        generateQRCode()
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

//    private fun generateQRCode() {
//        val totalPrice = CartManager.getTotalPrice()
//        val binding = ActivityDeliveryBinding.inflate(layoutInflater)
//        val request = VietQRRequest(
//            accountNo = "9566917032003",
//            accountName = "NGUYEN ANH TUAN",
//            acqId = "970422",
//            amount = totalPrice,
//            addInfo = "Thanh toan don hang Temu"
//        )
//
//        vietQRApi.generateQR(request).enqueue(object : Callback<VietQRResponse> {
//            override fun onResponse(call: Call<VietQRResponse>, response: Response<VietQRResponse>) {
//                if (response.isSuccessful) {
////                    val qrUrl = response.body()?.data?.qrDataURL
//                    val qrUrl = "https://img.vietqr.io/image/970422-9566917032003-compact2.png?amount=${totalPrice}&addInfo=thanh%20toan%20don%20hang%20Temu&accountName=NGUYEN%20ANH%20TUAN"
//                    if (qrUrl != null) {
//                        Picasso.get().load(qrUrl).into(binding.imgVietQR)
//                        val gson = Gson()
//                        val json = gson.toJson(response.body())
////                        Log.d("API_JSON", json)
//                        Log.d("DEBUG_QR", "QR URL: $qrUrl")
////                        Log.d("API_RESPONSE", response.body().toString())
//                    }
//                } else {
//                    Toast.makeText(this@DeliveryActivity, "Lỗi: ${response.code()}", Toast.LENGTH_SHORT).show()
//                    Log.d("DEBUG_QR", "Lỗi QR")
//                }
//            }
//
//            override fun onFailure(call: Call<VietQRResponse>, t: Throwable) {
//                Toast.makeText(this@DeliveryActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()
//                Log.d("DEBUG_QR", "Lỗi kết nối QR")
//            }
//        })
//    }
}

