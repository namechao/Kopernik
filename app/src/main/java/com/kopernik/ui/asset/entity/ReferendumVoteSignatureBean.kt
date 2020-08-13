package com.kopernik.ui.asset.entity

import android.os.Parcel
import android.os.Parcelable

class ReferendumVoteSignatureBean : Parcelable {
    var voteType:Int=-1 //1 赞同 2 反对 = 0
    var fee: String? = null
    var id = 0

    constructor() {}
    protected constructor(`in`: Parcel) {
        voteType = `in`.readInt()
        fee = `in`.readString()
        id = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(voteType!!)
        dest.writeString(fee)
        dest.writeDouble(id.toDouble())
    }

    companion object {
        val CREATOR: Parcelable.Creator<ReferendumVoteSignatureBean> =
            object : Parcelable.Creator<ReferendumVoteSignatureBean> {
                override fun createFromParcel(`in`: Parcel): ReferendumVoteSignatureBean? {
                    return ReferendumVoteSignatureBean(`in`)
                }

                override fun newArray(size: Int): Array<ReferendumVoteSignatureBean?> {
                    return arrayOfNulls(size)
                }
            }
    }
}