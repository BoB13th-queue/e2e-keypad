package bob.e2e.utils.logger

import org.slf4j.LoggerFactory

class Logger {
    companion object {
        private val logger = LoggerFactory.getLogger(Logger::class.java)

        fun debug(message: String) {
            logger.debug(message)
        }

        fun info(message: String) {
            logger.info(message)
        }

        fun error(message: String, e: Throwable) {
            logger.error(message, e)
        }

        fun warn(message: String) {
            logger.warn(message)
        }
    }
    
}