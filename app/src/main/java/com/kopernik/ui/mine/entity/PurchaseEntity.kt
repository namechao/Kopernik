package com.kopernik.ui.mine.entity

import android.os.Parcel
import android.os.Parcelable
import com.kopernik.ui.asset.entity.WithdrawEarningsBean

class PurchaseEntity : Parcelable {
    var mineMacName: String? = ""
    var mineMacPrice: String? = ""
    var consumeUyt: String? = ""
    var uytBanlance: String? = ""


    constructor() {}
    protected constructor(`in`: Parcel) {
        mineMacName = `in`.readString()
        mineMacPrice = `in`.readString()
        consumeUyt = `in`.readString()
        uytBanlance = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mineMacName)
        dest.writeString(mineMacPrice)
        dest.writeString(consumeUyt)
        dest.writeString(uytBanlance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<PurchaseEntity> =
            object : Parcelable.Creator<PurchaseEntity> {
                override fun createFromParcel(`in`: Parcel): PurchaseEntity? {
                    return PurchaseEntity(`in`)
                }

                override fun newArray(size: Int): Array<PurchaseEntity?> {
                    return arrayOfNulls(size)
                }
            }
    }
}