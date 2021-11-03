package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selfi.R
import com.selfi.adapter.TargetRecylerViewAdapter
import com.selfi.ui.fragment.BSheetTargetFragment
import com.selfi.models.Target
import com.selfi.models.response.DataResponseModel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TargetService
import kotlinx.android.synthetic.main.activity_target.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        toolbar_activity_title.setText("Target")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        floating_target.setOnClickListener {
            val bottomSheetTarget = BSheetTargetFragment()
            bottomSheetTarget.show(supportFragmentManager, "TAG")
        }


        recylerTarget()

        swipe_target.setOnRefreshListener {
            recylerTarget()
        }
    }

    public fun recylerTarget() {
        swipe_target.isRefreshing = true
       val pref =  SharedPrefHelper.getInstance(applicationContext).getAccount()
        val service =
            ServiceBuilder.buildService(TargetService::class.java).getTarget(pref.nis)

        service.enqueue(object : Callback<DataResponseModel<List<Target>>> {
            override fun onFailure(call: Call<DataResponseModel<List<Target>>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<DataResponseModel<List<Target>>>,
                response: Response<DataResponseModel<List<Target>>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.data!!.isEmpty()) {
                        txt_infoTargetKosong.visibility = View.VISIBLE
                        rv_target.visibility = View.GONE

                    } else {
                        txt_infoTargetKosong.visibility = View.GONE
                        rv_target.visibility = View.VISIBLE

                        rv_target.setHasFixedSize(true)
                        rv_target.layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        val adapter =
                            TargetRecylerViewAdapter(response.body()!!.data!!, this@TargetActivity)
                        rv_target.adapter = adapter
                        rv_target.adapter!!.notifyDataSetChanged()

                    }
                    swipe_target.isRefreshing = false

                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    swipe_target.isRefreshing = false

                }
            }

        })



    }
}




