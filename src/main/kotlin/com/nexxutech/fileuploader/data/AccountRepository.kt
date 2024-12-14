package com.nexxutech.fileuploader.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: JpaRepository<AccountEntity, String> {

    fun findByAccountId(accountId: String): Optional<AccountEntity?>
}