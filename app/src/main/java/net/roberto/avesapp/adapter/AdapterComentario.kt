package net.roberto.recyclerviewcomentarios.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.roberto.avesapp.R
import net.roberto.avesapp.model.Comentario


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterComentario(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<AdapterComentario.ViewHolder>() {

    private var dataList: List<Comentario> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setComentarios(comentarios: List<Comentario>) {
        this.dataList = comentarios
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Comentario){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvfecha = itemView.findViewById(R.id.tvfecha) as TextView
            val tvcomentario = itemView.findViewById(R.id.tvcomentario) as TextView
            val tvnickcomentario = itemView.findViewById(R.id.tvnickcomentario) as TextView

            tvfecha.text = dataItem.fecha
            tvcomentario.text = dataItem.comentario
            tvnickcomentario.text = dataItem.nick



            itemView.tag = dataItem

        }

    }
}