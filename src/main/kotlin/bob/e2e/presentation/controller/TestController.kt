package bob.e2e.presentation.controller

import bob.e2e.domain.service.KeypadService
import bob.e2e.domain.service.KeypadService.Companion.getHash
import bob.e2e.presentation.dto.GetKeypadResDto
import bob.e2e.presentation.dto.Submitkeypad
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Logger

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate



@RequestMapping("api")
@RestController
class TestController {
    private val logger = LoggerFactory.getLogger(KeypadService::class.java)
    private val keymap: MutableMap<String, Map<String, String>> = mutableMapOf<String, Map<String, String>>()
    // Create
    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping("/create")
    fun createKeypad(): GetKeypadResDto {
        val pubKey = KeypadService.getPubKey()

        val keyMapInfo: Map<String, String> = KeypadService.getKeymap()
        val keyOrder: List<String> = KeypadService.getShuffleKeyNum()
        val keypadImage: String = KeypadService.getKeypadImage(keyOrder)

        val nowTime = LocalDateTime.now().toString()
        println("gen timestamp: $nowTime")
        val keypadId = UUID.randomUUID().toString()

        this.keymap[keypadId] = keyMapInfo

        return GetKeypadResDto(
            keypadId = keypadId,
            pubKey = pubKey,
            timeStamp = nowTime,
            keypadImage = "data:image/png;base64,$keypadImage",
            keyData = keyOrder.map { keyMapInfo[it]!! },
            hash = getHash(keypadId, nowTime)
        )
    }

    @CrossOrigin(origins = ["http://localhost:3000"])
    @PostMapping("/submit")
    fun submitKeypad(@RequestBody submitKeypad: Submitkeypad): String {
        val keypadId = submitKeypad.keypadId
        val timeStamp = submitKeypad.timeStamp
        val input = submitKeypad.input

        println("get timestamp: $timeStamp")

        println("submitKeypad: ${getHash(keypadId, timeStamp)}")
        println("submitKeypad: ${submitKeypad.hash}")
        if (submitKeypad.hash != getHash(keypadId, timeStamp)) {
            throw Exception("hash not match")
        }

        println("input: $input")
        println("keymap: ${this.keymap[keypadId]}")

        // send post http://146.56.119.112:8081/auth Content-Type:application/json  {"userInput": String, "keyHashMap": Map<String, String>}
        data class SendData(
            val userInput: String,
            val keyHashMap: Map<String, String>
        )

        val payload = SendData(input, this.keymap[keypadId]!!)
        val baseUrl = "http://146.56.119.112:8081/auth"

        val restTemplate = RestTemplate()

        val headers = HttpHeaders().apply {
            set("Content-Type", "application/json")
        }

        val requestEntity = HttpEntity(payload, headers)

        val response: ResponseEntity<String> = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String::class.java)
        val responseBody = response.body ?: "No response body"

        println("Response: $responseBody")
        return responseBody
    }

    // Read

    // Update

    // Delete
}