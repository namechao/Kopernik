package com.kopernik.data

import com.kopernik.data.network.AssetNetWork
import com.kopernik.data.network.HomeNetWork
import com.kopernik.data.network.NodeNetWork
import com.kopernik.data.repository.AssetRepository
import com.kopernik.data.repository.HomeRepository
import com.kopernik.data.repository.NodeRepository


object InjectorUtil {

    fun getHomeRepository() = HomeRepository.getInstance(
        HomeNetWork.getInstance()
    )

    fun getAssetRepository() = AssetRepository.getInstance(
        AssetNetWork.getInstance()
    )

    fun getNodeRepository() = NodeRepository.getInstance(
        NodeNetWork.getInstance()
    )

}