package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable


class TransferCoinBean : Parcelable {
    var receiveId: String? = ""
    var transferNumber: String? = ""
    var handlerFee: String? = ""

    constructor()

    protected constructor(`in`: Parcel) {
        receiveId = `in`.readString()
        transferNumber = `in`.readString()
        handlerFee = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(receiveId)
        dest.writeString(transferNumber)
        dest.writeString(handlerFee)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<TransferCoinBean> =
            object : Parcelable.Creator<TransferCoinBean> {
                override fun createFromParcel(`in`: Parcel): TransferCoinBean? {
                    return TransferCoinBean(`in`)
                }

                override fun newArray(size: Int): Array<TransferCoinBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}