package com.example.assigment3

import android.os.Parcel
import android.os.Parcelable

data class Member(val fullName: String, val email: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(email)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Member> {
        override fun createFromParcel(parcel: Parcel): Member = Member(parcel)
        override fun newArray(size: Int): Array<Member?> = arrayOfNulls(size)
    }
}
