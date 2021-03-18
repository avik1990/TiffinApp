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
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.addresslist_activity.*
import kotlinx.android.synthetic.main.operator_activity.*
import kotlinx.android.synthetic.main.operator_activity.drawer_layout
import kotlinx.android.synthetic.main.toolbar.*

class OperatorActivity : AppCompatActivity(),
    View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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

        initViews()
    }

    private fun initViews() {
        btnDeliveryboyList.setOnClickListener(this)
        btnOrderList.setOnClickListener(this)
        btnDeliveryStatus.setOnClickListener(this)
        btnShowRefundClaims.setOnClickListener(this)
        iv_profile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnDeliveryboyList -> {
                val i = Intent(baseContext, DeliveryBoys::class.java)
                startActivity(i)
            }
            btnOrderList -> {
                val i = Intent(baseContext, AllotedActivity::class.java)
                startActivity(i)
            }
            btnDeliveryStatus -> {
                val i = Intent(baseContext, DeliveryStatus::class.java)
                startActivity(i)
            }
            btnShowRefundClaims -> {
                val i = Intent(baseContext, UserActivity::class.java)
                startActivity(i)
            }
            iv_profile -> {
                val i = Intent(baseContext, MyProfile::class.java)
                startActivity(i)
            }
            btn_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
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