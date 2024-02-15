package com.example.recyclerviewinfocom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewinfocom.DetailsAdapter
import com.example.recyclerviewinfocom.R
import com.example.recyclerviewinfocom.data.FetchDetailsModel
import com.example.recyclerviewinfocom.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<FetchDetailsModel>
    private lateinit var adapter: DetailsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataList = arrayListOf()
        dataBase = FirebaseDatabase.getInstance().getReference("User")
        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val userData = data.getValue(FetchDetailsModel::class.java)
                        dataList.add(userData!!)
                    }
                    adapter = DetailsAdapter(dataList)
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "$error")
            }
        })

        recyclerView = findViewById(R.id.rv_container)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DetailsAdapter(dataList)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        binding.sortByName.setOnClickListener {
            dataList.sortBy {
                it.name
            }
            adapter = DetailsAdapter(dataList)
            recyclerView.adapter = adapter
            recyclerView.hasFixedSize()
        }
        binding.sortByCity.setOnClickListener {
            dataList.sortBy {
                it.city
            }
            adapter = DetailsAdapter(dataList)
            recyclerView.adapter = adapter
            recyclerView.hasFixedSize()
        }
        binding.sortByAge.setOnClickListener {
            dataList.sortBy {
                it.age
            }
            adapter = DetailsAdapter(dataList)
            recyclerView.adapter = adapter
            recyclerView.hasFixedSize()
        }
        binding.addDetatilsForm.setOnClickListener {
            var intent = Intent(this, AddDetatilsActivity::class.java)
            startActivity(intent)
        }
    }
}