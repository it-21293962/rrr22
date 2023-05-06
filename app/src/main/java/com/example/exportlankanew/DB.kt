package com.example.exportlankanew

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)  {

    companion object{
        private const val DB_VERSION = 1
        private const val DB_NAME = "ExportLanka.DB"
        private const val TABLE_NAME = "ORDERS"


        //Table column names
        private const val ID = "id"
        private const val NAME = "name"
        private const val WEIGHT = "weight"
        private const val QUANTITY = "quantity"
        private const val BUDGET = "budget"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val TABLE_CREATE_QUERY = "CREATE TABLE $TABLE_NAME" +
                "(" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NAME TEXT, " +
                "$WEIGHT DOUBLE, " +
                "$QUANTITY INTEGER," +
                "$BUDGET DOUBLE" +
                ");"

        db?.execSQL(TABLE_CREATE_QUERY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME;"

        // Drop older table if exists
        db?.execSQL(DROP_TABLE_QUERY)

        // Create tables again
        onCreate(db)
    }

    fun addOrder(productOrder: order): Long {
        val sqLiteDatabase: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NAME, productOrder.productName)
        contentValues.put(WEIGHT, productOrder.weight)
        contentValues.put(QUANTITY, productOrder.quantity)
        contentValues.put(BUDGET, productOrder.budget)

        //save to the table
        val success = sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
        //close connection
        sqLiteDatabase.close()
        return success
    }

    //get all orders to a list
    @SuppressLint("Range")
    fun getAllOrders(): ArrayList<order> {
        val orderList: ArrayList<order> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val cursor: Cursor?

        try {
            cursor =  db.rawQuery(query, null)
        }catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }

        var id: Int
        var productName: String
        var weight : Double
        var quantity : Int
        var budget : Double

        if (cursor.moveToFirst()) {
            do {
                //val orderOb = order()
                id = cursor.getInt(cursor.getColumnIndex("id"))
                productName = cursor.getString(cursor.getColumnIndex("name"))
                weight = cursor.getDouble(cursor.getColumnIndex("weight"))
                quantity = cursor.getInt(cursor.getColumnIndex("quantity"))
                budget = cursor.getDouble(cursor.getColumnIndex("budget"))

                val productOrder = order(id = id, productName = productName, weight = weight, quantity = quantity, budget = budget )
                //orderOb.setId(cursor.getInt(0))
                //orderOb.setProductName(cursor.getString(1))
                //orderOb.setWeight(cursor.getDouble(2))
                //orderOb.setQuantity(cursor.getInt(3))
                //orderOb.setBudget(cursor.getDouble(4))
                orderList.add(productOrder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderList
    }

    fun updateOrder(productOrder: order): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, productOrder.id)
        contentValues.put(NAME, productOrder.productName)
        contentValues.put(WEIGHT, productOrder.weight)
        contentValues.put(QUANTITY, productOrder.quantity)
        contentValues.put(BUDGET, productOrder.budget)

        val success = db.update(TABLE_NAME, contentValues, "id=" +productOrder.id, null)
        db.close()
        return success
    }

    fun deleteOrderById(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TABLE_NAME, "id=+$id" , null )
        db.close()
        return success
    }






}