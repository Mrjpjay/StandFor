package com.example.standfor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.standfor.R
import com.example.standfor.model.LFList
import com.example.standfor.model.Vars

class AcronymAdapter(val list: List<Vars>): RecyclerView.Adapter<AcronymAdapter.AcronymViewHolder>() {

    class AcronymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            textView = itemView.findViewById(R.id.tv_acronym)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false)
        return AcronymViewHolder(view);
    }

    override fun onBindViewHolder(holder: AcronymViewHolder, position: Int) {
        holder.textView.text = list.get(position).lf
    }

    override fun getItemCount() = list.size

}