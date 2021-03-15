package com.example.user.auth.data.dto

import com.example.base.testutils.TestUtils
import moe.banana.jsonapi2.Document
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

private const val REGISTER_RESPONSE = """{"data":{"id":"34db2713-0664-48fb-9be4-9c8c0e850051","type":"account","attributes":{"email":"barrr6611@cz.cz"},"relationships":{"session":{"data":{"id":"04f40a15-40be-4579-b85b-85d572d2f785","type":"session"}},"user":{"data":{"id":"f46c6d10-4216-4bb1-abc2-876085fc90aa","type":"user"}}}},"included":[{"id":"f46c6d10-4216-4bb1-abc2-876085fc90aa","type":"user","attributes":{"first_name":null,"last_name":null}},{"id":"04f40a15-40be-4579-b85b-85d572d2f785","type":"session","attributes":{"access_token":"access-token","refresh_token":"refresh-token"},"relationships":{"account":{"data":null},"user":{"data":null}}}]}"""

class AccountDtoTest {
    @Test
    fun `given valid jsonApiMosh when register response is parsed then valid AccountDto is returned`() {
        // given
        val moshi = TestUtils
                .jsonApiMoshi(
                        AccountDto::class.java,
                        UserDto::class.java,
                        SessionDto::class.java)

        // when
        val accountDto = moshi.adapter(Document::class.java).fromJson(REGISTER_RESPONSE)?.asObjectDocument<AccountDto>()?.get()

        // then
        assertThat(accountDto).isNotNull

        assertThat(accountDto?.email).isNotBlank
        assertThat(accountDto?.getTokens()?.accessToken).isNotBlank
        assertThat(accountDto?.getTokens()?.refreshToken).isNotBlank
    }

}