package com.example.base.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulersFactory {

    val io: Scheduler
    val main: Scheduler
    val newExecutor: Scheduler

    companion object : SchedulersFactory {
        private var instance: SchedulersFactory = Default

        object Default : SchedulersFactory {
            override val io
                get() = Schedulers.io()
            override val main
                get() = AndroidSchedulers.mainThread()
            override val newExecutor: Scheduler
                get() = Schedulers.newThread()
        }

        // for test purposes
        fun set(factory: SchedulersFactory) {
            instance = factory
        }

        override val io
            get() = instance.io
        override val main
            get() = instance.main
        override val newExecutor: Scheduler
            get() = instance.newExecutor
    }
}
