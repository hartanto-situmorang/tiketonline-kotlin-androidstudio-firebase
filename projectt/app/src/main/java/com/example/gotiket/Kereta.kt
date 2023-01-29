package com.example.gotiket

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.EventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Kereta(id:String,username:String,date:String,tujuan:String,dari:String) : Fragment() {

    private lateinit var adapter : KeretaAdapter
    private lateinit var recycleview : RecyclerView
    private lateinit var newaaraylist :  ArrayList<ListKereta>
    private lateinit var ref: DatabaseReference
    private lateinit var referensi : DatabaseReference

    var id: String? = id
    var username: String? = username
    var datetext: String? = date
    var tujuanp: String? = tujuan
    var darip: String? = dari

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kereta, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newaaraylist = arrayListOf<ListKereta>()
        newaaraylist.clear()

        ref = FirebaseDatabase.getInstance().getReference("tiket")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (a in snapshot.children) {
                        if (
                            a.child("jenis").value.toString() == "kereta" &&
                            a.child("tujuan").value.toString() == tujuanp &&
                            a.child("jemput").value.toString() == darip
                        ){
                            val newa = ListKereta(
                                a.child("harga").value.toString(),
                                a.child("id").value.toString(),
                                a.child("jam").value.toString()+" WIB",
                                a.child("jemput").value.toString(),
                                a.child("namatiket").value.toString(),
                                a.child("namatiket").value.toString(),
                                a.child("tgl").value.toString(),
                                a.child("tujuan").value.toString()
                            )
                            newaaraylist.add(newa)
                        }else{

                        }

                    }
                    if (newaaraylist.size <= 0) {
                        Toast.makeText(
                            view.context,
                            "Maaf Tiket Kereta Tidak Tersedia",
                            Toast.LENGTH_SHORT
                        ).show()
                        val newa = ListKereta(
                            "################",
                            "##########",
                            "##########",
                            "",
                            "Mohon Maaf Tiket Tidak Tersedia",
                            "",
                            "Tiket Tidak Tersedia",
                            ""
                        )
                        newaaraylist.add(newa)
                    }
                    val layoutManager = LinearLayoutManager(context)
                    recycleview = view.findViewById(R.id.recylekrt_view)
                    recycleview.layoutManager = layoutManager
                    recycleview.setHasFixedSize(true)
                    adapter = KeretaAdapter(id.toString(),username.toString(),newaaraylist)
                    recycleview.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}