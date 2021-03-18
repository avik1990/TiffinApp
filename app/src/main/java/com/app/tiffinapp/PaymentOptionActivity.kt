package com.app.tiffinapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.AddOrderResponse
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.confirm_payment.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.Query

class PaymentOptionActivity : AppCompatActivity(), View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    var mmContext: Context? = null
    var menuId: String = ""
    var mealPlanId: String = ""
    var menuSlotId: String = ""
    lateinit var pDialog: ProgressDialog
    private var cd: ConnectionDetector? = null
    var regresponse: AddOrderResponse? = null
    var navigationView: NavigationView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_payment)
        mmContext = this

        cd = ConnectionDetector(mmContext)
        pDialog = ProgressDialog(mmContext)
        pDialog.setMessage("Processing...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)

        menuId = intent.getStringExtra("menuId")!!
        mealPlanId = intent.getStringExtra("mealPlanId")!!
        menuSlotId = intent.getStringExtra("menuSlotId")!!

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null
        btn_menu!!.setOnClickListener(this)
        if(WMPreference.get_userType(mmContext!!)!! == "O" || WMPreference.get_userType(mmContext!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }
        btnCoins.setOnClickListener {
            if (cd!!.isConnected) {
                UserOrder()
            }
        }

        btnGateway.setOnClickListener {

        }
    }

    private fun UserOrder() {
        pDialog.show()
        val BASE_URL = resources.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val redditAPI: ApiServices
        redditAPI = retrofit.create(ApiServices::class.java)
        val call = redditAPI.AddOrder(
            WMPreference.get_userId(mmContext!!)!!,
            "1", mealPlanId, menuSlotId, "7", "COIN"

        )


        call.enqueue(object : Callback<AddOrderResponse> {
            override fun onResponse(
                call: Call<AddOrderResponse>,
                response: retrofit2.Response<AddOrderResponse>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    regresponse = response.body()
                    if (regresponse != null) {
                        if (regresponse!!.getStatus() == 1) {
                            Utility.showToastShort(mmContext!!, regresponse!!.getMessage()!!)
                            val i = Intent(mmContext, OrderConfirmationActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Utility.showToastShort(mmContext!!, regresponse!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<AddOrderResponse>, t: Throwable) {
                pDialog.dismiss()
            }
        })
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
        Utility.openNavDrawer(id, mmContext!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}