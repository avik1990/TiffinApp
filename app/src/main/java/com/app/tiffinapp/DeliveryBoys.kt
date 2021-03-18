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
import com.app.tiffinapp.adapter.DeliveryBoysAdapter
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.DeliveryBoysModel
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.delivery_boys.*
import kotlinx.android.synthetic.main.delivery_boys.rvUsers
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeliveryBoys : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var context: Context? = null
    var users = DeliveryBoysModel()
    lateinit var pDialog: ProgressDialog
    private var cd: ConnectionDetector? = null
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivery_boys)
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
        //inflateAdapter()
        subscriptionData()
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
        val call = redditAPI.GetDeliveryBoyListing(WMPreference.get_userId(context!!)!!)

        call.enqueue(object : Callback<DeliveryBoysModel> {
            override fun onResponse(
                call: Call<DeliveryBoysModel>,
                response: retrofit2.Response<DeliveryBoysModel>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    users = response.body()!!
                    if (users != null) {
                        if (users!!.getStatus() == 1) {
                            inflateAdapter()
                        } else {
                            Utility.showToastShort(context!!, users!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<DeliveryBoysModel>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    private fun inflateAdapter() {
        val adapter = DeliveryBoysAdapter(context!!,users.getData()!!.deliveryBoyList!!)
        rvUsers.adapter = adapter

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, context!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        if (v == btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

}