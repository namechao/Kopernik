package com.kopernik.app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class AutoPollRecyclerView(
    context: Context?,
    attrs: AttributeSet?
) : RecyclerView(context!!, attrs) {
    var autoPollTask: AutoPollTask
    private var running=false //标示是否正在自动轮询 = false
    private var canRun=false //标示是否可以自动轮询,可在不需要的是否置false = false

    class AutoPollTask(reference: AutoPollRecyclerView) : Runnable {
        private val mReference: WeakReference<AutoPollRecyclerView>
        override fun run() {
            val recyclerView = mReference.get()
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(2, 2)
                recyclerView.postDelayed(
                    recyclerView.autoPollTask,
                    TIME_AUTO_POLL
                )
            }
        }

        //使用弱引用持有外部类引用->防止内存泄漏
        init {
            mReference = WeakReference(reference)
        }
    }

    //开启:如果正在运行,先停止->再开启
    fun start() {
        if (running) stop()
        canRun = true
        running = true
        postDelayed(autoPollTask, TIME_AUTO_POLL)
    }

    fun stop() {
        running = false
        removeCallbacks(autoPollTask)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> if (running) stop()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> if (canRun) start()
        }
        return  false //注释掉onTouchEvent()方法里面的stop和start方法，则列表自动滚动且不可触摸
//        return super.onTouchEvent(e)
    }

    companion object {
        private const val TIME_AUTO_POLL: Long = 16
    }

    init {
        autoPollTask = AutoPollTask(this)
    }
}