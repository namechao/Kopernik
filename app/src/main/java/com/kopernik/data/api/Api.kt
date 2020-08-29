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
    //http://xut.com:83  预上线测试  https://xut.com正式 172.25.187.54:8083
//    val baseUrl =
//        if (AppConfig.isDebug) "http://47.57.24.215:8089/mobile/v1/" else "https://uyt.com/mobile/v1/"
    val baseUrl =
        if (AppConfig.isDebug) "http://123.57.8.136:80/" else "https://kopernik.com/mobile/v1/"
    val appSearch =
        if (AppConfig.isDebug) "http://47.57.24.215:8086/#/appSearch/" else "https://kopernik.com/#/appSearch/"
    val home =
        if (AppConfig.isDebug) "http://47.57.24.215:8086/#/app" else "https://kopernik.com/#/app"
    val trade =
        if (AppConfig.isDebug) "http://47.57.24.215:8086/#/deal" else "https://kopernik.com/#/deal"
    val UserAgreementApi="http://123.57.8.136:8083/#/agreement"
    val UserNoticeApi=" http://123.57.8.136:8083/#/note?id="
    val InviteFriendsCodeApi="http://123.57.8.136:8083/#/register?inviteCode="
    //home
    val referendumList =  "register/referendumList" //公投列表
    val referendumDetails =
        "register/getReferendumDetails/" //公投详情 后拼接id
    val referendumVote =  "register/referendumVote" //公投投票

    //register
    val newMemon =
        "register/memon" //生成助记词http://120.27.250.5:8083/#/app
    val createdAccount =  "register/saveuser" //创建账户
    val importAccount =  "register/updateuser" //导入账户
    val modifyNick =  "register/updatelabel" //更新nick
    val modifyPass =  "register/updatepwd" //更新密码
    val exportMnemonic =  "register/getzjc" //导出助记词
    val contact =  "register/getcontacts" //联系人
    val modifyContact =  "register/saveaddress" //添加 更新 联系人
    val delContact =  "register/deladdress/" //删除联系人
    val nodeInviteAccount =
        "register/nodeRecommendationAccount" //节点邀请账户
    val nodeInviteNode =
        "register/nodeRecommendationNode" //节点邀请节点
    val nodeInviteNodeIntegral =
        "register/nodeRecommendScore" //节点邀请节点积分
    val accountInviteAccount =
        "register/recommendationAccount" //账户推荐账户
    val getLogoList =  "register/getNodeImgList" //节点logo 数据
    val checkAccountNode =
        "register/checknodeaccount" //检查节点是否退选
    val updateAccountNode =  "register/updatenodehash" //更新节点信息
    val checkUpdate =  baseUrl +"register/deploy" //版本更新
    val checkPw =  "register/checkPassword" //检查密码
    val checkMnemonic =  "register/checkuser" //判断助记词是否存在

    //node
    val balance =  "bnode/balance" //获取账户余额DNS
    val verifyNodelist =  "bnode/verifyNode" //正式节点
    val syncNodelist =  "bnode/syncNode" //候选节点
    val quitNodelist =  "bnode/quitNode" //退选节点

    /**
     * 注册节点：
     * 先判断用户状态是否符合创建节点的条件
     *
     * 进入页面后
     * 输入节点名，接口验证节点名是否存在
     * 输入节点地址或者节点名称后，接口验证节点是否存在
     * 网站地址输入后，正则验证是否为网址即可，可以为空
     *
     * 下一步---》页面显示的可用余额为上一页面返回的参数，kyCount
     * 输入密码确认签名-》接口注册，分配默认logo，跳转节点logo选择页面，返回新建节点地址
     * 如果用户选择logo后点击确定时 接口修改节点logo
     * 完成后返回到节点列表页面
     *
     */
    val checkRegisterInfo =  "bnode/registerinfo" //检查是否符合条件注册
    val checkNodeName =  "bnode/nodecheck" //检查节点名字是否已存在
    val checkNodeAddress =  "bnode/checknode/" //检查节点是否存在
    val nodeRegister =  "bnode/noderegister" //注册节点
    val voteList =  "bnode/getvotelist" //我的投票数据
    val vote =  "bnode/vote" //投票
    val voteInfo =  "bnode/voteinfo" //投票验证信息
    val voteHistory =  "bnode/voterecord" //投票操作记录
    val checkInfo =  "bnode/checkinfo" //提息 赎回 转投验证信息
    val redeem =  "bnode/redeem" //赎回
    val turn =  "bnode/switchTo" //转投
    val deduct =  "bnode/extract" //提息
    val timeLine =  "bnode/freerecord" //赎回 转投的时间轴
    val modifyNodeLogo =  "bnode/modifylogo" //修改节点logo
    val nodeQuitData =  "bnode/nodeQuery" //退选节点弹窗获取数据
    val nodeQuitSerach =  "bnode/fuzzyQuery" //退选节点弹窗模糊查询
    val oneClickRaiseInterestCheck =
        "bnode/checkextract" //一键提息检查
    val oneClickRaiseInterest =  "bnode/onekeyextract" //一键提息
    val nodeLeaderBoard =  "bnode/rankNodeList" //节点排行榜

    //asset
    val asset =  "asset/asset" //获取所有资产
    val assetDetails =  "asset/record" //资产详情
    val checkTransferStatus =  "asset/transferaccount" //转账判断
    val transfer =  "asset/savetransferaccount" //转账
    val checkWithdrawStatus =  "asset/cashwithdrawal" //提现判断
    val withdraw =  "asset/savecashbtc" //提现
    val checkMapStatus =  "asset/getmapping" //映射判断
    val checkDnsMapStatus =  "asset/getDnsMapping" //dns映射判断
    val map =  "asset/savemapping" //映射
    val dnsMap =  "asset/saveDnsMapping" //dns映射
    val cancelMap =  "asset/saveunmapping" //取消映射
    val dnsCancelMap =  "asset/saveDnsUnmapping" //dns取消映射
    val dnsCancelMapStatus =  "asset/getDnsUnMapping" //dns取消映射判断
    val unMapTimeLine =  "asset/mappingrecord" //取消映射时间轴
    val dnsUnMapTimeLine =  "asset/mappingDnsRecord" //dns取消映射时间轴
    val crossChainTx =  "asset/cashwithdrawalrecord" //跨链充提记录
    val cancelWithdraw =  "asset/cancelcash/" //取消提现
    val extract =  "asset/getGains" //获取收益
    val saveExtract =  "asset/saveGains" //提取收益

}