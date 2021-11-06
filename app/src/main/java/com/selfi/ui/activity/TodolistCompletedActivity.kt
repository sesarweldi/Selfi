package com.selfi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.selfi.R
import com.selfi.adapter.TodoCompletedRecyclerAdapter
import com.selfi.models.Todo
import com.selfi.models.response.DataResponseModel
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.TodoService
import kotlinx.android.synthetic.main.activity_todolist_completed.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodolistCompletedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist_completed)
        toolbar_activity_title.setText("Todo List")
        toolbar_activity_back.setOnClickListener {
            onBackPressed()
        }

        recyclerTodoCompleted()
    }


    fun recyclerTodoCompleted(){
        val pref =  SharedPrefHelper.getInstance(applicationContext).getAccount().nis
        val service = ServiceBuilder.buildService(TodoService:: class.java).getTodoCompleted(pref)
        service.enqueue(object: Callback<DataResponseModel<List<Todo>>> {
            override fun onFailure(call: Call<DataResponseModel<List<Todo>>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<DataResponseModel<List<Todo>>>,
                response: Response<DataResponseModel<List<Todo>>>
            ) {
                if(response.isSuccessful){

                        rv_todo_completed.setHasFixedSize(true)
                        rv_todo_completed.layoutManager = LinearLayoutManager(this@TodolistCompletedActivity)
                        val adapter =
                            TodoCompletedRecyclerAdapter(response.body()!!.data!!, this@TodolistCompletedActivity){
                                recyclerTodoCompleted()
                            }
                        rv_todo_completed.adapter = adapter
                }
                else{
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
