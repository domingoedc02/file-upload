package com.nexxutech.fileuploader.controller

import com.nexxutech.fileuploader.data.AccountEntity
import com.nexxutech.fileuploader.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping("/api/account")
    fun createAccount(@RequestBody dto: CreateAccountDto): ResponseEntity<Map<String, String>>{
        return accountService.createAccount(dto)
    }
}

data class CreateAccountDto(
    val name: String
)