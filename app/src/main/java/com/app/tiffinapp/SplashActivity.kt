package com.app.tiffinapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.tiffinapp.utils.WMPreference

class SplashActivity : AppCompatActivity() {
    var is_verfied = false
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        mContext = this

        is_verfied = WMPreference.getisVerified(mContext!!)

        if (is_verfied) {
            goToHomeActivity()
        } else {
            goToLoginActivity()
        }
    }


    private fun goToLoginActivity() {
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3 * 1000.toLong())
                    val i = Intent(baseContext, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                } catch (e: Exception) {
                }
            }
        }
        background.start()
    }

    private fun goToHomeActivity() {
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3 * 1000.toLong())
                    redirect()
                } catch (e: Exception) {
                }
            }
        }
        background.start()
    }

    private fun redirect() {
        if (WMPreference.get_userType(mContext!!)!!.equals("U")) {
            val i = Intent(baseContext, UserActivity::class.java)
            startActivity(i)
            finish()
        } else if (WMPreference.get_userType(mContext!!)!!.equals("O")) {
            val i = Intent(baseContext, OperatorActivity::class.java)
            startActivity(i)
            finish()
        }else if (WMPreference.get_userType(mContext!!)!!.equals("D")) {
            val i = Intent(baseContext, AllotedActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}