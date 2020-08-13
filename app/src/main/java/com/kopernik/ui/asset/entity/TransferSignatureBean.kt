package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class TransferSignatureBean : Parcelable {
    var value: String? = null
    var addressHash: String? = null
    var fee: String? = null
    var remark: String? = null
    var chainType = 0

    constructor() {}
    protected constructor(`in`: Parcel) {
        value = `in`.readString()
        addressHash = `in`.readString()
        fee = `in`.readString()
        remark = `in`.readString()
        chainType = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(value)
        dest.writeString(addressHash)
        dest.writeString(fee)
        dest.writeString(remark)
        dest.writeInt(chainType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<TransferSignatureBean> =
            object : Parcelable.Creator<TransferSignatureBean> {
                override fun createFromParcel(`in`: Parcel): TransferSignatureBean? {
                    return TransferSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<TransferSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}