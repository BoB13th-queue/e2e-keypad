package bob.e2e.domain.model

data class Keypad(
    val id: Int,
    val keypadList: List<Pair<String, String>>,
    val hash: String
)
