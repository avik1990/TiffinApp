package com.app.tiffinapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.adapter.BuyOnceZoneRecyclerAdapter
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.BuyOnceZoneList
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.addresslist_activity.*
import kotlinx.android.synthetic.main.buyonce_zone.*
import kotlinx.android.synthetic.main.buyonce_zone.drawer_layout
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuyOnceZone : AppCompatActivity(), BuyOnceZoneRecyclerAdapter.onRowItemSelected,NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private var cd: ConnectionDetector? = null
    var buyOnceZoneListResponse: BuyOnceZoneList? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyonce_zone)
        context = this
        cd = ConnectionDetector(context)

        pDialog = ProgressDialog(context)
        pDialog.setMessage("Please Wait...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if(WMPreference.get_userType(context!!)!! == "O" || WMPreference.get_userType(context!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

        if (cd!!.isConnected) {
            subscriptionData()
        } else {
            Utility.showToastShort(context!!, "No Internet!!")
        }
    }


    private fun inflateAdapter() {
        if (buyOnceZoneListResponse!!.getData()?.mealplansList!!.isNotEmpty()) {
            val adapter = BuyOnceZoneRecyclerAdapter(
                context,
                buyOnceZoneListResponse!!.getData()!!.mealplansList!!,
                this
            )
            rvList!!.setAdapter(adapter)
        } else {
            Utility.showToastShort(context, "No data found!!")
        }
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
        val call = redditAPI.UsergetBuyOnceZoneList(WMPreference.get_userId(context)!!, "1")

        call.enqueue(object : Callback<BuyOnceZoneList> {
            override fun onResponse(
                call: Call<BuyOnceZoneList>,
                response: retrofit2.Response<BuyOnceZoneList>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    buyOnceZoneListResponse = response.body()
                    if (buyOnceZoneListResponse != null) {
                        if (buyOnceZoneListResponse!!.getStatus() == 1) {
                            inflateAdapter()
                        } else {
                            Utility.showToastShort(
                                context,
                                buyOnceZoneListResponse!!.getMessage()!!
                            )
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<BuyOnceZoneList>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    override fun getPosition(menuId: String, mealPlanId: String, menuSlotId: String) {
        val intent = Intent(this, SubscriptionOrder::class.java)
        intent.putExtra("menuId", menuId)
        intent.putExtra("mealPlanId", mealPlanId)
        intent.putExtra("menuSlotId", menuSlotId)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, context!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        if (v === btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

}