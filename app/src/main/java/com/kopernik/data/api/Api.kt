package com.kopernik.data.api

import com.kopernik.app.config.AppConfig

/**
 *
 * @ProjectName:    Uyt
 * @Package:        com.kopernik.app.network
 * @ClassName:      api
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/15 4:50 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/15 4:50 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object Api {
    val baseUrl =
        if (AppConfig.isDebug) "http://123.57.8.136:80/" else "https://kopernik.network/"
    val UserAgreementApi=  if (AppConfig.isDebug)  "http://123.57.8.136:8083/#/agreement" else "https://kopernik.network/#/agreement"
    val UserNoticeApi=  if (AppConfig.isDebug) "http://123.57.8.136:8083/#/note?id=" else "https://kopernik.network/#/note?id="
    val InviteFriendsCodeApi= if (AppConfig.isDebug) "http://123.57.8.136:8083/#/register?inviteCode=" else "https://kopernik.network/#/register?inviteCode="
    val trade = if (AppConfig.isDebug) "http://47.57.24.215:8086/#/deal" else "https://trade.kopernik.network/#/deal"
    val checkUpdate =  baseUrl +"user/deploy" //版本更新
}