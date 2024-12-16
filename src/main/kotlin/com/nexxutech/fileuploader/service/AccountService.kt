package com.nexxutech.fileuploader.service

import com.nexxutech.fileuploader.controller.CreateAccountDto
import com.nexxutech.fileuploader.data.AccountEntity
import com.nexxutech.fileuploader.data.AccountRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class AccountService(
    private val accountRepository: AccountRepository,

) {
    @Value("\${file.secretKey}")
    private val secretKeyToken: String = ""

    fun createAccount (dto: CreateAccountDto): ResponseEntity<Map<String, String>>{
        try {
            val newId = generateUUID()
            val newAccount = AccountEntity()
            newAccount.accountId = newId
            newAccount.name = dto.name
            newAccount.token = generateToken(newId, secretKeyToken)
            accountRepository.save(newAccount)
            return ResponseEntity.status(200).body(mapOf("OK" to "Created Successfully"))
        } catch (e: Exception){
            return ResponseEntity.status(500).body(mapOf("error" to e.message.toString()))
        }
    }

    private fun generateToken(accountId: String, secretKey: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
        mac.init(secretKeySpec)

        val timestamp = System.currentTimeMillis() / 1000 // Current time in seconds
        val payload = "$accountId|$timestamp" // Token includes accountId and timestamp
        val signature = mac.doFinal(payload.toByteArray())

        // Combine payload and signature, Base64 encode it
        return Base64.getEncoder().encodeToString("$payload.${String(signature)}".toByteArray())
    }

    private fun generateUUID(): String {
        return UUID.randomUUID().toString() // Generates a random UUID and converts it to a String
    }
}