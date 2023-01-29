package com.example.gotiket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class History(id:String,username:String) : Fragment() {
    var id2: String? = id
    var username2: String? = username

    private lateinit var ref: DatabaseReference
    private lateinit var referensi: DatabaseReference
    private lateinit var adapter: HistoryAdapter
    private lateinit var recycleview: RecyclerView
    private lateinit var newaaraylist: ArrayList<Listbeli>
    lateinit var nama: Array<String>
    lateinit var tgl: Array<String>
    lateinit var jam: Array<String>
    lateinit var harga: Array<String>
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newaaraylist = arrayListOf<Listbeli>()
        ref = FirebaseDatabase.getInstance().getReference("beli")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        println(a.child("status").value.toString()+"----------------- adaaaaaaaaaa")
                        println(a.child("iduser").value.toString()+"===="+id2.toString())
                        if (a.child("iduser").value.toString() == id2.toString() && a.child("status").value.toString() == "Berhasil" || a.child("status").value.toString() == "Dibatalkan" && a.child("iduser").value.toString() == id2.toString()){
                            val newa = Listbeli(
                                a.child("idtiket").value.toString(),
                                a.child("iduser").value.toString(),
                                a.child("jenis").value.toString(),
                                a.child("jumlah").value.toString(),
                                a.child("komentar").value.toString(),
                                a.child("status").value.toString(),
                                a.child("tglberangkat").value.toString()
                            )
                            newaaraylist.add(newa)
                        }
                    }
                    if (newaaraylist.size == 0) {
                        Toast.makeText(
                            view.context,
                            "History Anda Masih Kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                        val newa = Listbeli(
                            "################",
                            "##########",
                            "##########",
                            "",
                            "Mohon Maaf",
                            "",
                            ""
                        )
                        newaaraylist.add(newa)
                    }
                    val layoutManager = LinearLayoutManager(context)
                    recycleview = view.findViewById(R.id.recylehist_view)
                    recycleview.layoutManager = layoutManager
                    recycleview.setHasFixedSize(true)
                    adapter = HistoryAdapter(id2.toString(), username2.toString(), newaaraylist)
                    recycleview.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}