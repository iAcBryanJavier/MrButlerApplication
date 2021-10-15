package com.example.mrbutlerapplication.model

class Employee {
    var name: String = ""
    var email: String = ""
    var contactNum: String = ""
    var age: String = ""
    var address: String = ""

    constructor(name: String, email: String, contactNum: String, age: String, address: String){
        this.name = name
        this.email = email
        this.contactNum = contactNum
        this.age = age
        this.address = address
    }
}