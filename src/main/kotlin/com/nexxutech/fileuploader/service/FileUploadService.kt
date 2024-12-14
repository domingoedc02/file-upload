package com.nexxutech.fileuploader.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileUploadService(
    @Value("\${file.upload-dir}") private val uploadDir: String
) {

    fun uploadFile(file: MultipartFile): String {
        val uploadPath: Path = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }
        val targetLocation = uploadPath.resolve(file.originalFilename!!)
        file.inputStream.use { input ->
            Files.copy(input, targetLocation)
        }
        return uploadDir+targetLocation.toUri().toString()
    }
}