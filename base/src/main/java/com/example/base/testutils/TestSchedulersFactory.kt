package com.example.base.testutils

import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulersFactory(
    override val io: Scheduler = Schedulers.trampoline(),
    override val main: Scheduler = Schedulers.trampoline(),
    override val newExecutor: Scheduler = Schedulers.trampoline()
) :
    SchedulersFactory
