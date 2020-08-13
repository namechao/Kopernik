package com.kopernik.common

interface SignatureType {
    companion object {
        const val NODE_REGISTER = 1
        const val VOTE = 2
        const val REDEEM = 3 //赎回
        const val TURN = 4 //转投
        const val DEDUCT = 5 //提息
        const val TRANSFER = 6 //转账
        const val WITHDRAW = 7 //提现
        const val MAP = 8 //映射
        const val UNMAP = 9 //取消映射
        const val ONE_KEY = 10 //一键提息
        const val REFERENDUM = 11 //公投投票
    }
}
