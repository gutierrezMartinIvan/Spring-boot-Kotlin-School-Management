package ar.com.school.management.utils

enum class Mark(mark: Int) {
    PENDING(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    companion object {
        fun setMark(mark: String): Mark {
            for (m in values()) {
                if (m.name == mark.uppercase())
                    return m
            }
            throw IllegalArgumentException("Invalid mark value")
        }
    }
}