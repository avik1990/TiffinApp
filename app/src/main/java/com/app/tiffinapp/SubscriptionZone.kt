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
import com.app.tiffinapp.adapter.SubscriptionAdapter
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.SubscriptionZoneList
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.delivery_boys.*
import kotlinx.android.synthetic.main.delivery_boys.drawer_layout
import kotlinx.android.synthetic.main.subscription_order.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SubscriptionZone : AppCompatActivity(), SubscriptionAdapter.onRowItemSelected,View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private var cd: ConnectionDetector? = null
    var mealplansListResponse: SubscriptionZoneList? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subscription_zone)
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

        if (cd!!.isConnected) {
            subscriptionData()
        } else {
            Utility.showToastShort(context!!, "No Internet!!")
        }

        if(WMPreference.get_userType(context!!)!! == "O" || WMPreference.get_userType(context!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }
    }

    private fun inflateAdapter() {
        if (mealplansListResponse!!.getData()?.mealplansList!!.isNotEmpty()) {
            val adapter =
                SubscriptionAdapter(mealplansListResponse!!.getData()?.mealplansList, this)
            rvUsers.adapter = adapter
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
        val call = redditAPI.UserSubscriptionList(WMPreference.get_userId(context!!)!!)

        call.enqueue(object : Callback<SubscriptionZoneList> {
            override fun onResponse(
                call: Call<SubscriptionZoneList>,
                response: retrofit2.Response<SubscriptionZoneList>
            ) {
                if (response.isSuccessful) {
                    mealplansListResponse = response.body()
                    if (mealplansListResponse != null) {
                        if (mealplansListResponse!!.getStatus() == 1) {
                            Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                            inflateAdapter()
                        } else {
                            Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<SubscriptionZoneList>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    override fun getPosition(id: String) {
        Utility.showToastShort(context, "" + id)
        val intent = Intent(this, SubscriptionOrder::class.java)
        intent.putExtra("menuId", id)
        intent.putExtra("mealPlanId", id)
        intent.putExtra("menuSlotId", id)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, context!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}