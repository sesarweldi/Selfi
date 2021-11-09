package com.selfi.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import com.selfi.R
import com.selfi.adapter.BelajarNoraRecyclerAdapter
import com.selfi.models.Belajar
import com.selfi.models.response.DataResponseModel
import com.selfi.services.api.BelajarService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_belajar_nora.*
import kotlinx.android.synthetic.main.fragment_belajar_produktif.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BelajarProduktifFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_belajar_produktif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerProduktif("")
    }

    fun recyclerProduktif(keyword: String?){
        val service = ServiceBuilder.buildService(BelajarService:: class.java,activity!!).searchBukuProduktif(keyword)
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
                    rv_belajarProduktif.setHasFixedSize(true)
                    rv_belajarProduktif.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
                    rv_belajarProduktif.adapter =
                        BelajarNoraRecyclerAdapter(response.body()!!.data!!, activity!!.applicationContext)
                    rv_belajarProduktif.adapter!!.notifyDataSetChanged()

                    search_produktif.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            recyclerProduktif(query)
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            recyclerProduktif(newText)
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
