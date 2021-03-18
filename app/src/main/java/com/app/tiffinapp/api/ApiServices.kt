package com.app.tiffinapp.api

import com.app.tiffinapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("userRegistration")
    fun UserRegistration(
        /*etFirstName.text.toString().trim(),
        etLastName.text.toString().trim(),
        etEmail.text.toString().trim(),
        etPhone.text.toString().trim(),
        etPassword.text.toString().trim()*/

        @Field("FirstName") FirstName: String,
        @Field("LastName") LastName: String,
        @Field("EmailAddress") EmailAddress: String,
        @Field("MobileNumber") MobileNumber: String,
        @Field("UsersPassword") UsersPassword: String
    ): Call<RegistrationResponse>

    @FormUrlEncoded
    @POST("checkLogin")
    fun UserLogin(
        @Field("MobileNumber") FirstName: String,
        @Field("UsersPassword") LastName: String
    ): Call<RegistrationResponse>


    @GET("getSubscriptionZoneList")
    fun UserSubscriptionList(
        @Query("UsersID") UsersID: String,
    ): Call<SubscriptionZoneList>

    @GET("getDeliveryAddress")
    fun GetAddressList(
        @Query("UsersID") UsersID: String,
    ): Call<AddressListModel>


    @GET("getBuyOnceZoneList")
    fun UsergetBuyOnceZoneList(
        @Query("UsersID") UsersID: String,
        @Query("DaysID") DaysID: String,
    ): Call<BuyOnceZoneList>


    @GET("getMyProfile")
    fun GetMyProfile(
        @Query("UsersID") UsersID: String,
    ): Call<MyProfilemodel>

    @GET("getDeliveryBoyListing")
    fun GetDeliveryBoyListing(
        @Query("OperatorID") OperatorID: String,
    ): Call<DeliveryBoysModel>

    @FormUrlEncoded
    @POST("addDeliveryAddress")
    fun UserAddAddress(
        @Query("UsersID") UsersID: String,
        @Field("FirstName") FirstName: String,
        @Field("LastName") LastName: String,
        @Field("AddressLine1") AddressLine1: String,
        @Field("AddressLine2") AddressLine2: String,
        @Field("City") City: String,
        @Field("State") State: String,
        @Field("LandMark") LandMark: String,
        @Field("MobileNumber") MobileNumber: String,
        @Field("AlternateMobileNumber") AlternateMobileNumber: String,
        @Field("PostCode") PostCode: String,
    ): Call<AddAddressResponse>


    @FormUrlEncoded
    @POST("addOrder")
    fun AddOrder(
        @Query("UsersID") UsersID: String,
        @Field("DaysID") DaysID: String,
        @Field("MealPlansID") MealPlansID: String,
        @Field("MealSlotID") MealSlotID: String,
        @Field("AddressID") AddressID: String,
        @Field("PaymentMethods") PaymentMethods: String,
    ): Call<AddOrderResponse>

    @GET("getDeliveryStatusListing")
    fun GetDeliveryStatusListing(
        @Query("OperatorID") OperatorID: String,
    ): Call<DeliveryStatusListingResponse>


}

/*
FirstName:Sanjoy
LastName: Majumder
:AD-3
: 128A
:Kolkata
:West Bengal
:CAP Camp
:1234567890
:1234567891
:700001
*/


