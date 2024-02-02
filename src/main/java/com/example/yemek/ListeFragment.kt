package com.example.yemek

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: YemekAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_liste, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = YemekAdapter()  // YemekAdapter'ı oluşturun (bu sınıfı daha sonra implemente etmelisiniz)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Burada adapter'a yemek verilerini ekleyebilirsiniz, örneğin:
        // adapter.submitList(yemekVerileri)
        // Veritabanından yemekleri al ve adapter'a ekle
        val dbHelper = DBHelper(requireContext())
        val yemekler = dbHelper.getAllYemekler()
        adapter.setYemekler(yemekler)

        return view
    }


}