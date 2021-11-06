package com.selfi.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.selfi.R
import com.selfi.adapter.JadwalByHariRecyclerAdapter
import com.selfi.adapter.JadwalHariRecyclerAdapter
import com.selfi.adapter.TargetProfileRecyclerAdapter
import com.selfi.models.*
import com.selfi.models.Target
import com.selfi.models.response.DataResponseModel
import com.selfi.models.response.ResponseLogin
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.JadwalService
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TargetService
import com.selfi.services.api.UserService
import com.selfi.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_jadwal.*
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


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
        exitButton()
        setCompletedTodo()
        setUncompletedTodo()
       // setTargetProfile()
        recyclerJadwalHariIni()
        recyclerTargetProfile()

    }

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
                        //  response.body()
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
        text_profile_name.text = user.nama
    }


    fun exitButton() {
        img_exit.setOnClickListener {
            var alert = AlertDialog.Builder(view!!.rootView.context)
            alert.setTitle("Logout")
            alert.setMessage("Apakah anda ingin Logout?")
            alert.setPositiveButton("Ya", { dialog: DialogInterface?, which: Int ->

                val pref = SharedPrefHelper(activity!!)
                pref.clearUser()
                startActivity(
                    Intent(activity!!, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )

            })
            alert.setNegativeButton("Tidak", { dialog: DialogInterface?, which: Int -> })
            alert.show()

        }
    }


    fun setUncompletedTodo() {
        val pref = SharedPrefHelper(activity!!)
        ServiceBuilder.buildService(UserService::class.java)
            .getTodoUncompleted(pref.getAccount().nis)
            .enqueue(object : Callback<DataResponseModel<List<TodoUncompleted>>> {
                override fun onFailure(
                    call: Call<DataResponseModel<List<TodoUncompleted>>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                    ).show()
                    Log.e("onFailure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DataResponseModel<List<TodoUncompleted>>>,
                    response: Response<DataResponseModel<List<TodoUncompleted>>>
                ) {
                    textCard_tugas_tertunda.text =
                        response.body()!!.data!!.get(0).totalKegiatanTertunda.toString()
                }

            })
    }


    fun setCompletedTodo() {
        val pref = SharedPrefHelper(activity!!)
        ServiceBuilder.buildService(UserService::class.java).getTodoCompleted(pref.getAccount().nis)
            .enqueue(object : Callback<DataResponseModel<List<TodoCompleted>>> {
                override fun onFailure(
                    call: Call<DataResponseModel<List<TodoCompleted>>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                    ).show()
                    Log.e("onFailure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DataResponseModel<List<TodoCompleted>>>,
                    response: Response<DataResponseModel<List<TodoCompleted>>>
                ) {
                    textCard_tugas_selesai.text =
                        response.body()!!.data!!.get(0).totalKegiatanSelesai.toString()
                }

            })
    }


    fun recyclerJadwalHariIni() {
        val pref = SharedPrefHelper(activity!!).getAccount().Idkelas
        ServiceBuilder.buildService(JadwalService::class.java)
            .getJadwalByHari(pref, getCurrentDay())
            .enqueue(object : Callback<DataResponseModel<List<JadwalMapelProfile>>> {
                override fun onFailure(
                    call: Call<DataResponseModel<List<JadwalMapelProfile>>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                    ).show()
                    Log.e("onFailure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DataResponseModel<List<JadwalMapelProfile>>>,
                    response: Response<DataResponseModel<List<JadwalMapelProfile>>>
                ) {
                    rv_jadwal_profile.setHasFixedSize(true)
                    rv_jadwal_profile.layoutManager = LinearLayoutManager(activity!!)
                    val adapter =
                        JadwalByHariRecyclerAdapter(response.body()!!.data!!, activity!!)
                    rv_jadwal_profile.adapter = adapter
                }
            })
    }

    fun recyclerTargetProfile(){
        val pref = SharedPrefHelper.getInstance(activity!!).getAccount().nis
        ServiceBuilder.buildService(UserService :: class.java).getTargetProfile(pref).enqueue(object : Callback<DataResponseModel<List<Target>>>{
            override fun onFailure(call: Call<DataResponseModel<List<Target>>>, t: Throwable) {
                Toast.makeText(
                    activity!!, "Error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("onFailure", t.message.toString())            }

            override fun onResponse(
                call: Call<DataResponseModel<List<Target>>>,
                response: Response<DataResponseModel<List<Target>>>
            ) {
                rv_target_profile.setHasFixedSize(true)
                rv_target_profile.layoutManager = LinearLayoutManager(activity!!)
                val adapter =
                    TargetProfileRecyclerAdapter(response.body()!!.data!!, activity!!)
                rv_target_profile.adapter = adapter
            }

        })
    }

    fun getCurrentDay(): String {
        var weekDay: String = ""

        val c: Calendar = Calendar.getInstance()
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)

        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "senin"
        }
        if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "selasa"
        }
        if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "rabu"
        }

        if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "kamis"
        }

        if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "jumat"
        }

        if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "sabtu"
        }

        if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "minggu"
        }

        return weekDay
    }
}
