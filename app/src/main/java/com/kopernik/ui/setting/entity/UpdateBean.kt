package com.kopernik.ui.setting.entity

class UpdateBean {
    var deploy: DeployBean? = null
    var notice: NoticeBean? = null

    class DeployBean {
        /**
         * id : 13
         * versionName : 1.0.1
         * versionCode : null
         * versionDesc : •【程序员小哥哥犯了一个错误，把上个版本钱包的USDT提现地址限制太多，这位小哥哥已经被拖出去打PP，现在已经恢复，大家赶紧更新吧】\n•【Bug修复】修复已知问题\n•【性能优化】优化性能，提升页面加载速度
         * type : 1
         * source : ios
         * updateTime : 1567220497000
         * url : itms-services://?action=download-manifest&url=https://xut-dns.oss-cn-hangzhou.aliyuncs.com/xut-download/manifest.plist
         */
        var id = 0
        var versionName: String? = null
        var versionCode = 0
        var versionDesc: String? = null
        var type = 0
        var source: String? = null
        var updateTime: Long = 0
        var url: String? = null

    }

    class NoticeBean {
        /**
         * id : 2
         * title : test
         * type : 1
         * memo : null
         * createTime : 1567577146000
         * startTime : 1567490737000
         * endTime : 1568268341000
         * content : test
         */
        var id = 0
        var title: String? = null
        var type = 0
        var memo: Any? = null
        var createTime: Long = 0
        var startTime: Long = 0
        var endTime: Long = 0
        var content: String? = null

    }
}