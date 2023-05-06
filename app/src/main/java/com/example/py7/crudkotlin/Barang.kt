package com.example.py7.crudkotlin

class Barang {


    var id: Int? = null
    var name: String? = null
    var jenis: String? = null
    var harga: String? = null
    var description: String? = null
    var image: ByteArray? = null

    constructor(id: Int, name: String, jenis: String, harga:String,description:String, image: ByteArray){
        this.id = id
        this.name = name
        this.jenis = jenis
        this.harga = harga
        this.description = description
        this.image = image
    }
}