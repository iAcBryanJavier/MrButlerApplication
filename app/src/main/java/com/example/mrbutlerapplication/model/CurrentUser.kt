package com.example.mrbutlerapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrentUser(val username: String, val profileImageUrl: String, val email: String, val uid:String):Parcelable {
    constructor():this("","","","")
}