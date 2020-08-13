package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable


class WithdrawCoinBean : Parcelable {
    var addressHash: String? = ""
    var withdrawNumber: String? = ""
    var withdrawNumberUnit: String? = ""
    var mineFee: String? = ""
    var mineFeeUnit: String? = ""

    constructor() {}
    protected constructor(`in`: Parcel) {
        addressHash = `in`.readString()
        withdrawNumber = `in`.readString()
        withdrawNumberUnit = `in`.readString()
        mineFee = `in`.readString()
        mineFeeUnit = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(addressHash)
        dest.writeString(withdrawNumber)
        dest.writeString(withdrawNumberUnit)
        dest.writeString(mineFee)
        dest.writeString(mineFeeUnit)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<WithdrawCoinBean> =
            object : Parcelable.Creator<WithdrawCoinBean> {
                override fun createFromParcel(`in`: Parcel): WithdrawCoinBean? {
                    return WithdrawCoinBean(`in`)
                }

                override fun newArray(size: Int): Array<WithdrawCoinBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}