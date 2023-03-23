package com.example.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.myapplication.view.HaberlerActivity
import com.example.myapplication.databinding.ActivityKullaniciBinding
import com.google.firebase.auth.FirebaseAuth

class KullaniciActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKullaniciBinding //binding 1
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityKullaniciBinding.inflate(layoutInflater) //binding 2
        val view = binding.root
        setContentView(view) //binding 2a

        auth = FirebaseAuth.getInstance()
    }
    fun girisYap(view: View){

        val email = binding.emailText.text.toString().trim() //binding 3.adım
        val sifre = binding.passwordText.text.toString().trim()   //binding 3.adım

        if (TextUtils.isEmpty(email)){
            binding.emailText.error = "Lütfen e mail giriniz."
            return@girisYap

        }else if(TextUtils.isEmpty(sifre)){
            binding.passwordText.error ="Lütfen şifre giriniz."


        }


        auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener(this) { task ->

            if (task.isSuccessful){

                val guncelKullanici = auth.currentUser?.email.toString() //isSuccesful zaten kesin giriş başarılı demek bu yüzde ? kullanmak ya da !! de soru çıkarmaz.
                Toast.makeText(this,"Hoşgeldin : ${guncelKullanici}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, HaberlerActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener { exception ->

            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
    fun kayitOl(view: View){

        val email = binding.emailText.text.toString().trim() //binding 3.adım
        val sifre = binding.passwordText.text.toString().trim()   //binding 3.adım

        if (TextUtils.isEmpty(email)){
            binding.emailText.error = "Lütfen e mail giriniz."
            return@kayitOl

        }else if(TextUtils.isEmpty(sifre)){
            binding.passwordText.error ="Lütfen şifre giriniz."


        }

        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(this) { task ->
            //listener lar asenkron çalışır zamana bağlı çalışmaz
            if (task.isSuccessful){
                //diğer activity e geçebilir
                val intent = Intent(applicationContext, HaberlerActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
}