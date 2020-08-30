package com.kopernik.ui.setting.entity

class UpdateBean2 {

    var status = 0
    var data: DataBean? = null

    class DataBean {

        var deploy: DeployBean? = null

        class DeployBean {
            /**
            "id": 26,
            "versionName": "V1.2.1",
            "versionCode": 23,
            "versionDesc": "•【体验升级】优化细节，提升跨链体验。",
            "type": 1,
            "source": "android",
            "updateTime": 1593228497000,
            "url": "http://uyt-oss.oss-cn-hongkong.aliyuncs.com/app/uyt.apk"
             */
            var id = 0
            var versionName: String? = null
            var versionCode = 0
            var versionDesc: String? = null
            var type = 0
            var source: String? = null
            var updateTime: Any? = null
            var url: String? = null

        }
    }
}