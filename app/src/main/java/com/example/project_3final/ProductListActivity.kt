package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val edtSearch : EditText = findViewById(R.id.etSearch)
        val imgBtn : TextView = findViewById(R.id.btnBack)
        val btnViewCart: ImageButton = findViewById(R.id.btnViewCart)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val productList = listOf(
            Product("Áo thun JTeeMan Unisex", 100000, R.drawable.ao_thun_jteeman_unisex),
            Product("Quần Jeans Light Blue QJ050 Màu Xanh", 490000, R.drawable.quan_jeans_light_blue_qj050_mau_xanh),
            Product("Giày Converse Chuck Taylor All Star 1970s cao đen trắng", 1600000, R.drawable.giay_converse_chuck_taylor_all_star_1970s_cao_den_trang),
            Product("Mũ hóa trang Teemo Liên minh huyền thoại", 200000, R.drawable.mu_hoa_trang_teemo_lien_minh_huyen_thoai),
            Product("PC NZXT ELITE LUXURY ALL BLACK I9 14900K RTX 4090 24GB ROG GAMING", 9000000, R.drawable.pc_i9_14900k_rtx_4090_24gb_rog_gaming),
            Product("NVIDIA GeForce RTX 5080", 40000000, R.drawable.card_nvidia_geforce_rtx_5080),
            Product("Chuột Gaming Logitech G102 Gen 2 LIGHTSYNC", 1000000, R.drawable.chuot_gaming_logitech_g102_gen_2_lightsync),
            Product("Bàn phím cơ Aula F75", 1200000, R.drawable.ban_phim_co_aula_f75),
            Product("Móc chìa khóa cỏ 4 lá", 12000, R.drawable.moc_chia_khoa_co_4_la),
            Product("Cáp nối dài USB 3.0 dài 2M âm dương Ugreen", 32000, R.drawable.cap_noi_dai_usb_3_0_dai_2m_am_duong_ugreen),
            Product("USB to HDMI Adapter", 50000, R.drawable.usb_to_hdmi_adapter),
            Product("Bộ Chia USB Hub ORICO PAPW4A-U3-015-BK-EP", 220000, R.drawable.bo_chia_usb_hub_orico_papw4a_u3_015_bk_ep)
        )

//        edtSearch.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {}
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val query = s.toString().lowercase().trim()
//                    val filtered = productList.filter {
//                    it.name.lowercase().contains(query)
//                }
//                ProductAdapter.updateList(filtered)
//            }
//        })

        imgBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnViewCart.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(productList)
    }
}