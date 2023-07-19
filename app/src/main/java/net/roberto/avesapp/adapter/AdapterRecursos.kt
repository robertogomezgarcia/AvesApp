package net.roberto.recyclerviewrecursos.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.roberto.avesapp.R
import net.roberto.avesapp.model.Recurso


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterRecursos(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<AdapterRecursos.ViewHolder>() {

    private var dataList: List<Recurso> = emptyList()

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

    internal fun setRecursos(recursos: List<Recurso>) {
        this.dataList = recursos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Recurso){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivfotorowrecurso = itemView.findViewById(R.id.ivfotorowrecurso) as ImageView
            val tvtitulorowrecurso = itemView.findViewById(R.id.tvtitulorowrecurso) as TextView


            tvtitulorowrecurso.text = dataItem.titulo

            // foto de internet a traves de Picasso
            Picasso.get().load("${dataItem.url}").into(ivfotorowrecurso)

            itemView.tag = dataItem

        }

    }
}