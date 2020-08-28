package com.kopernik.ui.asset.entity

/**
 *
 * @ProjectName:    Kopernik
 * @Package:        com.kopernik.ui.asset.entity
 * @ClassName:      ReceiveRecordEntity
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/8/28 2:43 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/8/28 2:43 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class ReceiveRecordEntity(
    val datas: List<UtkReceiveRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UtkReceiveRecord(
    val amount: String,
    val createTime: Long
)
data class TransferRecordEntity(
    val datas: List<UtkTransferRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UtkTransferRecord(
    val amount: String,
    val createTime: Long,
    val uidReceive: String,
    val type: String,
    val operate: String,
    val rate: String,
    val remark: String
)