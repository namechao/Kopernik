package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class RegisterNodeSignatureBean : Parcelable {
    var type = 0
    var desc1: String? = null
    var desc2: String? = null
    var desc3: String? = null
    var balance: String? = null
    var fee //手续费
            : String? = null
    var nodeName: String? = null
    var nodeAddress: String? = null
    var website: String? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        type = `in`.readInt()
        desc1 = `in`.readString()
        desc2 = `in`.readString()
        desc3 = `in`.readString()
        balance = `in`.readString()
        fee = `in`.readString()
        nodeName = `in`.readString()
        nodeAddress = `in`.readString()
        website = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(type)
        dest.writeString(desc1)
        dest.writeString(desc2)
        dest.writeString(desc3)
        dest.writeString(balance)
        dest.writeString(fee)
        dest.writeString(nodeName)
        dest.writeString(nodeAddress)
        dest.writeString(website)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<RegisterNodeSignatureBean> =
            object : Parcelable.Creator<RegisterNodeSignatureBean> {
                override fun createFromParcel(`in`: Parcel): RegisterNodeSignatureBean? {
                    return RegisterNodeSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<RegisterNodeSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}