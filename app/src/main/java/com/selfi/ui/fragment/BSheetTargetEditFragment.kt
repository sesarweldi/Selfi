package com.selfi.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_bsheet_target_edit.*
import kotlinx.android.synthetic.main.fragment_bsheet_todo_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BSheetTargetEditFragment() : BottomSheetDialogFragment() {

    var idTarget = 0

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_bsheet_target_edit, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = arguments
        idTarget = bundle!!.getInt("idEditTarget",0)
        et_judul_edit_target.setText(bundle!!.getString("judulEditTarget"))
        et_judul_edit_target.setText(bundle!!.getString("judulEditTarget"))
        et_deskripsi_edit_target.setText(bundle!!.getString("deskripsiEditTarget"))


        deleteTarget()
        editTarget()


    }

    fun editTarget(){
        val pref  = SharedPrefHelper.getInstance(activity!!.applicationContext).getAccount().nis

        btn_simpan_edit_target.setOnClickListener {
            ServiceBuilder.buildService(TargetService:: class.java, activity!!).updateTarget(
                idTarget, pref, et_judul_edit_target.text.toString(), et_deskripsi_edit_target.text.toString()
            ).enqueue(object: Callback<ResponseDB>{
                override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Error : ${t.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(call: Call<ResponseDB>, response: Response<ResponseDB>) {
                    Toast.makeText(
                        activity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    this@BSheetTargetEditFragment.dismiss()
                    (activity as TargetActivity).recylerTarget()
                }
            })
        }


    }

    fun deleteTarget(){

        btn_hapus_edit_target.setOnClickListener {
            var alert = AlertDialog.Builder(view!!.rootView.context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
            alert.setTitle("Hapus Target")
            alert.setMessage("Apakah anda ingin menghapus Target ini?")
            alert.setPositiveButton("Ya", { dialog: DialogInterface?, which: Int ->

                val pref =
                    SharedPrefHelper.getInstance(activity!!.applicationContext).getAccount().nis

                val service = ServiceBuilder.buildService(TargetService::class.java, activity!!.applicationContext)
                    .deleteTarget(pref, idTarget)
                service.enqueue(object : Callback<ResponseDB> {
                    override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Error : ${t.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(
                        call: Call<ResponseDB>,
                        response: Response<ResponseDB>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            if (response.body()!!.success) {
                                this@BSheetTargetEditFragment.dismiss()
                                (activity as TargetActivity).recylerTarget()
                            }
                        } else {
                            Toast.makeText(activity!!.applicationContext, "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                })
            })
            alert.setNegativeButton("Tidak", { dialog: DialogInterface?, which: Int -> })
            alert.show()

        }



    }


}
