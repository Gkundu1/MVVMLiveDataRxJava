package com.example.mvvmexample.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DriverData(

    @SerializedName("CreatedBy")
    @Expose
    var CreatedBy: Any? = null,
    @SerializedName("DriverNo")
    @Expose
    var DriverNo: Any? = null,
    @SerializedName("IsActive")
    @Expose
    var IsActive: Boolean = false,
    @SerializedName("PICEmail")
    @Expose
    var PICEmail: Any? = null,
    @SerializedName("PICName")
    @Expose
    var PICName: Any? = null,
    @SerializedName("PICPhone")
    @Expose
    var PICPhone: String? = null,
    @SerializedName("PhoneNumber")
    @Expose
    var PhoneNumber: Any? = null,
    @SerializedName("ID")
    @Expose
    var iD: Int = 0,
    @SerializedName("UserName")
    @Expose
    var userName: String? = null,
    @SerializedName("Password")
    @Expose
    var password: Any? = null,
    @SerializedName("FirstName")
    @Expose
    var firstName: Any? = null,
    @SerializedName("LastName")
    @Expose
    var lastName: Any? = null,
    @SerializedName("Email")
    @Expose
    var email: Any? = null

)