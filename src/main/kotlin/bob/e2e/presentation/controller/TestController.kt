package bob.e2e.presentation.controller

import bob.e2e.utils.logger.Logger
import java.security.MessageDigest
import bob.e2e.domain.service.KeypadService
import bob.e2e.presentation.dto.GetKeypadResDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


@RequestMapping("keypad")
@RestController
class TestController {
    // Create
    @GetMapping("/create")
    fun createKeypad(): GetKeypadResDto {
        val keyMapInfo: Map<String, String> = KeypadService.getKeymap()
        val keyOrder: List<String> = KeypadService.getShuffleKeyNum()
        val keypadImage: String = KeypadService.getKeypadImage(keyOrder)

        val nowTime = LocalDateTime.now()
        val keypadId = 1
        val pubKey = KeypadService.getPubKey()
        val secretKey = "secretKey" // This should be stored securely

        val bytes = "$keypadId $nowTime $secretKey".toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        val hash = digest.fold("") { str, it -> str + "%02x".format(it) }

        return GetKeypadResDto(
            keypadId = keypadId,
            pubKey = pubKey,
            keypadImage = "data:image/png;base64,$keypadImage",
            keyData = keyOrder.map { keyMapInfo[it]!! },
            hash = hash
        )
    }

    // Read

    // Update

    // Delete
}