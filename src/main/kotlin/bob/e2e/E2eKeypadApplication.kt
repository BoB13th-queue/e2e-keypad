package bob.e2e

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class E2eSecureKeypadApplication

fun main(args: Array<String>) {
    runApplication<E2eSecureKeypadApplication>(*args)
}