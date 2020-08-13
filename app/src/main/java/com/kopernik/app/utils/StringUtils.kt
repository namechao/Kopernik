package com.kopernik.app.utils

import java.io.*

object StringUtils {
    fun isEmpty(str: String?): Boolean {
        return str?.isEmpty() ?: true
    }

    fun getBytes(filePath: String?): ByteArray? {
        var buffer: ByteArray? = null
        try {
            val file = File(filePath)
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream(1000)
            val b = ByteArray(1000)
            var n: Int
            while (fis.read(b).also { n = it } != -1) {
                bos.write(b, 0, n)
            }
            fis.close()
            bos.close()
            buffer = bos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return buffer
    }

    fun getFile(
        bfile: ByteArray?,
        filePath: String,
        fileName: String
    ) {
        var bos: BufferedOutputStream? = null
        var fos: FileOutputStream? = null
        var file: File? = null
        try {
            val dir = File(filePath)
            if (!dir.exists() && !dir.isDirectory) { // �ж��ļ�Ŀ¼�Ƿ����?
                dir.mkdirs()
            }
            file = File(filePath + "\\" + fileName)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            bos.write(bfile)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bos != null) {
                try {
                    bos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
    }

    /**
     * 是否包大写字母
     * @param str
     * @return
     */
    fun isContainUpperCase(str: String): Boolean {
        var isUpperCase = false
        for (i in 0 until str.length) {
            if (Character.isUpperCase(str[i])) {
                isUpperCase = true
            }
        }
        return isUpperCase
    }

    /**
     * 是否包小写字母
     * @param str
     * @return
     */
    fun isContainLowerCase(str: String): Boolean {
        var isLowerCase = false
        for (i in 0 until str.length) {
            if (Character.isLowerCase(str[i])) {
                isLowerCase = true
            }
        }
        return isLowerCase
    }

    /**
     * 是否包含字母
     * @param str
     * @return
     */
    fun isContainAbc(str: String): Boolean {
        var isLowerCase = false
        var isUpperCase = false
        for (i in 0 until str.length) {
            if (Character.isLowerCase(str[i])) {
                isLowerCase = true
            } else if (Character.isUpperCase(str[i])) {
                isUpperCase = true
            }
        }
        return isLowerCase || isUpperCase
    }

    /**
     * 是否包含大小写字母
     * @param str
     * @return
     */
    fun isContainBothAbc(str: String): Boolean {
        var isLowerCase = false
        var isUpperCase = false
        for (i in 0 until str.length) {
            if (Character.isLowerCase(str[i])) {
                isLowerCase = true
            } else if (Character.isUpperCase(str[i])) {
                isUpperCase = true
            }
        }
        return isLowerCase && isUpperCase
    }

    /**
     * 是否包含数字
     * @param str
     * @return
     */
    fun isContainNumber(str: String): Boolean {
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {   //用char包装类中的判断数字的方法判断每一个字符
                return true
            }
        }
        return false
    }

    /**
     * 规则：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    fun isContainAll(str: String): Boolean {
        var isDigit = false //定义一个boolean值，用来表示是否包含数字
        var isLowerCase = false //定义一个boolean值，用来表示是否包含字母
        var isUpperCase = false
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true
            } else if (Character.isLowerCase(str[i])) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true
            } else if (Character.isUpperCase(str[i])) {
                isUpperCase = true
            }
        }
        val regex = "^[a-zA-Z0-9]+$"
        return isDigit && isLowerCase && isUpperCase && str.matches(Regex(regex))
    }

    fun listToString(list: List<*>, separator: String?): String {
        val sb = StringBuilder()
        for (i in list.indices) {
            sb.append(list[i]).append(separator)
        }
        return if (list.isEmpty()) "" else sb.toString()
    }
}