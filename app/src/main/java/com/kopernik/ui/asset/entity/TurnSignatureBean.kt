package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class TurnSignatureBean : Parcelable {
    var turnValue: String? = null
    var fee: String? = null
    var nodeHash: String? = null
    var targetNodeHash: String? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        turnValue = `in`.readString()
        fee = `in`.readString()
        nodeHash = `in`.readString()
        targetNodeHash = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(turnValue)
        dest.writeString(fee)
        dest.writeString(nodeHash)
        dest.writeString(targetNodeHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<TurnSignatureBean> =
            object : Parcelable.Creator<TurnSignatureBean> {
                override fun createFromParcel(`in`: Parcel): TurnSignatureBean? {
                    return TurnSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<TurnSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}