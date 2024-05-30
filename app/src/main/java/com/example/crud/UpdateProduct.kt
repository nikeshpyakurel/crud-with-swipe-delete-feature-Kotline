package com.example.crud

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud.databinding.ActivityAddProductBinding
import com.example.crud.databinding.ActivityUpdateProductBinding
import com.example.crud.model.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateProduct : AppCompatActivity() {
    lateinit var updateProductBinding: ActivityUpdateProductBinding
    var id=""
    var firebaseDatabase:FirebaseDatabase=FirebaseDatabase.getInstance()
    var ref: DatabaseReference =firebaseDatabase.reference.child("products")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateProductBinding= ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(updateProductBinding.root)
        var product : ProductModel? = intent.getParcelableExtra("products")

        product.let {
            Log.d("dataaaaaaa",it?.productName.toString())
        }
        updateProductBinding.updateeditTextName.setText(product?.productName)
        updateProductBinding.updateeditTextDesc.setText(product?.productDesc)
        updateProductBinding.updateeditTextPrice.setText(product?.productPrice.toString())
        id=product?.id.toString()
        updateProductBinding.updatebutton.setOnClickListener {
            var name: String =updateProductBinding.updateeditTextName.text.toString()
            var desc: String =updateProductBinding.updateeditTextDesc.text.toString()
            var price: Int =updateProductBinding.updateeditTextPrice.text.toString().toInt()
            var updateMap= mutableMapOf<String,Any>()
            updateMap["productName"]=name
            updateMap["productPrice"]=price
            updateMap["productDesc"]=desc
            updateMap["id"]=id
            ref.child(id).updateChildren(updateMap).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Data Updated Success", Toast.LENGTH_LONG).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,"Data Update Faued",Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}