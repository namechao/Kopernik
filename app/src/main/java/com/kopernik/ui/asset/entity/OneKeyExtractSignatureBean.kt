package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class OneKeyExtractSignatureBean protected constructor(`in`: Parcel) :
    Parcelable {
    /**
     * unclaimedCount : 57158.17051211
     * rate : 0.17776
     * count : 16
     */
    var unclaimedCount: Double
    var rate: Double
    var count: Int
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeDouble(unclaimedCount)
        dest.writeDouble(rate)
        dest.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<OneKeyExtractSignatureBean> =
            object : Parcelable.Creator<OneKeyExtractSignatureBean> {
                override fun createFromParcel(`in`: Parcel): OneKeyExtractSignatureBean? {
                    return OneKeyExtractSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<OneKeyExtractSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }

    init {
        unclaimedCount = `in`.readDouble()
        rate = `in`.readDouble()
        count = `in`.readInt()
    }
}