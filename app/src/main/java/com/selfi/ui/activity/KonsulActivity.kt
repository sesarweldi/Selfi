package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.KonsulRecyclerViewAdapter
import com.selfi.models.konsul
import com.selfi.models.response.DataResponseModel
import com.selfi.services.api.KonsulService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_konsul.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonsulActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsul)

        toolbar_activity_title.setText("Konsultasi")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }
        recylerKonsul()
    }


    private fun recylerKonsul() {

        val service = ServiceBuilder.buildService(KonsulService::class.java, this@KonsulActivity)
        service.getKonseling().enqueue(object : Callback<DataResponseModel<List<konsul>>> {
            override fun onFailure(
                call: Call<DataResponseModel<List<konsul>>>,
                t: Throwable
            ) {
                Toast.makeText(
                    applicationContext,
                    "Error : ${t.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("onFailure", t.message!!)
            }


            override fun onResponse(
                call: Call<DataResponseModel<List<konsul>>>,
                response: Response<DataResponseModel<List<konsul>>>
            ) {
                if (response.isSuccessful) {
                    rv_konsul.layoutManager = LinearLayoutManager(this@KonsulActivity)
                    val adapter =
                        KonsulRecyclerViewAdapter(response.body()!!.data!!, applicationContext)
                    rv_konsul.adapter = adapter

                }
            }

        })
    }
}

//    rv_konsul.layoutManager = LinearLayoutManager(this@KonsulActivity)
//    val adapter =
//        KonsulRecyclerViewAdapter(response.body()!!, applicationContext)
//    rv_konsul.adapter = adapter


// Toast.makeText(this@KonsulActivity,"Dipencet",Toast.LENGTH_LONG).show()

//whatsApp("6282123558369")

//  val installed: Boolean = appInstalledOrNot("com.whatsapp")

//                if (installed){
//
//                }else{
//                    Toast.makeText(this@KonsulActivity,"Silahkan download WhatsApp terlebih dahulu", Toast.LENGTH_SHORT).show()
//                }
//            }


//     private fun whatsApp(phoneNumber: String){
//         try {
//             val packageManager = this.packageManager
//             val i = Intent(Intent.ACTION_VIEW)
//             val url = "https://api.whatsapp.com/send?phone=" +"$phoneNumber"
//             i.setPackage("com.whatsapp")
//             i.data = Uri.parse(url)
//
//             if (i.resolveActivity(packageManager)!=null){
//                 startActivity(i)
//             }
//             else{
//                 Toast.makeText(this,"Silahkan download WhatsApp terlebih dahulu", Toast.LENGTH_SHORT).show()
//             }
//
//         }
//
//         catch (e: Exception){
//             Toast.makeText(this,""+e.stackTrace,Toast.LENGTH_SHORT).show()
//         }

//    private fun appInstalledOrNot(url: String):Boolean{
////        val packageManager: PackageManager = getPackageManager()
////        var appInstalled:Boolean
////        try {
////            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES)
////            appInstalled= true
////        }
////        catch (e: PackageManager.NameNotFoundException){
////            // Toast.makeText(this,""+e.stackTrace,Toast.LENGTH_SHORT).show()
////            appInstalled = false
////         }
////        return appInstalled
////    }



