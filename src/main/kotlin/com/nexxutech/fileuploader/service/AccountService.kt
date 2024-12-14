package com.nexxutech.fileuploader.service

import com.nexxutech.fileuploader.controller.CreateAccountDto
import com.nexxutech.fileuploader.data.AccountEntity
import com.nexxutech.fileuploader.data.AccountRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    fun createAccount (dto: CreateAccountDto): ResponseEntity<Map<String, String>>{
        try {
            val newAccount = AccountEntity()
            newAccount.accountId = generateUUID()
            newAccount.name = dto.name
            accountRepository.save(newAccount)
            return ResponseEntity.status(200).body(mapOf("OK" to "Created Successfully"))
        } catch (e: Exception){
            return ResponseEntity.status(500).body(mapOf("error" to e.message.toString()))
        }
    }

    private fun generateUUID(): String {
        return UUID.randomUUID().toString() // Generates a random UUID and converts it to a String
    }
}