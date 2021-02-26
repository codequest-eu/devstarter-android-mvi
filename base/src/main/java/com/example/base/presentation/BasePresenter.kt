package com.example.base.presentation

import androidx.lifecycle.ViewModel
import com.example.base.utils.SchedulersFactory
import hu.akarnokd.rxjava3.subjects.UnicastWorkSubject
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE, INTENT, VIEW_EVENT>(
    initialState: VIEW_STATE
) : ViewModel() {
    protected val intentProcessor: FlowableProcessor<INTENT> = PublishProcessor.create()

    private val viewStateSubject: BehaviorSubject<VIEW_STATE> = BehaviorSubject.create()
    val viewState: Observable<VIEW_STATE> get() = viewStateSubject

    private val viewEventsSubject: UnicastWorkSubject<VIEW_EVENT> = UnicastWorkSubject.create()
    val viewEvents: Observable<VIEW_EVENT> get() = viewEventsSubject

    private val disposable: Disposable
    private val reduceScheduler = SchedulersFactory.newExecutor

    init {
        @Suppress("LeakingThis")
        disposable = subscribeToViewIntents(initialState, provideViewIntents())
    }

    override fun onCleared() {
        disposable.dispose()
    }

    protected fun publishEvent(event: VIEW_EVENT) {
        viewEventsSubject.onNext(event)
    }

    fun acceptIntent(intent: INTENT) {
        intentProcessor.onNext(intent)
    }

    private fun subscribeToViewIntents(initialState: VIEW_STATE, flowables: Flowable<PARTIAL_VIEW_STATE>) =
        flowables
            .observeOn(reduceScheduler)
            .scan(initialState, this::reduceViewState)
            .observeOn(SchedulersFactory.main)
            .subscribe({ viewStateSubject.onNext(it) }, { it.printStackTrace() })

    protected abstract fun provideViewIntents(): Flowable<PARTIAL_VIEW_STATE>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
