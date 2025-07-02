package com.example.project_3final

import retrofit2.http.GET
import retrofit2.http.Path

data class Province(val code: String, val name: String)
data class District(val code: String, val name: String)
data class Ward(val code: String, val name: String)

data class DistrictWithWards(
    val code: String,
    val name: String,
    val wards: List<Ward>
)

data class ProvinceWithDistricts(
    val code: String,
    val name: String,
    val districts: List<DistrictWithWards>
)

interface VnApi {
    @GET("p/")
    suspend fun getProvinces(): List<Province>

    @GET("p/{code}?depth=2")
    suspend fun getProvinceDetails(@Path("code") provinceCode: String): ProvinceWithDistricts
}