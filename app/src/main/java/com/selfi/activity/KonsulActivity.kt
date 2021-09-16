package com.selfi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selfi.R
import com.selfi.adapter.KonsulRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_konsul.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import kotlinx.android.synthetic.main.toolbar_activity.view.*

class KonsulActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<KonsulRecyclerViewAdapter.ListViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsul)

        toolbar_activity_title.setText("Konsultasi")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        rv_konsul.layoutManager =LinearLayoutManager(this)
        adapter = KonsulRecyclerViewAdapter()
        rv_konsul.adapter = adapter
    }
}
