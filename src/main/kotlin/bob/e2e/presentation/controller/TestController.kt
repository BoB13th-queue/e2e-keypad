package bob.e2e.presentation.controller

import bob.e2e.domain.model.Keypad
import bob.e2e.domain.service.KeypadService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("keypad")
@RestController
class TestController {
    // Create
    @PostMapping("/create")
    fun createKeypad() {
        val shuffledNumList: List<Int> = KeypadService.getShuffleKeyNum()
        val keypadList = KeypadService.getEncodeImageToBase64()

        println("keypad: $shuffledNumList, $keypadList")
        val keypad = Keypad(
            id = 1,
            keypadList = shuffledNumList.mapIndexed { index, i ->
                Pair(i.toString(), keypadList[index])
            },
            hash = "hash"
        )


    }

    // Read

    // Update

    // Delete
}