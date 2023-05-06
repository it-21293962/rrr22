package com.example.exportlankanew
import java.util.*
class order {


    var id: Int = getAutoId()
    var productName: String = ""
    var weight: Double = 0.0
    var budget: Double = 0.0
    var quantity: Int = 0



    companion object{
        fun getAutoId() : Int{
            val random = Random()
            return random.nextInt(100)
        }
    }

    constructor() {}

    constructor(id: Int, productName: String, weight: Double, quantity: Int, budget: Double) {
        this.id = id
        this.productName = productName
        this.weight = weight
        this.quantity = quantity
        this.budget = budget
    }

    constructor(productName: String, weight: Double, quantity: Int, budget: Double) {
        this.productName = productName
        this.weight = weight
        this.quantity = quantity
        this.budget = budget
    }

}
