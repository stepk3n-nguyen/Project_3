package com.example.project_3final

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProductListActivity : AppCompatActivity() {
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: List<Product>
    private lateinit var filteredList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val edtSearch : EditText = findViewById(R.id.etSearch)
        val btnViewCart: ImageButton = findViewById(R.id.btnViewCart)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val btnProfile: ImageButton = findViewById(R.id.imgProfile)
        productList = listOf(
            Product(name="Dây mạng cat5e UTP Lention Lan-5E05 dài 5M", price = 270000, imageName = "day_mang_cat5e_utp_lention_lan_5e05_dai_5m"),
            Product(name="USB to 3.5mm Jack Audio Adapter", price=90000, imageName = "usb_to_jack_3_5mm"),
            Product(name="Đế tản nhiệt Laptop COOLER MASTER", price=400000, imageName = "de_tan_nhiet_laptop_cooler_master"),
            Product(name="FAN Tản Nhiệt Khí CPU SSTC - AC3H92 LED RGB ( LGA1700)", price=500000, imageName = "fan_tan_nhiet_khi_cpu_sstc_ac3h92_led_rgb_lga1700"),
            Product(name="PC NZXT ELITE LUXURY ALL BLACK I9 14900K RTX 4090 24GB ROG GAMING", price=8999000, imageName = "pc_i9_14900k_rtx_4090_24gb_rog_gaming"),
            Product(name="Card đồ họa NVIDIA GeForce RTX 5080", price=40000000, imageName = "card_nvidia_geforce_rtx_5080"),
            Product(name="Chuột Gaming Logitech G102 Gen 2 LIGHTSYNC", price=1000000, imageName = "chuot_gaming_logitech_g102_gen_2_lightsync"),
            Product(name="Bàn phím cơ Aula F75", price=1199000, imageName = "ban_phim_co_aula_f75"),
            Product(name="Kê tay HyperWork Hybrid gỗ óc chó kết hợp nhôm CNC 36 cm (HPW-WR01-ALW-36L-BRW)", price=1200, imageName = "ke_tay_hyperwork_hybrid_go_oc_cho_ket_hop_nhom_cnc_36_cm_hpw_wr01_alw_36l_brw_2_"),
            Product(name="Cáp nối dài USB 3.0 dài 2M âm dương Ugreen", price=32000, imageName = "cap_noi_dai_usb_3_0_dai_2m_am_duong_ugreen"),
            Product(name="Pin cúc áo CR1632", price=5000, imageName = "pin_cuc_ao_cr1632"),
            Product(name="USB to HDMI Adapter", price=50000, imageName = "usb_to_hdmi_adapter"),
            Product(name="Bộ Chia USB Hub ORICO PAPW4A-U3-015-BK-EP", price=220000, imageName = "bo_chia_usb_hub_orico_papw4a_u3_015_bk_ep"),
            Product(name="Màn hình BenQ ZOWIE XL2586X (24 inch/FHD/FAST TN/540Hz/DyAc™ 2)", price=27990000, imageName = "man_hinh_benq_zowie_xl2586x_24inch_fhd_fast_tn_540hz_dyac_2"),
            Product(name="Giá đỡ màn hình HyperWork Alpha Pro HPW-GMA02-BLK Đen", price=1099000, imageName = "gia_do_man_hinh_hyperwork_alpha_pro_hpw_gma02_blk_den_2"),
            Product(name="Bàn di chuột SteelSeries QcK Edge Medium 63822 (270 x 320 x 2mm)", price=389000, imageName = "ban_di_chuot_qck_edge_medium_63822_0000_2"),
            Product(name="Nguồn máy tính GIGABYTE P750BS 750W (80 Plus Bronze/Màu Đen)", price=1499000, imageName = "nguon_may_tinh_gigabyte_p750bs_750w_80_plus_bronze_mau_den_2"),
            Product(name="Ổ cứng HDD WD 1TB Blue 3.5 inch, 7200RPM, SATA, 64MB Cache (WD10EZEX)", price=1399000, imageName = "o_cung_hdd_western_caviar_blue_0000_layer_1")
        )

        fetchProductsFromFirestore()

        btnProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnViewCart.setOnClickListener {
            startActivity(Intent(this, CartListActivity::class.java))
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProductAdapter(productList)

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
        //----------------------------UPLOAD PRODUCT DATA TO FIRESTORE---------------------------------//
//        uploadProductsToFirestore(productList)
        //----------------------------UPLOAD PRODUCT DATA TO FIRESTORE---------------------------------//
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

//----------------------------UPLOAD PRODUCT DATA TO FIRESTORE---------------------------------//
//    private fun uploadProductsToFirestore(products: List<Product>) {
//        val db = FirebaseFirestore.getInstance()
//        val collection = db.collection("products")
//        for (product in products) {
//            val data = hashMapOf(
//                "name" to product.name,
//                "price" to product.price,
//                "imageName" to product.imageName
//            )
//            collection.add(data)
//        }
//    }
//----------------------------UPLOAD PRODUCT DATA TO FIRESTORE---------------------------------//

    // load ds sp tu Firestore vaf map imageName sang anh trong drawable
    private fun fetchProductsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("products").get().addOnSuccessListener { result ->
            val products = mutableListOf<Product>()
            for (document in result) {
                val name = document.getString("name") ?: ""
                val price = document.getLong("price")?.toInt() ?: 0
                val imageName = document.getString("imageName") ?: ""
                val product = Product(
                    id = document.id,
                    name = name,
                    price = price,
                    imageName = imageName
                )
                products.add(product)
            }

            // Map imageName thành R.drawable.id
            productList = products.map {
                it.copy(imageResId = getDrawableIdByName(it.imageName))
            }

            filteredList = productList.toMutableList()
            adapter = ProductAdapter(filteredList)
            findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        }
    }

    //lay resource id
    private fun getDrawableIdByName(name: String): Int {
        return resources.getIdentifier(name, "drawable", packageName)
    }
}