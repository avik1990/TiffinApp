package com.app.tiffinapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SubscriptionZoneList {
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
        @SerializedName("MealplansList")
        @Expose
        var mealplansList: List<MealplansList>? = null
    }


    class MealplansList {
        @SerializedName("MealPlansID")
        @Expose
        var mealPlansID: String? = null

        @SerializedName("MealPlansName")
        @Expose
        var mealPlansName: String? = null
    }
}