package com.coffee.shop.online.model

import android.os.Parcel
import android.os.Parcelable

class ItemsDTO(
    var title: String = "",
    var description: String = "",
    var picUrl: MutableList<String> = mutableListOf(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var extra: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(picUrl)
        parcel.writeDouble(price)
        parcel.writeDouble(rating)
        parcel.writeInt(numberInCart)
        parcel.writeString(extra)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ItemsDTO> {
        override fun createFromParcel(parcel: Parcel): ItemsDTO = ItemsDTO(parcel)

        override fun newArray(size: Int): Array<ItemsDTO?> = arrayOfNulls(size)
    }
}