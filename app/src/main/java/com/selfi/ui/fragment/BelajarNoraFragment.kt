package com.selfi.ui.fragment

import android.net.DnsResolver
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.selfi.R
import com.selfi.adapter.BelajarNoraRecyclerAdapter
import com.selfi.models.Belajar
import com.selfi.models.response.DataResponseModel
import com.selfi.services.api.BelajarService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_belajar_nora.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BelajarNoraFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_belajar_nora, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerNora("")
    }


    fun recyclerNora(keyword: String?){
        val service = ServiceBuilder.buildService(BelajarService:: class.java, activity!!).searchBukuNormatif(keyword)
        service.enqueue(object: Callback<DataResponseModel<List<Belajar>>>{
            override fun onFailure(call: Call<DataResponseModel<List<Belajar>>>, t: Throwable) {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Error : ${t.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("onFailure", t.message!!)
            }

            override fun onResponse(
                call: Call<DataResponseModel<List<Belajar>>>,
                response: Response<DataResponseModel<List<Belajar>>>
            ) {
                if (response.isSuccessful){
                    rv_belajarNora.setHasFixedSize(true)
                    rv_belajarNora.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
                    rv_belajarNora.adapter =
                        BelajarNoraRecyclerAdapter(response.body()!!.data!!, activity!!.applicationContext)
                    rv_belajarNora.adapter!!.notifyDataSetChanged()

                    search_nora.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            recyclerNora(query)
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            recyclerNora(newText)
                            return false
                        }

                    })
                }
                else{
                    Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
