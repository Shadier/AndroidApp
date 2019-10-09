package com.example.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.series_element.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.AsyncTask
import java.net.URL


class Adapter (items : ArrayList<Serie>, var clickListener: ClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var items : ArrayList<Serie>? = null
    var viewHolder: ViewHolder? = null
    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.series_element, parent, false)
        viewHolder = ViewHolder(vista, clickListener)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return this.items?.count()!!
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item = items?.get(position)

        Picasso.get()
            .load(item?.picture)
            .into(holder.picture)
        holder.name?.text = item?.name
        holder.status?.text = item?.status

    }


    class ViewHolder(vista: View, listener: ClickListener) : RecyclerView.ViewHolder(vista), View.OnClickListener{


        var vista = vista
        var listener: ClickListener? = null
        var picture : ImageView? = null
        var name : TextView? = null
        var status : TextView? = null
        init {
            picture = vista.findViewById(R.id.imgCover)
            name = vista.txtName
            status = vista.txtStatus
            this.listener = listener
            vista.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            this.listener?.onClic(v!!, adapterPosition)
        }
    }


}