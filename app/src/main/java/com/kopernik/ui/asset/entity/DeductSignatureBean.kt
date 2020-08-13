package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class DeductSignatureBean : Parcelable {
    var deductValue: String? = null
    var fee: String? = null
    var nodeHash: String? = null

    constructor() {}

    protected constructor(`in`: Parcel) {
        deductValue = `in`.readString()
        fee = `in`.readString()
        nodeHash = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(deductValue)
        dest.writeString(fee)
        dest.writeString(nodeHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<DeductSignatureBean> =
            object : Parcelable.Creator<DeductSignatureBean> {
                override fun createFromParcel(`in`: Parcel): DeductSignatureBean? {
                    return DeductSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<DeductSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}