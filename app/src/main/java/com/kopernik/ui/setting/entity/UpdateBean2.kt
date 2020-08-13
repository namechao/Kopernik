package com.kopernik.ui.setting.entity

class UpdateBean2 {
    /**
     * status : 200
     * data : {"deploy":{"id":14,"versionName":"v2.0.0","versionCode":11,"versionDesc":"nisafhas \\n fs  fsaf \\n","type":1,"source":"android","updateTime":null,"url":""}}
     */
    var status = 0
    var data: DataBean? = null

    class DataBean {
        /**
         * deploy : {"id":14,"versionName":"v2.0.0","versionCode":11,"versionDesc":"nisafhas \\n fs  fsaf \\n","type":1,"source":"android","updateTime":null,"url":""}
         */
        var deploy: DeployBean? = null

        class DeployBean {
            /**
             * id : 14
             * versionName : v2.0.0
             * versionCode : 11
             * versionDesc : nisafhas \n fs  fsaf \n
             * type : 1
             * source : android
             * updateTime : null
             * url :
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