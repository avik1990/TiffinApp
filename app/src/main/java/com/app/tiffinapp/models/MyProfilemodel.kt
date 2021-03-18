package com.app.tiffinapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MyProfilemodel {
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
        @SerializedName("ProfileDetails")
        @Expose
        var profileDetails: ProfileDetails? = null
    }

    class ProfileDetails {
        @SerializedName("UsersID")
        @Expose
        var usersID: String? = null

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

        @SerializedName("AddressLine1")
        @Expose
        var addressLine1: String? = null

        @SerializedName("AddressLine2")
        @Expose
        var addressLine2: String? = null

        @SerializedName("PostCode")
        @Expose
        var postCode: String? = null

        @SerializedName("City")
        @Expose
        var city: String? = null

        @SerializedName("State")
        @Expose
        var state: String? = null

        @SerializedName("MyCoins")
        @Expose
        var myCoins: String? = null

        @SerializedName("ProfileImage")
        @Expose
        var profileImage: String? = null
    }
}