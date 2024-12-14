package com.nexxutech.fileuploader.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileUploadService(
    @Value("\${file.upload-dir}") private val uploadDir: String
) {

    fun uploadFile(file: MultipartFile): String {
        val uploadPath = Paths.get(uploadDir)
        Files.createDirectories(uploadPath)

        val filePath = uploadPath.resolve(file.originalFilename!!)
        file.inputStream.use { input ->
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING)
        }

        // Return the URL where the file can be accessed
        return "/uploads/${file.originalFilename}"
    }
}