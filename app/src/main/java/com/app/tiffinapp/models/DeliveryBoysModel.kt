package com.app.tiffinapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DeliveryBoysModel {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("Message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: Data? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }

    class Data {
        @SerializedName("DeliveryBoyList")
        @Expose
        var deliveryBoyList: List<DeliveryBoyList>? = null
    }


    class DeliveryBoyList {
        @SerializedName("DeliveryboyID")
        @Expose
        var deliveryboyID: String? = null

        @SerializedName("ProfileImageURL")
        @Expose
        var profileImageURL: String? = null

        @SerializedName("FirstName")
        @Expose
        var firstName: String? = null

        @SerializedName("LastName")
        @Expose
        var lastName: String? = null

        @SerializedName("MobileNumber")
        @Expose
        var mobileNumber: String? = null

        @SerializedName("EmailAddress")
        @Expose
        var emailAddress: String? = null

        @SerializedName("IsAvailable")
        @Expose
        var isAvailable: String? = null
    }
}