@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1


// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, List<String>>()
    for ((name, mark) in grades) {
        res[mark] = res.getOrDefault(mark, mutableListOf()) + name
    }
    return res
}

/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for (key in a.keys) {
        if (a[key] == b[key]) continue
        return false
    }
    return true
}

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    TODO()
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val aSet = a.toSet()
    val bSet = b.toSet()
    val result = mutableListOf<String>()
    for (value in aSet)
        if (value in bSet) result += value
    return result
}

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val setKeys = mapA.keys.union(mapB.keys)
    val result = mutableMapOf<String, String>()
    for (key in setKeys) {
        val values = mutableSetOf<String>()
        if (mapA[key] != null) values.add(mapA.getValue(key))
        if (mapB[key] != null) values.add(mapB.getValue(key))
        result[key] = values.joinToString { it }
    }
    return result
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val mapStockPrices = mutableMapOf<String, Pair<Double, Int>>()
    for ((key, price) in stockPrices) {
        val (mapPrice, count) = mapStockPrices[key] ?: 0.0 to 0
        mapStockPrices[key] =
            (mapPrice + price) to (count + 1)
    }
    val res = mutableMapOf<String, Double>()
    for ((key, pair) in mapStockPrices) {
        res[key] = pair.first / pair.second
    }
    return res
}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minPrice = 0.0
    var name: String? = null
    var flag = false
    for ((nameInStuff, kindToPrice) in stuff) {
        if ((kindToPrice.first == kind) && (!flag || kindToPrice.second < minPrice)) {
            minPrice = kindToPrice.second
            flag = true
            name = nameInStuff
        }
    }
    return name
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setOfChars = chars.map { it.toLowerCase() }.toSet()
    for (letter in word.toLowerCase().toSet()) {
        if (letter in setOfChars) continue
        return false
    }
    return true
}

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun <T> numberOfChars(list: List<T>): Map<T, Int> {
    val res = mutableMapOf<T, Int>()
    for (any in list) {
        res[any] = res.getOrDefault(any, 0) + 1
    }
    return res
}

fun extractRepeats(list: List<String>): Map<String, Int> {
    val res = numberOfChars(list)
    val listOfSingleString = mutableListOf<String>()
    for ((element, number) in res) {
        if (number == 1) listOfSingleString += element
    }
    return res - listOfSingleString
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val mapOfWords = mutableMapOf<Map<Char, Int>, Int>()
    for (word in words) {
        val key = numberOfChars(word.toList())
        mapOfWords[key] = mapOfWords.getOrDefault(key, 0) + 1
    }
    for (value in mapOfWords.values) {
        if (value > 1) return true
    }
    return false
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> = TODO()
/*
{
    val setOfAllNames = mutableSetOf<String>()
    for ((name, mainFriends) in friends) {
        setOfAllNames += name
        for (mainFrName in mainFriends) setOfAllNames += mainFrName
    }
    var allFriends = mapOf<String, Set<String>>()
    val setDoneNames = mutableSetOf<String>()
    for (name in setOfAllNames) {
        if (name in setDoneNames) continue
        val (allFriendsCh, setDoneNameCh) = findAllFriends(friends, name, setDoneNames, null)
        allFriends = allFriendsCh
        setDoneNames += setDoneNameCh
    }
    return allFriends
}

fun findAllFriends(
    mainFriends: Map<String, Set<String>>,
    name: String,
    setDoneNames: Set<String>,
    stopName: String?
): Pair<Map<String, Set<String>>, Set<String>> {
    var mainFriendsCh = mainFriends.toMutableMap()
    val setDoneNamesCh = setDoneNames.toMutableSet()
    val res = mainFriends[name]?.toMutableSet()
    setDoneNamesCh += name
    if (res != null && name != stopName && name !in setDoneNamesCh) {
        val stopNameCh = stopName ?: name
        for (friendName in res) {
            val (friendsMap, friendsSet) = findAllFriends(mainFriends, friendName, setDoneNames, stopNameCh)
            res += friendsMap.getOrDefault(friendName, setOf()) - name
            mainFriendsCh = friendsMap.toMutableMap()
            setDoneNamesCh += friendsSet.toMutableSet()
            mainFriendsCh[name] = res
        }
    }
    mainFriendsCh[name] = mainFriendsCh.getOrDefault(name, setOf())
    return mainFriendsCh to setDoneNamesCh
}

   val setOfAllNames = mutableSetOf<String>()
    for ((name, mainFriends) in friends) {
        setOfAllNames += name
        for (mainFrName in mainFriends) setOfAllNames += mainFrName
    }
    val allFriends = mutableMapOf<String, MutableSet<String>>()
    val allAdditionalFriends = mutableMapOf<String, Set<String>>()
    for (name in setOfAllNames) {
        val mainFriends = friends.getOrDefault(name, setOf())
        allFriends[name] = mainFriends.toMutableSet()
        for (mainFrName in mainFriends) {
            allAdditionalFriends[name] = friends.getOrDefault(mainFrName, mutableSetOf()) - name
        }
        allFriends[name]?.addAll(allAdditionalFriends.getOrDefault(name, mutableSetOf()))
    }
   return allFriends */

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val mapOfNumber = mutableMapOf<Int, Int>()
    for (i in list.indices) {
        val analysingNumber = list[i]
        val possiblePair = mapOfNumber[number - analysingNumber]
        if (possiblePair != null) return possiblePair to i
        mapOfNumber[analysingNumber] = i
    }
    return -1 to -1
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val listOfTreasures = treasures.toList()
    val result = setOf<String>()
    return if (listOfTreasures.isEmpty()) result
    else treasuresAnalizing(listOfTreasures.size - 1, listOfTreasures, capacity, result).first
}


fun treasuresAnalizing(
    index: Int,
    listOfTreasures: List<Pair<String, Pair<Int, Int>>>,
    capacity: Int,
    setOfTreasures: Set<String>
): Pair<Set<String>, Int> {
    val currentTreasure = listOfTreasures[index].first
    val (veight, cost) = listOfTreasures[index].second
    val resSetOfTreasures = setOfTreasures.toMutableSet()
    val resCost = 0
    if (index == 0) {
        return if (veight > capacity)
            resSetOfTreasures to resCost
        else
            (resSetOfTreasures + currentTreasure) to cost
    } else {
        val withoutCurrent = treasuresAnalizing(
            index - 1,
            listOfTreasures,
            capacity,
            resSetOfTreasures
        )
        return if (veight > capacity) withoutCurrent
        else {
            val withCurrent = treasuresAnalizing(
                index - 1,
                listOfTreasures,
                capacity - veight,
                resSetOfTreasures
            )
            if (withoutCurrent.second > withCurrent.second + cost) withoutCurrent
            else (withCurrent.first + currentTreasure) to (withCurrent.second + cost)
        }
    }
}