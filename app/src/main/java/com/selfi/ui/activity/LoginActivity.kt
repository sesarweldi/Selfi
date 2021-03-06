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


            ServiceBuilder.buildService(UserService::class.java, this@LoginActivity).login(nis, password)
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

                        if(response.isSuccessful){
                            val pref= SharedPrefHelper.getInstance(applicationContext)
                            pref.saveUser(response.body()!!.user!!)
                            pref.saveAuthToken(response.body()!!.token!!)
                            Log.d("tes token", "token : ${response.body()!!.token}" )


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
                        } else if(response.code() == 400){

                            Toast.makeText(
                                this@LoginActivity,"Password Anda Salah!" ,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else{
                            Toast.makeText(
                                this@LoginActivity,"Nis Salah atau Anda Belum Terdaftar",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }
    }


}


