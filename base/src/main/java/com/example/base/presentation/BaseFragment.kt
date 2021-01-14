package com.example.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.base.nav.Navigation
import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.io.Serializable
import javax.inject.Inject

abstract class BaseFragment<VIEW_STATE : Serializable, VIEW_EVENT, PRESENTER : BasePresenter<VIEW_STATE, *, *, VIEW_EVENT>>(
        @LayoutRes val layoutId: Int
) : Fragment() {

    abstract val presenter: PRESENTER
    @Inject protected lateinit var navigation: Navigation

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutId, null, false)

    override fun onStart() {
        super.onStart()

        presenter
                .viewEvents
                .observeOn(SchedulersFactory.main)
                .subscribe(::handle)
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
    abstract fun handle(viewEvent: VIEW_EVENT)
}