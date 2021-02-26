package com.example.base

import com.example.base.presentation.BasePresenter
import com.example.base.testutils.TestSchedulersFactory
import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import java.io.Serializable

class BasePresenterTest {
    lateinit var UT: TestPresenter

    @Before
    fun setup() {
        SchedulersFactory.set(TestSchedulersFactory())
        UT = TestPresenter()
    }

    // VIEW_STATE tests
    @Test
    fun `given state exists, reduceViewState is given valid partialState, produces valid output`() {
        // given
        val to = UT.viewState.test()

        // when
        UT.acceptIntent(TestIntent.Add(5))
        // then
        to.assertValueAt(1) {
            it.count == 5
        }

        // when
        UT.acceptIntent(TestIntent.Sub(2))
        // then
        to.assertValueAt(2) {
            it.count == 3
        }
    }

    // VIEW_EVENT tests
    @Test
    fun `when event is published, subscriber is notified`() {
        // given
        val to = UT.viewEvents.test()

        // when
        UT.publishTestEvent(TestEvent("one"))

        // then
        to.assertValueCount(1)
    }

    @Test
    fun `given events emitted before subscribe, subscriber is notified`() {
        // given: noop

        // when
        UT.publishTestEvent(TestEvent("one"))
        UT.publishTestEvent(TestEvent("two"))
        val to = UT.viewEvents.test()

        // then
        to.assertValueCount(2)
    }

    @Test
    fun `given subscribe-dispose-emit-subscribe scenario, subscriber receives missing events`() {
        // given
        val to = UT.viewEvents.test()

        // when
        UT.publishTestEvent(TestEvent("one"))
        UT.publishTestEvent(TestEvent("two"))
        to.assertValueCount(2)
        to.dispose()

        UT.publishTestEvent(TestEvent("three"))
        UT.publishTestEvent(TestEvent("four"))
        UT.publishTestEvent(TestEvent("five"))

        val to2 = UT.viewEvents.test()

        // then
        to2.assertValueCount(3)
    }
}

data class TestViewState(val count: Int) : Serializable
sealed class TestIntent {
    data class Add(val count: Int) : TestIntent()
    data class Sub(val count: Int) : TestIntent()
}

sealed class TestPartialState {
    data class Add(val count: Int) : TestPartialState()
    data class Sub(val count: Int) : TestPartialState()
}

data class TestEvent(val what: String)

class TestPresenter : BasePresenter<TestViewState, TestPartialState, TestIntent, TestEvent>(TestViewState(0)) {
    override fun provideViewIntents(): Flowable<TestPartialState> {
        return intentProcessor
            .map {
                when (it) {
                    is TestIntent.Add -> TestPartialState.Add(it.count)
                    is TestIntent.Sub -> TestPartialState.Sub(it.count)
                }
            }
    }

    override fun reduceViewState(previousState: TestViewState, partialState: TestPartialState): TestViewState {
        return when (partialState) {
            is TestPartialState.Add -> TestViewState(previousState.count + partialState.count)
            is TestPartialState.Sub -> TestViewState(previousState.count - partialState.count)
        }
    }

    fun publishTestEvent(event: TestEvent) {
        publishEvent(event)
    }
}
