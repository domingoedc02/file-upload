package com.nexxutech.fileuploader.controller

import com.nexxutech.fileuploader.service.FileUploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class FileUploadController(
    private val fileUploadService: FileUploadService
) {

    @PostMapping("/api/upload")
    fun uploadImage(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is missing")
        }
        val fileUrl = fileUploadService.uploadFile(file)
        return ResponseEntity.ok(fileUrl)
    }
}