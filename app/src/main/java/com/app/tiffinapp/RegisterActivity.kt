package com.app.tiffinapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.tiffinapp.api.ApiServices
import com.app.tiffinapp.models.RegistrationResponse
import com.app.tiffinapp.utils.ConnectionDetector
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import kotlinx.android.synthetic.main.register_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private var cd: ConnectionDetector? = null
    var regresponse: RegistrationResponse? = null
    lateinit var context: Context
    lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        context = this
        cd = ConnectionDetector(context)
        pDialog = ProgressDialog(context)
        pDialog.setMessage("Verifying...")
        pDialog.setCanceledOnTouchOutside(false)
        pDialog.setCancelable(false)

        btnSubmit.setOnClickListener(this)
        llLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == btnSubmit) {
            if (cd!!.isConnected) {
                if (etFirstName.text.toString().trim().isEmpty()) {
                    Utility.showToastShort(context, "Please enter Firstname")
                    return
                }
                if (etLastName.text.toString().trim().isEmpty()) {
                    Utility.showToastShort(context, "Please enter Lastname")
                    return
                }
                if (etEmail.text.toString().trim().isEmpty()) {
                    Utility.showToastShort(context, "Please enter Email")
                    return
                }
                if (etPhone.text.toString().trim().isEmpty()) {
                    Utility.showToastShort(context, "Please enter Phone")
                    return
                }
                if (etPassword.text.toString().trim().isEmpty()) {
                    Utility.showToastShort(context, "Please enter Password")
                    return
                }

                UserRegistarion()
            }
        } else if (v == llLogin) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun UserRegistarion() {
        pDialog.show()
        val BASE_URL = resources.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val redditAPI: ApiServices
        redditAPI = retrofit.create(ApiServices::class.java)
        val call = redditAPI.UserRegistration(
            etFirstName.text.toString().trim(),
            etLastName.text.toString().trim(),
            etEmail.text.toString().trim(),
            etPhone.text.toString().trim(),
            etPassword.text.toString().trim()
        )

        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: retrofit2.Response<RegistrationResponse>
            ) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    regresponse = response.body()
                    if (regresponse != null) {
                        if (regresponse!!.getStatus() == 1) {
                            Utility.showToastShort(context, regresponse!!.getData()!!.message!!)
                            WMPreference.set_firstuserName(
                                context,
                                regresponse!!.getData()!!.firstName!!
                            )
                            WMPreference.set_lastName(context, regresponse!!.getData()!!.lastName!!)
                            WMPreference.set_userEmail(
                                context,
                                regresponse!!.getData()!!.emailAddress!!
                            )
                            WMPreference.set_userPhone(
                                context,
                                regresponse!!.getData()!!.mobileNumber!!
                            )
                            WMPreference.set_userId(context, regresponse!!.getData()!!.usersID!!)
                            WMPreference.set_userType(
                                context,
                                regresponse!!.getData()!!.usersType!!
                            )

                            WMPreference.setisVerified(context, true)

                            redirect()
                        } else {
                            Utility.showToastShort(context, regresponse!!.getMessage()!!)
                        }
                    }
                }
                pDialog.dismiss()
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                pDialog.dismiss()
            }
        })
    }

    private fun redirect() {
        when {
            WMPreference.get_userType(context!!)!!.equals("U") -> {
                val i = Intent(baseContext, UserActivity::class.java)
                startActivity(i)
                finish()
            }
            WMPreference.get_userType(context!!)!!.equals("O") -> {
                val i = Intent(baseContext, OperatorActivity::class.java)
                startActivity(i)
                finish()
            }
            WMPreference.get_userType(context!!)!!.equals("D") -> {
                val i = Intent(baseContext, AllotedActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

}