package com.example.appName.presentation.base

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.appName.BuildConfig
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>>(
        @LayoutRes val layoutId: Int
) : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var presenter: PRESENTER

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    var savedInstanceState: Bundle? = null

    private var disposable: Disposable? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        inflateView()
        subscribeToViewState()

        if (!BuildConfig.DEBUG) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val viewState = presenter.currentViewState
        outState.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, viewState)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    private fun subscribeToViewState() {
        disposable = presenter.stateObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
    }

    private fun inflateView() {
        setContentView(layoutId)
    }

    abstract fun render(viewState: VIEW_STATE)

    companion object {
        const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"
    }
}
