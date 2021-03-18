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
import com.app.tiffinapp.models.AddAddressResponse
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.add_address_activity.*
import kotlinx.android.synthetic.main.add_address_activity.drawer_layout
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_activity.*
import kotlinx.android.synthetic.main.user_profile_activity.btnSubmit
import kotlinx.android.synthetic.main.user_profile_activity.etAdd
import kotlinx.android.synthetic.main.user_profile_activity.etAdd2
import kotlinx.android.synthetic.main.user_profile_activity.etCity
import kotlinx.android.synthetic.main.user_profile_activity.etFirstName
import kotlinx.android.synthetic.main.user_profile_activity.etLastName
import kotlinx.android.synthetic.main.user_profile_activity.etMobileNo
import kotlinx.android.synthetic.main.user_profile_activity.etPostalCode
import kotlinx.android.synthetic.main.user_profile_activity.etState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddAddress : AppCompatActivity(), View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private var cd: ConnectionDetector? = null
    var addAddressResponse: AddAddressResponse? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_address_activity)
        context = this
        cd = ConnectionDetector(context)

        pDialog = ProgressDialog(context)
        pDialog.setMessage("Please Wait...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)


        btnSubmit.setOnClickListener(this)
        btnAddressList.setOnClickListener(this)
        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if (WMPreference.get_userType(context!!)!! == "O" || WMPreference.get_userType(context!!)!! == "D") {
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

    }


    override fun onClick(v: View?) {
        if (v == btnSubmit) {
            if (cd!!.isConnected) {
                addAddress()
            } else {
                Utility.showToastShort(context!!, "No Internet!!")
            }
        } else if (v == btnAddressList) {
            val i = Intent(baseContext, AddressList::class.java)
            startActivity(i)
        } else if (v === btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun addAddress() {
        if (etFirstName.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Firstname")
            return
        }
        if (etLastName.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Lastname")
            return
        }
        if (etAdd.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Address1")
            return
        }
        if (etAdd2.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Address2")
            return
        }
        if (etCity.text.isEmpty()) {
            Utility.showToastShort(context, "Enter City")
            return
        }
        if (etState.text.isEmpty()) {
            Utility.showToastShort(context, "Enter State")
            return
        }
        if (etLandmark.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Landmark")
            return
        }
        if (etMobileNo.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Mobile")
            return
        }

        if (etPostalCode.text.isEmpty()) {
            Utility.showToastShort(context, "Enter Postcode")
            return
        }


        pDialog.show()
        val BASE_URL = resources.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val redditAPI: ApiServices
        redditAPI = retrofit.create(ApiServices::class.java)
        val call = redditAPI.UserAddAddress(
            WMPreference.get_userId(context)!!,
            etFirstName.text.toString().trim(),
            etLastName.text.toString().trim(),
            etAdd.text.toString().trim(),
            etAdd2.text.toString().trim(),
            etCity.text.toString().trim(),
            etState.text.toString().trim(),
            etLandmark.text.toString().trim(),
            etMobileNo.text.toString().trim(),
            etMobileNoALternate.text.toString().trim(),
            etPostalCode.text.toString().trim(),
        )

        call.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(
                call: Call<AddAddressResponse>,
                response: retrofit2.Response<AddAddressResponse>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    addAddressResponse = response.body()
                    if (addAddressResponse != null) {
                        if (addAddressResponse!!.getStatus() == 1) {
                            Utility.showToastShort(context, addAddressResponse!!.getMessage()!!)
                            finish()
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, context!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}