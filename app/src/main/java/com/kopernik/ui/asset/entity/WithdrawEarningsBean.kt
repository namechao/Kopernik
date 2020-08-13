package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable


class WithdrawEarningsBean : Parcelable {
    var title: String? = ""
    var oneLineLeft: String? = ""
    var oneLineRight: String? = ""
    var twoLineLeft: String? = ""
    var twoLineRight: String? = ""
    var bottomButton : String? = ""

    constructor() {}
    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        oneLineLeft = `in`.readString()
        oneLineRight = `in`.readString()
        twoLineLeft = `in`.readString()
        twoLineRight = `in`.readString()
        bottomButton = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(oneLineLeft)
        dest.writeString(oneLineRight)
        dest.writeString(twoLineLeft)
        dest.writeString(twoLineRight)
        dest.writeString(bottomButton)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<WithdrawEarningsBean> =
            object : Parcelable.Creator<WithdrawEarningsBean> {
                override fun createFromParcel(`in`: Parcel): WithdrawEarningsBean? {
                    return WithdrawEarningsBean(`in`)
                }

                override fun newArray(size: Int): Array<WithdrawEarningsBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}