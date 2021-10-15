package com.selfi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.selfi.R
import com.selfi.models.User
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseLogin
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initAction()
    }


    fun initAction() {
        btn_login.setOnClickListener {
            val nis = edt_nisLogin.text.toString().trim()
            val password = edt_passwordLogin.text.toString().trim()

            if (nis.isEmpty()) {
                edt_nisLogin.error = "NIS harus diisi!"
                edt_nisLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edt_passwordLogin.error = "Password harus diisi!"
                edt_passwordLogin.requestFocus()
                return@setOnClickListener
            }


            ServiceBuilder.buildService(UserService::class.java).login(nis, password)
                .enqueue(object : Callback<ResponseLogin> {
                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Error : ${t.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Log.e("onFailure", t.message!!)
                    }

                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
//                        Toast.makeText(
//                            this@LoginActivity,
//                            response.body()!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()

                        if (response.isSuccessful) {
                            if (response.body()!!.success) {
                                val pref = SharedPrefHelper.getInstance(applicationContext)
                                pref.saveUser(response.body()!!.user!!)

                                Log.d("selfi", "id : ${response.body()!!.user!!.nis}")

                                Toast.makeText(
                                    this@LoginActivity,
                                    response.body()!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()


                                startActivity(
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                )
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }


}


