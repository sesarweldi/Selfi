package com.selfi.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.adapter.diffutil.DiffUtilTarget
import com.selfi.models.Target
import com.selfi.models.response.ResponseDB
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TargetService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetRecylerViewAdapter(private var mValues: List<Target>, private val mContext: Context) :
    RecyclerView.Adapter<TargetRecylerViewAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_target, parent, false)
    )

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = mValues[position]
        holder.txt_JudulTarget.text = item.judulTarget
        holder.txt_DeskripsiTarget.text = item.deskripsiTarget


        holder.card_target.setOnClickListener { view ->
            var alert = AlertDialog.Builder(view.rootView.context)
            alert.setTitle("Hapus Target")
            alert.setMessage("Apakah anda ingin menghapus Target ini?")
            alert.setPositiveButton("Ya", { dialog: DialogInterface?, which: Int ->

                val pref =
                    SharedPrefHelper.getInstance(mContext.applicationContext).getAccount().nis

                val service = ServiceBuilder.buildService(TargetService::class.java)
                    .deleteTarget(pref, item.idTarget)
                service.enqueue(object : Callback<ResponseDB> {
                    override fun onFailure(call: Call<ResponseDB>, t: Throwable) {
                        Toast.makeText(
                            mContext.applicationContext,
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
                                mContext.applicationContext,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(mContext.applicationContext, "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                })
            })
            alert.setNegativeButton("Tidak", { dialog: DialogInterface?, which: Int -> })
            alert.show()
        }
    }

    inner class ListViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val txt_JudulTarget: TextView = mView.findViewById(R.id.txt_JudulTarget)
        val txt_DeskripsiTarget: TextView = mView.findViewById(R.id.txt_DeskripsiTarget)
        val card_target: CardView = mView.findViewById(R.id.cardItemTarget)
    }

//    fun updatedData(mValues2: List<Target>){
//        val diffResults = DiffUtil.calculateDiff(DiffUtilTarget(mValues,mValues2))
//        diffResults.dispatchUpdatesTo(this)
//    }
}