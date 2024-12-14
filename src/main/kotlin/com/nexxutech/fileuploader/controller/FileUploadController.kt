package com.nexxutech.fileuploader.controller

import com.nexxutech.fileuploader.service.FileUploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class FileUploadController(
    private val fileUploadService: FileUploadService
) {

    @PostMapping("/api/upload/{id}")
    fun uploadImage(@RequestParam("file") file: MultipartFile, @PathVariable("id") id: String): ResponseEntity<Map<String, String>> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(mapOf("Bad Request" to "File is missing"))
        }
        return fileUploadService.uploadFile(file, id)
    }
}