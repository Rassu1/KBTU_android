package com.example.afinal

class BookCategory {

    var id:Long = 0
    var category:String = ""
    var description: String = ""
    var image: String = ""
    var price: String = ""
    var rating: Int = 0
    var title: String = ""


    constructor()
    constructor(category: String, description: String, id: Long, image: String, price: String, rating: Int, title: String) {
        this.id = id
        this.category = category
        this.title = title
        this.image = image
        this.description = description
        this.price = price
        this.rating = rating

    }

}