package com.gsanthosh91.whatsappdaterv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val list: List<MyChat>, private val itsMe: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_RIGHT = 1
    private val TYPE_LEFT = 2
    private val TYPE_CENTER = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == TYPE_RIGHT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mine_message, parent, false)
            RightViewHolder(view)
        } else if (viewType == TYPE_CENTER) {
            view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_center_message, parent, false)
            CenterViewHolder(view)
        } else {
            view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_other_message, parent, false)
            LeftViewHolder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val obj = list[position]
        return if (obj.from.equals(itsMe)) {
            TYPE_RIGHT
        } else if (obj.from.equals(0)) {
            TYPE_CENTER
        } else {
            TYPE_LEFT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        if (getItemViewType(position) == TYPE_RIGHT) {
            (holder as RightViewHolder).bind(item)
        } else if (getItemViewType(position) == TYPE_CENTER) {
            (holder as CenterViewHolder).bind(item)
        } else {
            (holder as LeftViewHolder).bind(item)
        }

    }


    class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var content: TextView? = null
        private var timestamp: TextView? = null

        init {
            content = itemView.findViewById(R.id.content)
            timestamp = itemView.findViewById(R.id.timestamp)
        }

        fun bind(item: MyChat) {
            content?.text = """${item.id}: ${item.content}"""
            timestamp?.text = item.timestamp.toString()
        }

    }

    class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var content: TextView? = null
        private var timestamp: TextView? = null

        init {
            content = itemView.findViewById(R.id.content)
            timestamp = itemView.findViewById(R.id.timestamp)
        }

        fun bind(item: MyChat) {
            content?.text = """${item.id}: ${item.content}"""
            timestamp?.text = item.timestamp.toString()
        }

    }

    class CenterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var content: TextView? = null

        init {
            content = itemView.findViewById(R.id.content)
        }

        fun bind(item: MyChat) {
            content?.text = item.content
        }

    }

    override fun getItemCount(): Int = list.size
}