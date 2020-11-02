package com.kopernik.app.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Environment
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import dev.utils.common.encrypt.MD5Utils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * 获取设备ID
 * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
 */
object DeviceIDUtil {
    /**
     * 渠道标志为：
     * 1，andriod（a）
     *
     *
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    fun getDeviceId(context: Context): String {
        val deviceId = StringBuilder()
        // 渠道标志
        deviceId.append("a")
        try {
            //wifi mac地址
            val wifi =
                context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            val wifiMac = info.macAddress
            if (!TextUtils.isEmpty(wifiMac)) {
                deviceId.append("wifi")
                deviceId.append(wifiMac)
                Log.e("getDeviceId : ", deviceId.toString())
                return MD5Utils.md5Upper(deviceId.toString())
            }
            //IMEI（imei）
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }
            val imei = tm.deviceId
            if (!TextUtils.isEmpty(imei)) {
                deviceId.append("imei")
                deviceId.append(imei)
                Log.e("getDeviceId : ", deviceId.toString())
                return MD5Utils.md5Upper(deviceId.toString())
            }
            //序列号（sn）
            val sn = tm.simSerialNumber
            if (!TextUtils.isEmpty(sn)) {
                deviceId.append("sn")
                deviceId.append(sn)
                Log.e("getDeviceId : ", deviceId.toString())
                return MD5Utils.md5Upper(deviceId.toString())
            }
            //如果上面都没有， 则生成一个id：随机码
            val uuid = getUUID(context)
            if (!TextUtils.isEmpty(uuid)) {
                deviceId.append("id")
                deviceId.append(uuid)
                Log.e("getDeviceId : ", deviceId.toString())
                return MD5Utils.md5Upper(deviceId.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            deviceId.append("id").append(getUUID(context))
        }
        //        LogUtil.e("getDeviceId : ", deviceId.toString());
        return MD5Utils.md5Upper(deviceId.toString())
    }

    /**
     * 得到全局唯一UUID
     */
    fun getUUID(context: Context): String? {
        val mShare =
            context.getSharedPreferences("uuid", Context.MODE_PRIVATE)
        var uuid: String? = null
        if (mShare != null) {
            uuid = mShare.getString("uuid", "")
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString()
            mShare!!.edit().putString("uuid", uuid).commit()
        }
        return uuid
    }
    private val SD_PATH =
        Environment.getExternalStorageDirectory().path + "/保存二维码/"
     fun saveBitmap2file(bmp: Bitmap, context:Context) {


        var savePath="";
        var fileName = generateFileName() + ".JPEG";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
            return ;
        }
        var filePic =  File(savePath + fileName);
        try {
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            var fos =  FileOutputStream(filePic);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "保存成功,位置:" + filePic.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch ( e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    filePic.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast( Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + savePath+fileName)))


    }
    private fun generateFileName(): String? {
        return UUID.randomUUID().toString()
    }

}