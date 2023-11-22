// WITH_STDLIB

fun toStringTemplateAny(x: Any) = "$x"
fun <T> toStringTemplateGeneric(x: T) = "$x"
inline fun <T> toStringTemplateInlineGeneric(x: T) = "$x"
inline fun <reified T> toStringTemplateInlineReifiedGeneric(x: T) = "$x"

class TestClass<T>(val x: T) {
    fun asString() = "$x"
    inline fun asStringInline() = "$x"
}

fun testLong() {
    val expectedMax = "9223372036854775807"
    assertEquals(expectedMax, toStringTemplateAny(Long.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateGeneric(Long.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateInlineGeneric(Long.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateInlineReifiedGeneric(Long.MAX_VALUE))
    assertEquals(expectedMax, TestClass(Long.MAX_VALUE).asString())
    assertEquals(expectedMax, TestClass(Long.MAX_VALUE).asStringInline())

    val expectedMin = "-9223372036854775808"
    assertEquals(expectedMin, toStringTemplateAny(Long.MIN_VALUE))
    assertEquals(expectedMin, toStringTemplateGeneric(Long.MIN_VALUE))
    assertEquals(expectedMin, toStringTemplateInlineGeneric(Long.MIN_VALUE))
    assertEquals(expectedMin, toStringTemplateInlineReifiedGeneric(Long.MIN_VALUE))
    assertEquals(expectedMin, TestClass(Long.MIN_VALUE).asString())
    assertEquals(expectedMin, TestClass(Long.MIN_VALUE).asStringInline())
}

fun testULong() {
    val expectedMax = "18446744073709551615"
    assertEquals(expectedMax, toStringTemplateAny(ULong.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateGeneric(ULong.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateInlineGeneric(ULong.MAX_VALUE))
    assertEquals(expectedMax, toStringTemplateInlineReifiedGeneric(ULong.MAX_VALUE))
    assertEquals(expectedMax, TestClass(ULong.MAX_VALUE).asString())
    assertEquals(expectedMax, TestClass(ULong.MAX_VALUE).asStringInline())
}

class UserClass {
    override fun toString() = "Hello World!"

    @JsName("valueOf")
    fun valueOf() = "NOT OK!!!"
}

fun testUserClass() {
    val expected = "Hello World!"
    assertEquals(expected, toStringTemplateAny(UserClass()))
    assertEquals(expected, toStringTemplateGeneric(UserClass()))
    assertEquals(expected, toStringTemplateInlineGeneric(UserClass()))
    assertEquals(expected, toStringTemplateInlineReifiedGeneric(UserClass()))
    assertEquals(expected, TestClass(UserClass()).asString())
    assertEquals(expected, TestClass(UserClass()).asStringInline())
}

value class UserValueClass(val x: Int) {
    override fun toString() = "Hello World!"

    @JsName("valueOf")
    fun valueOf() = "NOT OK!!!"
}

fun testUserValueClass() {
    val expected = "Hello World!"
    assertEquals(expected, toStringTemplateAny(UserValueClass(1)))
    assertEquals(expected, toStringTemplateGeneric(UserValueClass(1)))
    assertEquals(expected, toStringTemplateInlineGeneric(UserValueClass(1)))
    assertEquals(expected, toStringTemplateInlineReifiedGeneric(UserValueClass(1)))
    assertEquals(expected, TestClass(UserValueClass(1)).asString())
    assertEquals(expected, TestClass(UserValueClass(1)).asStringInline())
}

fun box(): String {
    testLong()
    testULong()
    testUserClass()
    testUserValueClass()
    return "OK"
}
