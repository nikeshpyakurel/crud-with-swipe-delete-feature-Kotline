package com.example.crud

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud.databinding.ActivityAddProductBinding
import com.example.crud.model.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProductActivity : AppCompatActivity() {
    lateinit var addProductBinding: ActivityAddProductBinding


    var firebaseDatabase:FirebaseDatabase=FirebaseDatabase.getInstance()
    var ref:DatabaseReference=firebaseDatabase.reference.child("products")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addProductBinding=ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(addProductBinding.root)
        addProductBinding.button.setOnClickListener {
            var name: String =addProductBinding.editTextName.text.toString()
            var desc: String =addProductBinding.editTextDesc.text.toString()
            var price: Int =addProductBinding.editTextPrice.text.toString().toInt()

            var id = ref.push().key.toString()

            var data =ProductModel(name, price ,id, desc)
            ref.child(id).setValue(data).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Data Uploaded Success",Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                {
                    Toast.makeText(applicationContext,it.exception?.message,Toast.LENGTH_LONG).show()
                }
            }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}