package com.example.yemek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class YemekAdapter : RecyclerView.Adapter<YemekAdapter.YemekViewHolder>() {
    private var yemekler: List<Yemek> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return YemekViewHolder(view)
    }

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        val yemek = yemekler[position]
        holder.yemekIsmiTextView.text = yemek.yemekIsmi
        holder.yemekTarifiTextView.text = yemek.yemekTarifi
        holder.yemekResimImageView.setImageBitmap(yemek.resimBitmap)
    }

    override fun getItemCount(): Int {
        return yemekler.size
    }

    fun setYemekler(yemekler: List<Yemek>) {
        this.yemekler = yemekler
        notifyDataSetChanged()
    }

    inner class YemekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val yemekIsmiTextView: TextView = itemView.findViewById(R.id.yemekIsmi)
        val yemekTarifiTextView: TextView = itemView.findViewById(R.id.yemekTarifi)
        val yemekResimImageView: ImageView = itemView.findViewById(R.id.yemekResim)
    }
}
