package com.selfi.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.selfi.models.Target

class DiffUtilTarget(
    private val oldList: List<Target>,
    private val newList: List<Target>
): DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idTarget == newList[newItemPosition].idTarget

    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
       return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
//
//        return when{
//            oldList[oldItemPosition].idTarget != newList[newItemPosition].idTarget ->{
//                false
//            }
//
//            oldList[oldItemPosition].judulTarget != newList[newItemPosition].judulTarget ->{
//                false
//            }
//
//            oldList[oldItemPosition].deskripsiTarget != newList[newItemPosition].deskripsiTarget ->{
//                false
//            }
//
//            else -> true
//
//
//        }
    }
}