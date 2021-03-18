package com.app.tiffinapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.addresslist_activity.*
import kotlinx.android.synthetic.main.operator_activity.*
import kotlinx.android.synthetic.main.operator_activity.drawer_layout
import kotlinx.android.synthetic.main.toolbar.*

class DashboardActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    var mContext: Context? = null
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.operator_activity)
        mContext = this

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if(WMPreference.get_userType(mContext!!)!! == "O" || WMPreference.get_userType(mContext!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, mContext!!)
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