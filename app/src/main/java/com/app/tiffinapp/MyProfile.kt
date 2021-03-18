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
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.MyProfilemodel
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_profile_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyProfile : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private var cd: ConnectionDetector? = null
    var mealplansListResponse: MyProfilemodel? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile_activity)
        context = this
        cd = ConnectionDetector(context)

        pDialog = ProgressDialog(context)
        pDialog.setMessage("Please Wait...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)
        btnAddDeliveryAddress.setOnClickListener(this)

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if (WMPreference.get_userType(context)!! == "O" || WMPreference.get_userType(context)!! == "D") {
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

        if (cd!!.isConnected) {
            subscriptionData()
        } else {
            Utility.showToastShort(context!!, "No Internet!!")
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
        val call = redditAPI.GetMyProfile(WMPreference.get_userId(context!!)!!)

        call.enqueue(object : Callback<MyProfilemodel> {
            override fun onResponse(
                call: Call<MyProfilemodel>,
                response: retrofit2.Response<MyProfilemodel>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    mealplansListResponse = response.body()
                    if (mealplansListResponse != null) {
                        if (mealplansListResponse!!.getStatus() == 1) {
                            fillData()
                        } else {
                            Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<MyProfilemodel>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    private fun fillData() {
        etFirstName.setText(mealplansListResponse!!.getData()!!.profileDetails!!.firstName.toString())
        etLastName.setText(mealplansListResponse!!.getData()!!.profileDetails!!.lastName.toString())
        etMobileNo.setText(mealplansListResponse!!.getData()!!.profileDetails!!.mobileNumber.toString())
        etEmail.setText(mealplansListResponse!!.getData()!!.profileDetails!!.emailAddress.toString())
        etAdd.setText(mealplansListResponse!!.getData()!!.profileDetails!!.addressLine1.toString())
        etAdd2.setText(mealplansListResponse!!.getData()!!.profileDetails!!.addressLine2.toString())
        etPostalCode.setText(mealplansListResponse!!.getData()!!.profileDetails!!.postCode.toString())
        etCity.setText(mealplansListResponse!!.getData()!!.profileDetails!!.city.toString())
        etState.setText(mealplansListResponse!!.getData()!!.profileDetails!!.state.toString())
        etCoins.setText(mealplansListResponse!!.getData()!!.profileDetails!!.myCoins.toString())
        //etPassword.setText(mealplansListResponse!!.getData()!!.profileDetails!!.firstName.toString())
    }

    override fun onClick(v: View?) {
        if (v == btnAddDeliveryAddress) {
            val i = Intent(baseContext, AddAddress::class.java)
            startActivity(i)
        } else if (v == btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
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