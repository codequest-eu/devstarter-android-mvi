package com.example.user.presentation.register

import com.example.user.auth.usecase.RegisterUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.Before

//internal class RegisterUseCaseTd: RegisterUseCase {
//
//    var success = true
//
//    override fun execute(username: String, password: String): Single<RegisterUseCase.Result> {
//        return if (success) {
//             Single.just(RegisterUseCase.Result.Success)
//        } else {
//            Single.just(RegisterUseCase.Result.Failure)
//        }
//    }
//}
//
//internal class RegisterPresenterTest {
//
//    lateinit var testSubject: RegisterPresenter
//
//    @Before
//    fun setup() {
//        testSubject = RegisterPresenter(RegisterViewState(), )
//    }
//}