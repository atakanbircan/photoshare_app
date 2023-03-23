package com.example.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.model.Post
import com.example.myapplication.R
import com.example.myapplication.adapter.HaberRecyclerAdapter
import com.example.myapplication.databinding.ActivityHaberlerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HaberlerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHaberlerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase : FirebaseFirestore
    private lateinit var recyclerViewAdapter : HaberRecyclerAdapter



    var postListesi = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHaberlerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseFirestore.getInstance()

        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = HaberRecyclerAdapter(postListesi)
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    fun verileriAl(){
        dataBase.collection("Post").orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (snapshot != null){
                    if (snapshot.isEmpty == false){
                        val documents = snapshot.documents

                        postListesi.clear() //önceden kalanlar görünmesin diye

                        for (document in documents){
                            val kullaniciEmail = document.get("kullaniciemail") as String
                            val kullaniciYorumu= document.get("kullaniciyorum") as String
                            val gorselUrl = document.get("gorselurl") as String

                            val indilenPost = Post(kullaniciEmail,kullaniciYorumu,gorselUrl)
                            postListesi.add(indilenPost)
                        }

                        recyclerViewAdapter.notifyDataSetChanged() //yeni bilgi geldi kendini güncelle demek için
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.fotograf_paylas){
            //fotoğraf paylaşma aktivitesine gidecek
            val intent = Intent(this, FotografPaylasmaActivity::class.java)
            startActivity(intent)
        }else if (item.itemId == R.id.cikis_yap){
            auth.signOut()
            val intent = Intent(this, KullaniciActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}