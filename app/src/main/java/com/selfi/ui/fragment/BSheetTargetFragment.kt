package com.selfi.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.selfi.R
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TargetService
import com.selfi.ui.activity.TargetActivity
import kotlinx.android.synthetic.main.activity_target.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_target.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BSheetTargetFragment (): BottomSheetDialogFragment(){

    private var pref_id= 0
    private var id_target = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_target, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref_id = SharedPrefHelper(activity!!.applicationContext).getAccount().nis
        btnSimpanClick()
    }



    fun btnSimpanClick(){
        btn_simpanTarget.setOnClickListener {
            if(edt_inputJudulTarget.text.toString().trim().isEmpty()){
                edt_inputJudulTarget.error = "Judul tidak boleh kosong"
                edt_inputJudulTarget.requestFocus()
                return@setOnClickListener
            }

            else{
                val service = ServiceBuilder.buildService(TargetService :: class.java, activity!!)
                service.addTarget(
                    id_target,
                    pref_id,
                    edt_inputJudulTarget.text.toString(),
                    edt_inputDeskripsiTarget.text.toString()
                ).enqueue(object : Callback <ResponseDB>{
                    override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                        Log.e("onFailure", t.message!!)
                    }

                    override fun onResponse(
                        call: Call<ResponseDB>,
                        response: Response<ResponseDB>
                    ) {
                        Toast.makeText(
                            activity!!.applicationContext,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()


                        if (response.body()!!.success) {
                            this@BSheetTargetFragment.dismiss()
                            (activity as TargetActivity).recylerTarget()
                        }
                    }
                })
            }
        }
    }
}