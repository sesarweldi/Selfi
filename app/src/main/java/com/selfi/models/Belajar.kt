package com.selfi.models

import com.google.gson.annotations.SerializedName

data class Belajar (
    @SerializedName("id_buku")
    val idBuku: Int,
    val judul: String,
    val penulis: String,
    val penerbit: String,
    val kategori: String,
    val sampul: String,
    val lampiran: String
)