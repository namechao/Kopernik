package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class MapSignatureBean : Parcelable {
    var mapType = 0
    var value: String? = null
    var fee: String? = null
    var remark: String? = null
    var chainType = 0

    constructor() {}
    protected constructor(`in`: Parcel) {
        mapType = `in`.readInt()
        value = `in`.readString()
        fee = `in`.readString()
        remark = `in`.readString()
        chainType = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(mapType)
        dest.writeString(value)
        dest.writeString(fee)
        dest.writeString(remark)
        dest.writeInt(chainType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<MapSignatureBean> =
            object : Parcelable.Creator<MapSignatureBean> {
                override fun createFromParcel(`in`: Parcel): MapSignatureBean? {
                    return MapSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<MapSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}