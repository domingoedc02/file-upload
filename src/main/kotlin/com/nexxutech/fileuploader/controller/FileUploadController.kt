package com.nexxutech.fileuploader.controller

import com.nexxutech.fileuploader.service.FileUploadService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

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

    @GetMapping("/uploads/{fileName}")
    fun getFile(
        @PathVariable("fileName") fileName: String,
        @RequestParam("id") id: String,
        request: HttpServletRequest
    ): ResponseEntity<Any>{
        return fileUploadService.getFile(id, fileName, request)
    }
}