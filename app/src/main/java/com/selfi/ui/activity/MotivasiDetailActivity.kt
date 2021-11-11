package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.selfi.R
import com.selfi.models.Motivasi
import com.selfi.models.response.DataResponseModel
import com.selfi.services.api.MotivasiService
import com.selfi.services.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_motivasi_detail.*
import kotlinx.android.synthetic.main.item_motivasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MotivasiDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motivasi_detail)

        img_close_motivasi.setOnClickListener {
            onBackPressed()
        }

        txt_deskripsi_motivasi.text = intent.getStringExtra("deskripsi")
        judul_motivasi_detail.text = intent.getStringExtra("judul")
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(7))
        Glide.with(this)
            .load(intent.getStringExtra("gambar"))
            .apply(requestOptions)
            .into(img_detail_motivasi)
    }
}


