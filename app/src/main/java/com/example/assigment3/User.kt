package com.example.assigment3

import android.os.Parcel
import android.os.Parcelable

data class `User.kt`(val name: String, val email: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<`User.kt`> {
        override fun createFromParcel(parcel: Parcel): `User.kt` {
            return `User.kt`(parcel)
        }

        override fun newArray(size: Int): Array<`User.kt`?> {
            return arrayOfNulls(size)
        }
    }
}