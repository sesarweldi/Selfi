package com.selfi.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.selfi.*
import com.selfi.ui.activity.*
import com.selfi.models.User
import com.selfi.models.response.ResponseLogin
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.UserService
import kotlinx.android.synthetic.main.fragment_home_fragment.*
import kotlinx.android.synthetic.main.fragment_home_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*


class home_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val cardBelajar:CardView = findViewById(R.id.card_main_belajar)
        pref_id = SharedPrefHelper(getActivity()!!.applicationContext).getAccount().nis



        view.card_main_belajar.setOnClickListener {
            startActivity(Intent(activity, BelajarActivity::class.java))
        }

        view.card_main_jadwal.setOnClickListener {
            startActivity(Intent(activity, JadwalActivity::class.java))
        }

        view.card_main_konsul.setOnClickListener {
            startActivity(Intent(activity, KonsulActivity::class.java))
        }

        view.card_main_motivasi.setOnClickListener {
            startActivity(Intent(activity, MotivasiActivity::class.java))
        }

        view.card_main_target.setOnClickListener {
            startActivity(Intent(activity, TargetActivity::class.java))
        }

        view.card_main_todo.setOnClickListener {
            startActivity(Intent(activity, TodolistActivity::class.java))
        }


        setCurrentDate()
        initUser()
    }


    //    private var nis:Int = 0
    private var pref_id: Int = 0

    fun initUser() {
        val pref = SharedPrefHelper(activity!!)
        ServiceBuilder.buildService(UserService::class.java)
            .getUserById(pref.getAccount().nis/*,1913949*/).enqueue(
                object : Callback<ResponseLogin> {
                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        pref.saveUser(
                            response.body()!!.user!!
                        )
                   //     response.body()
                        setText()
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Toast.makeText(
                            activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                        ).show()
                        Log.e("onFailure", t.message.toString())
                    }
                })
    }

    fun setText() {
        val user = SharedPrefHelper.getInstance(activity!!.applicationContext).getAccount()
        tv_name_home.text = "Hallo, " + user.nama + "!"
    }


    fun setCurrentDate() {
        val calendar: Calendar = Calendar.getInstance()
        val currentDate: String =
            DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime())

        tv_tanggal_home.setText(currentDate)
    }
}

