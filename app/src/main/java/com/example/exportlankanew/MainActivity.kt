package com.example.exportlankanew

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.search.SearchView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var add: Button
    private lateinit var context: Context
    private lateinit var db: DB
    private lateinit var recycleView: RecyclerView
    private lateinit var btnView: Button
    private lateinit var pname: EditText
    private lateinit var weight: EditText
    private lateinit var quantity: EditText
    private lateinit var budget: EditText
    private var adapter: OrderAdapter? = null
    private var productOrder: order? = null
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        db = DB(this)

        add.setOnClickListener{ addOrder() }
        btnView.setOnClickListener{ getOrders() }
       btnUpdate.setOnClickListener{ updateOrder()}

        adapter?.setOnClickItem {
            Toast.makeText(this, it.productName, Toast.LENGTH_SHORT).show()
            pname.setText(it.productName)
            weight.setText(it.weight.toString())
            quantity.setText(it.quantity.toString())
            budget.setText(it.budget.toString())
            productOrder = it
        }

        adapter?.setOnClickDeleteItem {
            deleteOrder(it.id)
        }
    }

    private fun addOrder(){
        val productName = pname.text.toString()
        val orderWeight = weight.text.toString().toDouble()
        val orderQuantity = quantity.text.toString().toInt()
        val orderBudget = budget.text.toString().toDouble()

        if(productName.isEmpty()){
            Toast.makeText(this,"Please enter required fields", Toast.LENGTH_SHORT).show()
        }else{
            val productOrder = order(productName = productName, weight = orderWeight, quantity = orderQuantity, budget = orderBudget)
            val status = db.addOrder(productOrder)
            //checking insert success or not
            if (status > -1) {
                Toast.makeText(this, "Order Added Successfully.", Toast.LENGTH_SHORT).show()
                clearEditText()
                getOrders()
            }else {
                Toast.makeText(this, "Record not saved.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        pname.setText("")
        weight.setText("")
        quantity.setText("")
        budget.setText("")
    }

    private fun getOrders() {
        val orderList = db.getAllOrders()
        adapter?.addItems(orderList)



        val searchView: SearchView = findViewById(R.id.searchView) as SearchView




    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                (this@MainActivity.listBarang.filter(query) as Filterable).filter(query)
                if(query == ""){
                    adapter?.addItems(orderList)

                }else {
                    var l = orderList.filter{i -> i.productName!!.contains(query, true)} as java.util.ArrayList<order>
                    adapter?.addItems(l)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                (this@MainActivity.listBarang.filter as Filterable).filter(newText)
//                listBarang.filter{i -> i.name == newText}

                if(newText == ""){
                    adapter?.addItems(orderList)
                }else {
                    var l = orderList.filter{i -> i.productName!!.contains(newText, true)} as java.util.ArrayList<order>
                    adapter?.addItems(l)
                }
                return false
            }
        })
    }

    private fun updateOrder() {
        val name = pname.text.toString()
        val weight = weight.text.toString().toDouble()
        val quantity = quantity.text.toString().toInt()
        val budget = budget.text.toString().toDouble()
    //checking whether record has been changed
        if (name == productOrder?.productName && weight == productOrder?.weight && quantity == productOrder?.quantity && budget == productOrder?.budget) {
            Toast.makeText(this, "Record not changed.", Toast.LENGTH_SHORT).show()
            return
        }
        if (productOrder == null) return

        val productOrder = order(
//             productOrder!!.productName,
//            productOrder!!.weight,
//            productOrder!!.quantity,
//            productOrder!!.budget
        name,weight,quantity,budget
        )
        val status = db.updateOrder(productOrder)
        if (status > -1) {
            clearEditText()
            getOrders()
        }else{
            Toast.makeText(this, "update failed.",Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteOrder(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete the item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            db.deleteOrderById(id)
            getOrders()
            dialog.dismiss()

        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun initView() {
        add = findViewById(R.id.aobutton)
        btnView = findViewById(R.id.viewbutton)
        recycleView = findViewById(R.id.recycler)
        pname = findViewById(R.id.aoproductnamebox)
        weight = findViewById(R.id.aoweightbox)
        quantity = findViewById(R.id.aoquantitybox)
        budget = findViewById(R.id.aobudgetbox)
        btnUpdate = findViewById(R.id.updateBtn)

    }

    private fun initRecyclerView(){
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = OrderAdapter()
        recycleView.adapter = adapter
    }
}