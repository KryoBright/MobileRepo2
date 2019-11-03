package com.example.dontforget

import android.app.LauncherActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.note_item.view.*

class AdapterForRecycler( val context: Context,val launch:InterfaceActivityLauncher,val sPE:SharedPreferences) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        var taskAmount:Int=0
        lateinit var categoriesList:List<CategoryEntity>
        lateinit var allTasks:List<List<TaskEntity>>
        fun prepare(dataGiver:DatabaseManipulator) {
            taskAmount = dataGiver.taskAmount()+dataGiver.getCategories().size
            categoriesList = dataGiver.getCategories()
            allTasks = categoriesList.map {dataGiver.getTasks(it.category_id)}
        }
    }



    override fun getItemCount(): Int {
        //return dirName.list().filter{((reg.matchEntire(it)!=null)and(addReg.containsMatchIn(it)))}.size
        return taskAmount
    }

    override fun getItemViewType(position: Int): Int {
        var ind=position
        var cat=0
        while ((cat<categoriesList.size)and(ind>(allTasks[cat].size)))
        {
            ind -= (allTasks[cat].size+1)
            cat++
        }
        var type=0
        if (ind!=0) {
            type = 1
        }
        return type
    }
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder:RecyclerView.ViewHolder
        if (viewType==1)
        {
            viewHolder=ViewHolderItem(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false))
        }
        else
        {
            viewHolder=ViewHolderCategory(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        var ind=position
        var cat=0
        while ((cat<categoriesList.size)and(ind>(allTasks[cat].size)))
        {
            ind -= (allTasks[cat].size+1)
            cat++
        }
        if (ind!=0)
        {
            (holder as ViewHolderItem).chkr.isChecked=allTasks[cat][ind-1].task_done
            if (categoriesList[cat].color!=null) {
            }
            holder.taskDesk.text=allTasks[cat][ind-1].task_description
            holder.taskName.text=allTasks[cat][ind-1].task_title
           /* holder.chkr.setOnClickListener{
                dataGiver.updStatus(holder.chkr.isChecked,allTasks[cat][ind-1].task_id)
            }
            holder.itemView.setOnClickListener {
                dataGiver.redact(allTasks[cat][ind-1].task_id)
            }*/
            holder.itemView.setOnClickListener {
                sPE.edit().putLong("id",allTasks[cat][ind-1].task_id).apply()
                val intent=Intent(context,ActivityTaskMore::class.java)
                launch.launchActivity(intent)
            }
        }
        else
        {
            (holder as ViewHolderCategory).textCategory.text=categoriesList[cat].category_title
        }
            }

    fun thanosSwipe(viewHolder: RecyclerView.ViewHolder){
        //Some delete
        notifyItemRemoved(viewHolder.adapterPosition)
        notifyDataSetChanged()
    }
}

class ViewHolderItem (view: View) : RecyclerView.ViewHolder(view) {
    val colorer=view.colorBar
    val taskName=view.taskNameText
    val taskDesk=view.taskDeskText
    val chkr=view.doneChkr
}

class ViewHolderCategory (view: View) : RecyclerView.ViewHolder(view) {
    val textCategory=view.categoryNameText
}

