package com.nexxutech.fileuploader.service

import com.nexxutech.fileuploader.data.AccountRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class FileUploadService(
    @Value("\${file.upload-dir}") private val uploadDir: String,
    @Value("\${file.baseUrl}") private val fileBaseUrl: String,
    private val accountRepository: AccountRepository
) {

    fun uploadFile(file: MultipartFile, accountId: String): ResponseEntity<Map<String, String>> {
        val account = accountRepository.findByAccountId(accountId)

        if (account.isPresent){
            val uploadPath: Path = Paths.get(uploadDir)
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath)
            }

            // Generate a new filename (you can use a UUID or timestamp)
            val newFileName = accountId + "_" + getCurrentTimestamp() + "_" + file.originalFilename

            // Resolve the new filename into the target location
            val targetLocation = uploadPath.resolve(newFileName)

            file.inputStream.use { input ->
                Files.copy(input, targetLocation)
            }
            return ResponseEntity.status(200).body(mapOf("imageUrl" to fileBaseUrl+newFileName))
        } else{
            return ResponseEntity.status(400).body(mapOf("Bad Request" to "You don't have permission to upload"))
        }
    }

    private fun getCurrentTimestamp(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return now.format(formatter)
    }
}