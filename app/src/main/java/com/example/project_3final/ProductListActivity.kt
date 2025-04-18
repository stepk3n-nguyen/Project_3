package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListActivity : AppCompatActivity() {
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: List<Product>
    private lateinit var filteredList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val edtSearch : EditText = findViewById(R.id.etSearch)
        val imgBtn : TextView = findViewById(R.id.btnBack)
        val btnViewCart: ImageButton = findViewById(R.id.btnViewCart)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        productList = listOf(
            Product("Dây mạng cat5e UTP Lention Lan-5E05 dài 5M", 270000, R.drawable.day_mang_cat5e_utp_lention_lan_5e05_dai_5m),
            Product("USB to 3.5mm Jack Audio Adapter", 90000, R.drawable.usb_to_jack_3_5mm),
            Product("Đế tản nhiệt Laptop COOLER MASTER", 400000, R.drawable.de_tan_nhiet_laptop_cooler_master),
            Product("FAN Tản Nhiệt Khí CPU SSTC - AC3H92 LED RGB ( LGA1700)", 500000, R.drawable.fan_tan_nhiet_khi_cpu_sstc_ac3h92_led_rgb_lga1700),
            Product("PC NZXT ELITE LUXURY ALL BLACK I9 14900K RTX 4090 24GB ROG GAMING", 8999000, R.drawable.pc_i9_14900k_rtx_4090_24gb_rog_gaming),
            Product("Card đồ họa NVIDIA GeForce RTX 5080", 40000000, R.drawable.card_nvidia_geforce_rtx_5080),
            Product("Chuột Gaming Logitech G102 Gen 2 LIGHTSYNC", 1000000, R.drawable.chuot_gaming_logitech_g102_gen_2_lightsync),
            Product("Bàn phím cơ Aula F75", 1199000, R.drawable.ban_phim_co_aula_f75),
            Product("Kê tay HyperWork Hybrid gỗ óc chó kết hợp nhôm CNC 36 cm (HPW-WR01-ALW-36L-BRW)", 12000, R.drawable.ke_tay_hyperwork_hybrid_go_oc_cho_ket_hop_nhom_cnc_36_cm_hpw_wr01_alw_36l_brw_2_),
            Product("Cáp nối dài USB 3.0 dài 2M âm dương Ugreen", 32000, R.drawable.cap_noi_dai_usb_3_0_dai_2m_am_duong_ugreen),
            Product("USB to HDMI Adapter", 50000, R.drawable.usb_to_hdmi_adapter),
            Product("Bộ Chia USB Hub ORICO PAPW4A-U3-015-BK-EP", 220000, R.drawable.bo_chia_usb_hub_orico_papw4a_u3_015_bk_ep),
            Product("Màn hình BenQ ZOWIE XL2586X (24 inch/FHD/FAST TN/540Hz/DyAc™ 2)", 27990000, R.drawable.man_hinh_benq_zowie_xl2586x_24inch_fhd_fast_tn_540hz_dyac_2),
            Product("Giá đỡ màn hình HyperWork Alpha Pro HPW-GMA02-BLK Đen", 1099000, R.drawable.gia_do_man_hinh_hyperwork_alpha_pro_hpw_gma02_blk_den_2),
            Product("Bàn di chuột SteelSeries QcK Edge Medium 63822 (270 x 320 x 2mm)", 389000, R.drawable.ban_di_chuot_qck_edge_medium_63822_0000_2),
            Product("Nguồn máy tính GIGABYTE P750BS 750W (80 Plus Bronze/Màu Đen)", 1499000, R.drawable.nguon_may_tinh_gigabyte_p750bs_750w_80_plus_bronze_mau_den_2),
            Product("Ổ cứng HDD WD 1TB Blue 3.5 inch, 7200RPM, SATA, 64MB Cache (WD10EZEX)", 1399000, R.drawable.o_cung_hdd_western_caviar_blue_0000_layer_1)
        )

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

        //sreach
        filteredList = productList.toMutableList()
        adapter = ProductAdapter(filteredList)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(text.toString())
            }
        })
    }
    private fun filterProducts(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(productList)
        } else {
            val lowerQuery = query.lowercase()
            filteredList.addAll(productList.filter {
                it.name.lowercase().contains(lowerQuery)
            })
        }
        adapter.notifyDataSetChanged()
    }
}