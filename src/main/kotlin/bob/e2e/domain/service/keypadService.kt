package bob.e2e.domain.service

import org.springframework.core.io.ClassPathResource
import java.util.*

class KeypadService {

    companion object {
        fun getShuffleKeyNum(): List<Int> {
            val shuffleKeyNum = mutableListOf<Int>()
            for (i in 0..12) {
                shuffleKeyNum.add(i)
            }
            shuffleKeyNum.shuffle()
            return shuffleKeyNum
        }

        fun getEncodeImageToBase64(): List<String> {
            val imageList: MutableList<String> = mutableListOf()
            for (i in 0..10) {
                System.out.println("resources/_$i.png")
                imageList.add(
                    Base64.getEncoder().encodeToString(
                        ClassPathResource("resources/_$i.png").inputStream.readBytes()
                    )
                )
            }

            return imageList
        }
    }

}