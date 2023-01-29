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
import com.google.firebase.database.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Pesawat(id:String,username:String,date:String,tujuan:String,dari:String) : Fragment() {
    var id1: String? = id
    var username: String? = username
    var datetext: String? = date
    var tujuanp: String? = tujuan
    var darip: String? = dari

    private lateinit var adapter: PesawatAdapter
    private lateinit var recycleview: RecyclerView
    private lateinit var newaaraylist: ArrayList<ListPesawat>
    private lateinit var ref: DatabaseReference
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pesawat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            newaaraylist = arrayListOf<ListPesawat>()
            newaaraylist.clear()
            ref = FirebaseDatabase.getInstance().getReference("tiket")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (a in snapshot.children) {
                            if (a.child("jenis").value.toString() == "pesawat" &&
                                a.child("tujuan").value.toString() == tujuanp &&
                                a.child("jemput").value.toString() == darip
                            ) {
                                val newa = ListPesawat(
                                    a.child("harga").value.toString(),
                                    a.child("id").value.toString(),
                                    a.child("jam").value.toString() + " WIB",
                                    a.child("jemput").value.toString(),
                                    a.child("namatiket").value.toString(),
                                    a.child("namatiket").value.toString(),
                                    a.child("tgl").value.toString(),
                                    a.child("tujuan").value.toString()
                                )
                                newaaraylist.add(newa)
                            }
                        }

                        if (newaaraylist.size == 0) {
                            Toast.makeText(
                                view.context,
                                "Maaf Tiket Tidak Tersedia",
                                Toast.LENGTH_SHORT
                            ).show()
                            val newa = ListPesawat(
                                "################",
                                "##########",
                                "##########",
                                "",
                                "Mohon Maaf",
                                "",
                                "Tiket Tidak Tersedia",
                                ""
                            )
                            newaaraylist.add(newa)
                        }
                        val layoutManager = LinearLayoutManager(context)
                        recycleview = view.findViewById(R.id.recylepst_view)
                        recycleview.layoutManager = layoutManager
                        recycleview.setHasFixedSize(true)
                        adapter = PesawatAdapter(id1.toString(), username.toString(), newaaraylist)
                        recycleview.adapter = adapter
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
