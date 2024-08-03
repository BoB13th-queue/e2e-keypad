package bob.e2e.domain.service

import bob.e2e.utils.logger.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.util.*
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


class KeypadService {
    companion object {
        private val logger = LoggerFactory.getLogger(KeypadService::class.java)

        fun getShuffleKeyNum(): List<String> {
            val shuffleKeyNum = mutableListOf<String>(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ", " "
            )
            shuffleKeyNum.shuffle()
            return shuffleKeyNum
        }

        fun getKeymap(): Map<String, String> {
            val inputList = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ")
            val map: Map<String, String> = inputList.associateWith { UUID.randomUUID().toString() }

            return map.toMap()
        }


        private fun getByteArrayImage(): Map<String, ByteArray> {
            val imageList: MutableMap<String, ByteArray> = mutableMapOf<String, ByteArray>()

            // get Image from Resource
            for (i in 0..9) {
                val imgFile = ClassPathResource("_$i.png")
                imageList[i.toString()] = imgFile.inputStream.readBytes()
            }

            imageList[" "] = ClassPathResource("_blank.png").inputStream.readBytes()

            return imageList.toMap()
        }

        fun getKeypadImage(
                            imageOrder: List<String>,
                            cols: Int = 4,
                            rows: Int = 3): String {
            val imageMap = getByteArrayImage()

            val firstImage = ImageIO.read(ByteArrayInputStream(imageMap[imageOrder.first()]))
            val singleWidth = firstImage.width
            val singleHeight = firstImage.height

            // 전체 그리드 이미지의 크기 계산
            val totalWidth = singleWidth * cols
            val totalHeight = singleHeight * rows

            // 새 BufferedImage 생성
            val gridImage = BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB)
            val g2d = gridImage.createGraphics()

            // 이미지를 그리드에 배치
            imageOrder.take(cols * rows).forEachIndexed { index, key ->
                val x = (index % cols) * singleWidth
                val y = (index / cols) * singleHeight

                imageMap[key]?.let { imageBytes ->
                    val image = ImageIO.read(ByteArrayInputStream(imageBytes))
                    g2d.drawImage(image, x, y, null)
                }
            }

            g2d.dispose()

            // BufferedImage를 ByteArray로 변환
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(gridImage, "png", outputStream)
            val imageBytes = outputStream.toByteArray()

            // ByteArray를 Base64로 인코딩
            return Base64.getEncoder().encodeToString(imageBytes)
        }

        fun getPubKey(): String {
            return "PubKey"
        }
    }

}