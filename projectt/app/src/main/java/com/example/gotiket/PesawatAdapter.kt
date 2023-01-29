package com.example.gotiket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView

class PesawatAdapter(id:String,username:String,private val newlist : ArrayList<ListPesawat>): RecyclerView.Adapter<PesawatAdapter.MyViewHolder>(){
    var id5:String?=id
    var username:String?=username
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.layout_listpesawat,
        parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = newlist[position]

        holder.nama.text = currentitem.jenis
        holder.waktu.text = currentitem.jam
        holder.tgl.text = currentitem.tgl
        holder.harga.text = currentitem.harga

        val datakerreta = arrayOf(currentitem.harga,currentitem.id,currentitem.jam,currentitem.jemput,currentitem.jenis,currentitem.namatiket,currentitem.tgl,currentitem.tujuan)
        holder.klik.setOnClickListener {
            val fragment2 = jumlahpenumpang(id5.toString(), username.toString(), datakerreta)
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
        val nama : TextView = itemView.findViewById(R.id.jenis_pesawat)
        val waktu : TextView = itemView.findViewById(R.id.waktu)
        val harga : TextView = itemView.findViewById(R.id.harga)
        val tgl : TextView = itemView.findViewById(R.id.tlg_berangkat)
        var klik : RelativeLayout = itemview.findViewById(R.id.rlpst_klik)
    }
}