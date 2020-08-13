package com.kopernik.ui.Ecology.entity

import android.os.Parcel
import android.os.Parcelable

class CheckRegisterInfo : Parcelable {
    var high: String? = null
    var balance: String? = null
    var low: String? = null
    var isOk = false
    var imglist: List<String>? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        high = `in`.readString()
        balance = `in`.readString()
        low = `in`.readString()
        isOk = `in`.readByte().toInt() != 0
        imglist = `in`.createStringArrayList()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(high)
        dest.writeString(balance)
        dest.writeString(low)
        dest.writeByte((if (isOk) 1 else 0).toByte())
        dest.writeStringList(imglist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<CheckRegisterInfo?> =
            object : Parcelable.Creator<CheckRegisterInfo?> {
                override fun createFromParcel(`in`: Parcel): CheckRegisterInfo? {
                    return CheckRegisterInfo(`in`)
                }

                override fun newArray(size: Int): Array<CheckRegisterInfo?> {
                    return arrayOfNulls(size)
                }
            }
    }
}