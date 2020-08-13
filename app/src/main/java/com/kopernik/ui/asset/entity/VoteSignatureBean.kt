package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class VoteSignatureBean : Parcelable {
    var type = 0
    var voteValue: String? = null
    var fee: String? = null
    var nodeHash: String? = null
    var remark: String? = null
    var targetNodeHash: String? = null

    constructor() {}

    protected constructor(`in`: Parcel) {
        type = `in`.readInt()
        voteValue = `in`.readString()
        fee = `in`.readString()
        nodeHash = `in`.readString()
        remark = `in`.readString()
        targetNodeHash = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(type)
        dest.writeString(voteValue)
        dest.writeString(fee)
        dest.writeString(nodeHash)
        dest.writeString(remark)
        dest.writeString(targetNodeHash)
    }

    companion object {
        val CREATOR: Parcelable.Creator<VoteSignatureBean> =
            object : Parcelable.Creator<VoteSignatureBean> {
                override fun createFromParcel(`in`: Parcel): VoteSignatureBean? {
                    return VoteSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<VoteSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}