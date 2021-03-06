package com.app.tiffinapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AddOrderResponse {

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("Message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: String? = null

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

    fun getData(): String? {
        return data
    }

    fun setData(data: String?) {
        this.data = data
    }

}