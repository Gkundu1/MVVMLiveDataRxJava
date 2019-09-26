package com.example.mvvmexample.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DMSLoginResponse(

    @SerializedName("Data")
    @Expose
    val Data: List<DriverData>,
    @SerializedName("Status")
    @Expose
    var status: String? = null,
    @SerializedName("StatusCode")
    @Expose
    var statusCode: Int = 0,
    @SerializedName("StatusMessage")
    @Expose
    var statusMessage: String? = null,
    @SerializedName("TokenKey")
    @Expose
    var tokenKey: String? = null,
    @SerializedName("TokenIssuedOn")
    @Expose
    var tokenIssuedOn: String? = null,
    @SerializedName("TokenExpiresOn")
    @Expose
    var tokenExpiresOn: String? = null
)