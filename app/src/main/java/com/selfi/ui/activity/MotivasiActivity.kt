package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.MotivasiRecyclerAdapter
import com.selfi.models.Motivasi
import com.selfi.models.response.DataResponseModel
import com.selfi.services.api.MotivasiService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_motivasi.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MotivasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motivasi)
        toolbar_activity_title.setText("Motivasi")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        recyclerMotivasi()
        swipe_motivasi.setOnRefreshListener {
            recyclerMotivasi()
        }



        search_motivasi.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMotivasi(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchMotivasi(newText)
                return false
            }

        })
    }

    fun recyclerMotivasi() {
        swipe_motivasi.isRefreshing = true
        val service = ServiceBuilder.buildService(MotivasiService::class.java).getMotivasi()
        service.enqueue(object : Callback<DataResponseModel<List<Motivasi>>> {
            override fun onFailure(call: Call<DataResponseModel<List<Motivasi>>>, t: Throwable) {
                Toast.makeText(
                    this@MotivasiActivity,
                    "Error : ${t.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("onFailure", t.message!!)
            }


            override fun onResponse(
                call: Call<DataResponseModel<List<Motivasi>>>,
                response: Response<DataResponseModel<List<Motivasi>>>
            ) {
                if (response.isSuccessful) {
                    rv_motivasi.setHasFixedSize(true)
                    rv_motivasi.layoutManager = LinearLayoutManager(this@MotivasiActivity)
                    rv_motivasi.adapter =
                        MotivasiRecyclerAdapter(response.body()!!.data!!, this@MotivasiActivity)
                    rv_motivasi.adapter!!.notifyDataSetChanged()

                } else {
                    Toast.makeText(this@MotivasiActivity, "Error", Toast.LENGTH_SHORT).show()
                }
                swipe_motivasi.isRefreshing = false
            }
        })
    }

    fun searchMotivasi(key: String?) {
        swipe_motivasi.isRefreshing = true
        val service = ServiceBuilder.buildService(MotivasiService::class.java).searchMotivasi(key)
        service.enqueue(object : Callback<DataResponseModel<List<Motivasi>>> {
            override fun onFailure(call: Call<DataResponseModel<List<Motivasi>>>, t: Throwable) {
                Toast.makeText(
                    this@MotivasiActivity,
                    "Error : ${t.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("onFailure", t.message!!)
            }

            override fun onResponse(
                call: Call<DataResponseModel<List<Motivasi>>>,
                response: Response<DataResponseModel<List<Motivasi>>>
            ) {
                if (response.isSuccessful) {
                    rv_motivasi.setHasFixedSize(true)
                    rv_motivasi.layoutManager = LinearLayoutManager(this@MotivasiActivity)
                    rv_motivasi.adapter =
                        MotivasiRecyclerAdapter(response.body()!!.data!!, this@MotivasiActivity)
                    rv_motivasi.adapter!!.notifyDataSetChanged()
                }
                swipe_motivasi.isRefreshing = false

            }
        })
    }


}
