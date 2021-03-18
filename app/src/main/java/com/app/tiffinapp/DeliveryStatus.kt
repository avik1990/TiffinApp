package com.app.tiffinapp

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.adapter.DeliveryStatusAdapter
import com.app.tiffinapp.models.AllotedDeliveryModel
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.delivery_boys.*
import kotlinx.android.synthetic.main.toolbar.*

class DeliveryStatus : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var mContext: Context? = null
    var users = ArrayList<AllotedDeliveryModel>()
    var navigationView: NavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivery_status)
        mContext = this

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if(WMPreference.get_userType(mContext!!)!! == "O" || WMPreference.get_userType(mContext!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

        users.add(AllotedDeliveryModel("1. Belal Khan"))
        users.add(AllotedDeliveryModel("2. Belal Khan"))
        users.add(AllotedDeliveryModel("3. Belal Khan"))
        users.add(AllotedDeliveryModel("4. Belal Khan"))
        users.add(AllotedDeliveryModel("5. Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))
        users.add(AllotedDeliveryModel("Belal Khan"))


        inflateAdapter()
    }

    private fun inflateAdapter() {
        val adapter = DeliveryStatusAdapter(users)
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

}