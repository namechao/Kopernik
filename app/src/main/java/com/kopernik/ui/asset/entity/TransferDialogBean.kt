package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable


class TransferDialogBean : Parcelable {
    var addressHash: String? = ""
    var transferNumber: String? = ""
    var free: String? = ""

    constructor() {}
    protected constructor(`in`: Parcel) {
        addressHash = `in`.readString()
        transferNumber = `in`.readString()
        free = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(addressHash)
        dest.writeString(transferNumber)
        dest.writeString(free)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<TransferDialogBean> =
            object : Parcelable.Creator<TransferDialogBean> {
                override fun createFromParcel(`in`: Parcel): TransferDialogBean? {
                    return TransferDialogBean(`in`)
                }

                override fun newArray(size: Int): Array<TransferDialogBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}