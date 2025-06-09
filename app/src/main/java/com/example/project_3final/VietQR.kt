package com.example.project_3final

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class VietQRRequest(
    val accountNo: String,
    val accountName: String,
    val acqId: String,
    val amount: Int?,
    val addInfo: String,
    val template: String = "compact2"
)

data class VietQRResponse(
    val code: String,
    val desc: String,
    val data: QRData
)

data class QRData(
    val qrDataURL: String,
    val qrString: String
)

interface VietQRApi {
    @POST("generate")
    fun generateQR(@Body body: VietQRRequest): Call<VietQRResponse>
}
