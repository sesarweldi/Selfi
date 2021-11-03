package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.JadwalHariRecyclerAdapter
import com.selfi.models.JadwalHari
import com.selfi.models.response.DataResponseModel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.JadwalService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_jadwal.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        toolbar_activity_title.setText("Jadwal")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        setRecyclerJadwal()
    }


    fun setRecyclerJadwal(){
        val pref = SharedPrefHelper.getInstance(applicationContext).getAccount().Idkelas
        val service = ServiceBuilder.buildService(JadwalService:: class.java).getJadwalByKelas(pref)



        service.enqueue(object: Callback<DataResponseModel<List<JadwalHari>>>{
            override fun onFailure(call: Call<DataResponseModel<List<JadwalHari>>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<DataResponseModel<List<JadwalHari>>>,
                response: Response<DataResponseModel<List<JadwalHari>>>
            ) {
                if(response.isSuccessful){
                    rv_jadwal.setHasFixedSize(true)
                    rv_jadwal.layoutManager = LinearLayoutManager(this@JadwalActivity)
                    val adapter =
                        JadwalHariRecyclerAdapter(response.body()!!.data!!, applicationContext/*, response.body()!!.data!!.get(3).mapel*/)
                    rv_jadwal.adapter = adapter

                    txt_Jadwal_Mapel.text = response.body()!!.data!!.get(0).kelas.nama_kelas
                }

            }
        })
    }
}
