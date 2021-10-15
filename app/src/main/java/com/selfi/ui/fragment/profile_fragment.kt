package com.selfi.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.selfi.R
import com.selfi.models.User
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.UserService
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class profile_fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUser()
    }

    fun initUser(){
        val pref = SharedPrefHelper(activity!!)
        ServiceBuilder.buildService(UserService::class.java)
            .getUserById(pref.getAccount().nis/*,1913949*/).enqueue(
                object : Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
//                        pref.saveUser(
//                            response.body()!!
//                        )
                        response.body()
                        setText()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                        ).show()
                        Log.e("onFailure", t.message.toString())
                    }
                })
    }

    fun setText(){
        val user = SharedPrefHelper.getInstance(activity!!.applicationContext).getAccount()
        text_profile_name.text = user.nama
    }


}
