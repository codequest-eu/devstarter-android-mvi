package com.example.base.presentation

import androidx.fragment.app.Fragment
import com.example.base.nav.Navigation
import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.io.Serializable
import javax.inject.Inject

abstract class BaseFragment<
        VIEW_STATE : Serializable,
        VIEW_EVENT,
        PRESENTER : BasePresenter<VIEW_STATE, *, *, VIEW_EVENT>>()
    : Fragment() {

    abstract val presenter: PRESENTER

    @Inject
    protected lateinit var navigation: Navigation
    protected val compositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        presenter
                .viewEvents
                .observeOn(SchedulersFactory.main)
                .subscribe(::handleEvent)
                .addTo(compositeDisposable)

        presenter
                .viewState
                .distinctUntilChanged()
                .observeOn(SchedulersFactory.main)
                .subscribe(::render)
                .addTo(compositeDisposable)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    abstract fun render(viewState: VIEW_STATE)
    abstract fun handleEvent(viewEvent: VIEW_EVENT)
}
