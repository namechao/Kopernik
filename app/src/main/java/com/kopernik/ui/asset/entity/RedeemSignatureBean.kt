package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class RedeemSignatureBean : Parcelable {
    var redeemValue: String? = null
    var fee: String? = null
    var nodeHash: String? = null

    constructor() {}
    constructor(redeemValue: String?) {
        this.redeemValue = redeemValue
    }

    protected constructor(`in`: Parcel) {
        redeemValue = `in`.readString()
        fee = `in`.readString()
        nodeHash = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(redeemValue)
        dest.writeString(fee)
        dest.writeString(nodeHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<RedeemSignatureBean> =
            object : Parcelable.Creator<RedeemSignatureBean> {
                override fun createFromParcel(`in`: Parcel): RedeemSignatureBean? {
                    return RedeemSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<RedeemSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}