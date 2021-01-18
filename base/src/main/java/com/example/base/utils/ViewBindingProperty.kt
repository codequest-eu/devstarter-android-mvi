package com.example.base.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingProperty<T : ViewBinding>(
        private val fragment: Fragment,
        private val factory: (View) -> T) : ReadOnlyProperty<Fragment, T> {
    private var instance: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        instance?.let {
            return it
        }

        fragment
                .viewLifecycleOwner
                .lifecycle
                .addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun onDestroy() {
                        instance = null
                    }
                })

        return factory(fragment.requireView()).also {
            instance = it
        }
    }
}
