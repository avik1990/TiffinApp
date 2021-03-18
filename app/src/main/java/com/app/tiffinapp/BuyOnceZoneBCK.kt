//package com.app.tiffinapp
//
//import android.app.ProgressDialog
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.app.tiffinapp.adapter.BuyOnceZoneAdapter
//import com.app.tiffinapp.adapter.SubscriptionAdapter
//import com.app.tiffinapp.api.ApiServices
//import com.app.tiffinapp.models.BuyOnceZoneList
//import com.app.tiffinapp.utils.ConnectionDetector
//import com.app.tiffinapp.utils.Utility
//import com.app.tiffinapp.utils.WMPreference
//import kotlinx.android.synthetic.main.buyonce_zone.*
//import kotlinx.android.synthetic.main.delivery_boys.*
//import kotlinx.android.synthetic.main.delivery_boys.rvUsers
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class BuyOnceZoneBCK : AppCompatActivity(), BuyOnceZoneAdapter.onRowItemSelected {
//
//    private var cd: ConnectionDetector? = null
//    var buyOnceZoneListResponse: BuyOnceZoneList? = null
//    lateinit var context: Context
//    lateinit var pDialog: ProgressDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.buyonce_zone)
//        context = this
//        cd = ConnectionDetector(context)
//
//        pDialog = ProgressDialog(context)
//        pDialog.setMessage("Please Wait...")
//        pDialog.setCanceledOnTouchOutside(false)
//        pDialog.setCancelable(false)
//
//        if (cd!!.isConnected) {
//            subscriptionData()
//        } else {
//            Utility.showToastShort(context!!, "No Internet!!")
//        }
//    }
//
//    private fun inflateAdapter() {
//        if (buyOnceZoneListResponse!!.getData()?.mealplansList!!.isNotEmpty()) {
//            val adapter = BuyOnceZoneAdapter(context,buyOnceZoneListResponse!!.getData()?.mealplansList!!, this)
//            expandableListView!!.setAdapter(adapter)
//        } else {
//            Utility.showToastShort(context, "No data found!!")
//        }
//    }
//
//    private fun subscriptionData() {
//        pDialog.show()
//        val BASE_URL = resources.getString(R.string.base_url)
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val redditAPI: ApiServices
//        redditAPI = retrofit.create(ApiServices::class.java)
//        val call = redditAPI.UsergetBuyOnceZoneList(WMPreference.get_userId(context)!!, "1")
//
//        call.enqueue(object : Callback<BuyOnceZoneList> {
//            override fun onResponse(
//                call: Call<BuyOnceZoneList>,
//                response: retrofit2.Response<BuyOnceZoneList>
//            ) {
//                Log.d("String", "" + response)
//                if (response.isSuccessful) {
//                    buyOnceZoneListResponse = response.body()
//                    if (buyOnceZoneListResponse != null) {
//                        if (buyOnceZoneListResponse!!.getStatus() == 1) {
//                            Utility.showToastShort(context, buyOnceZoneListResponse!!.getMessage()!!)
//                            inflateAdapter()
//                        } else {
//                            Utility.showToastShort(context, buyOnceZoneListResponse!!.getMessage()!!)
//                        }
//                    }
//                }
//                pDialog.dismiss()
//            }
//
//            override fun onFailure(call: Call<BuyOnceZoneList>, t: Throwable) {
//                pDialog.dismiss()
//            }
//        })
//    }
//
//    override fun getPosition(id: String) {
//        Utility.showToastShort(context, "" + id)
//    }
//
//}