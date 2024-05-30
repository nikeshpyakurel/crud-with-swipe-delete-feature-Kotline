package com.example.crud

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.adapter.ProductAdapter
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var firebaseDatabase:FirebaseDatabase=FirebaseDatabase.getInstance()
    var ref:DatabaseReference=firebaseDatabase.reference.child("products")
    var productList=ArrayList<ProductModel>()
    lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
       productAdapter=ProductAdapter(this@MainActivity,productList)
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (eachData in snapshot.children){
                    var product=eachData.getValue(ProductModel::class.java)
                    if(product!=null){
                        productList.add(product)
                    }
                }
                mainBinding.viewInterface.layoutManager=LinearLayoutManager(this@MainActivity)
                mainBinding.viewInterface.adapter=productAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = productAdapter.getProductID(viewHolder.adapterPosition)

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, aid ->
                        ref.child(id).removeValue().addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(applicationContext,"Data Deleted Success", Toast.LENGTH_LONG).show()
                            }
                            else{
                                Toast.makeText(applicationContext,"Data Update Faued",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    .setNegativeButton("No") { dialog, aid ->
                        dialog.dismiss()
                        productAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                val alert = builder.create()
                alert.show()
            }
        }).attachToRecyclerView(mainBinding.viewInterface)
        mainBinding.createData.setOnClickListener {
            var  intent =Intent(this@MainActivity,AddProductActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}