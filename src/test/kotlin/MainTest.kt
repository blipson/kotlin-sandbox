import org.junit.Test
import java.net.URLEncoder

class MainTest {
    @Test
    fun reduceTest() {
        val lines = listOf("hello", "there", "general", "nope", "DONT ADD", "uh uh", "kenobi")
        val endList: List<Any> = lines.fold(listOf(0, listOf<Any>())) { acc, e ->
            listOf(
                    acc[0] as Int + 1,
                    (acc[1] as List<*>).plusElement(listOf(acc[0] as Int, e))
            )
        }
        println(endList)
    }

    @Test
    fun stringMapTest() {
        val testMap = LinkedHashMap<String, String>()
        testMap["testKey0"] = "testValue0"
        testMap["testKey1"] = "testValue1"
        testMap["testKey2"] = "testValue2"
        testMap["testKey3"] = "testValue3"
        testMap["testKey4"] = "testValue4"
        println(testMap.map { "&" + URLEncoder.encode(it.key, "UTF-8") + "=" + URLEncoder.encode(it.value, "UTF-8") }.joinToString(""))
    }

    @Test
    fun multiLineStringTest() {
        val id = 1234
        val name = "namerino"
        println("${javaClass.name}:[$id, name=$name]")
        println("${javaClass.name}:[" +
                        "$id, " +
                        "name=$name]")
    }
}

