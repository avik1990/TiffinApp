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
import com.app.tiffinapp.adapter.AddressListAdapter
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.AddressListModel
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.addresslist_activity.*
import kotlinx.android.synthetic.main.addresslist_activity.drawer_layout
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressList : AppCompatActivity(), AddressListAdapter.onRowItemSelected,
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private var cd: ConnectionDetector? = null
    var mealplansListResponse: AddressListModel? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addresslist_activity)
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

        if (WMPreference.get_userType(context!!)!! == "O" || WMPreference.get_userType(context!!)!! == "D") {
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

        if (cd!!.isConnected) {
            subscriptionData()
        } else {
            Utility.showToastShort(context!!, "No Internet!!")
        }
    }

    override fun onClick(v: View?) {
        if (v == btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun inflateAdapter() {
        if (mealplansListResponse!!.getData()?.addressList!!.isNotEmpty()) {
            val adapter =
                AddressListAdapter(mealplansListResponse!!.getData()?.addressList, this)
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
        val call = redditAPI.GetAddressList(WMPreference.get_userId(context!!)!!)

        call.enqueue(object : Callback<AddressListModel> {
            override fun onResponse(
                call: Call<AddressListModel>,
                response: retrofit2.Response<AddressListModel>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    mealplansListResponse = response.body()
                    if (mealplansListResponse != null) {
                        if (mealplansListResponse!!.getStatus() == 1) {
                            // Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                            inflateAdapter()
                        } else {
                            Utility.showToastShort(context, mealplansListResponse!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<AddressListModel>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    override fun getPosition(id: String) {
        //Utility.showToastShort(context, "" + id)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, context!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}