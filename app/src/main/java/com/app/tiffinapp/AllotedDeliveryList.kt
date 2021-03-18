package com.app.tiffinapp

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.adapter.AllotedDeliveryAdapter
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.AddressListModel
import com.app.tiffinapp.models.AllotedDeliveryModel
import com.app.tiffinapp.models.DeliveryStatusListingResponse
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.alloted_listactivity.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllotedDeliveryList : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    var mContext: Context? = null
    var navigationView: NavigationView? = null
    private var cd: ConnectionDetector? = null
    lateinit var pDialog: ProgressDialog
    var mealplansListResponse: DeliveryStatusListingResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alloted_listactivity)
        mContext = this
        cd = ConnectionDetector(mContext)

        pDialog = ProgressDialog(mContext)
        pDialog.setMessage("Please Wait...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if (WMPreference.get_userType(mContext!!)!! == "O" || WMPreference.get_userType(mContext!!)!! == "D") {
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }



        if (cd!!.isConnected) {
            subscriptionData()
        } else {
            Utility.showToastShort(mContext!!, "No Internet!!")
        }
    }

    private fun inflateAdapter() {
        val adapter = AllotedDeliveryAdapter(mContext!!, mealplansListResponse!!.getData()?.deliveryStatusList!!)
        //now adding the adapter to recyclerview
        rvUsers.adapter = adapter

    }

    override fun onClick(v: View?) {
        if (v == btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, mContext!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun subscriptionData() {
        pDialog.show()
        val BASE_URL = resources.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val redditAPI: ApiServices
        redditAPI = retrofit.create(ApiServices::class.java)
        val call = redditAPI.GetDeliveryStatusListing(WMPreference.get_userId(mContext!!)!!)

        call.enqueue(object : Callback<DeliveryStatusListingResponse> {
            override fun onResponse(
                call: Call<DeliveryStatusListingResponse>,
                response: retrofit2.Response<DeliveryStatusListingResponse>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    mealplansListResponse = response.body()
                    if (mealplansListResponse != null) {
                        if (mealplansListResponse!!.getStatus() == 1) {
                            // Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                            if (mealplansListResponse!!.getData()?.deliveryStatusList!!.isNotEmpty()) {
                                inflateAdapter()
                            }
                        } else {
                            Utility.showToastShort(
                                mContext!!,
                                mealplansListResponse!!.getMessage()!!
                            )
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<DeliveryStatusListingResponse>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

}