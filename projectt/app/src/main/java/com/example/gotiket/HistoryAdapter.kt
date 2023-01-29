package com.example.gotiket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter (id:String,username:String,private val newlist : ArrayList<Listbeli>): RecyclerView.Adapter<HistoryAdapter.MyViewHolder>(){

    var id:String?=id
    var username:String?=username

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.layout_listhistory,
            parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = newlist[position]

        holder.jenis.text = currentitem.jenis
        holder.jumlah.text = currentitem.komentar
        holder.tgl.text = currentitem.tglberangkat
        holder.status.text = currentitem.status

        val databeli = arrayOf(
            currentitem.idtiket,
            currentitem.iduser,
            currentitem.jenis,
            currentitem.jumlah,
            currentitem.komentar,
            currentitem.status,
            currentitem.tglberangkat
        )

        holder.klik.setOnClickListener {
            val fragment2 = commenthistory2(id.toString(), username.toString(), databeli)
            val activity : MainActivity = it.context as MainActivity
            val fragmentTransaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment2)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return newlist.size
    }

    class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val jenis : TextView = itemView.findViewById(R.id.jenis_hyst)
        val jumlah : TextView = itemView.findViewById(R.id.jmltikethyst)
        val tgl : TextView = itemView.findViewById(R.id.tglhyst)
        val status : TextView = itemView.findViewById(R.id.statushyst)
        var klik : RelativeLayout = itemview.findViewById(R.id.rlhyst_klik)
    }
}