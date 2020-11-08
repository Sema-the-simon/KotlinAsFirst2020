@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException
import kotlin.math.pow


// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val dictionary = mapOf(
        "января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4,
        "мая" to 5, "июня" to 6, "июля" to 7, "августа" to 8,
        "сентября" to 9, "октября" to 10, "ноября" to 11, "декабря" to 12
    )
    val parts = str.split(" ")
    return try {
        if (parts.size != 3) throw NumberFormatException("For input string: $str")
        val days = parts[0].toInt()
        val month = dictionary[parts[1]] ?: throw NumberFormatException("For input string: ${parts[1]}")
        val year = parts[2].toInt()
        if (days > daysInMonth(month, year)) throw NumberFormatException("For input string: $days")
        String.format("%02d.%02d.%02d", days, month, year)
    } catch (e: NumberFormatException) {
        ""
    }

}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String = TODO()

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val set = setOf(' ', '-')
    val analysingString = phone.filterNot { it in set }
    val result = StringBuilder()
    var indexOfOpen = -1
    var indexOfClose = -1
    val error = NumberFormatException("For input string: $phone")
    try {
        for (i in analysingString.indices)
            when (val char = analysingString[i]) {
                '+' -> if (i == 0) result.append(char)
                else throw error
                '(' -> if (indexOfOpen < 0) indexOfOpen = i
                else throw  error
                ')' -> if (indexOfOpen != -1 && indexOfClose < 0 && i - indexOfOpen > 1) indexOfClose = i
                else throw error
                in '0'..'9' -> result.append(char)
                else -> throw error
            }
        return result.toString()
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val analysingString = jumps.split(" ")
    var maxHeight = -1
    try {
        if (analysingString.size % 2 != 0) throw NumberFormatException("For input string: $jumps")
        for (i in 0..analysingString.size - 2 step 2) {
            val height = analysingString[i].toInt()
            val stringOfAttempts = analysingString[i + 1]
            for (attempt in stringOfAttempts) {
                when (attempt) {
                    '+' -> if (height > maxHeight) maxHeight = height
                    '%', '-' -> continue
                    else -> throw NumberFormatException("For input string: $stringOfAttempts")
                }
            }
        }
        return maxHeight
    } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val analysingString = expression.split(" ")
    val error = IllegalArgumentException("For input string: $expression")
    if (analysingString.size % 2 != 1) throw error
    try {
        var result = if (analysingString[0][0].isDigit()) analysingString[0].toInt()
        else throw error
        for (i in 1 until analysingString.size step 2) {
            val currentNumber = if (analysingString[i + 1][0].isDigit()) analysingString[i + 1].toInt()
            else throw error
            when (analysingString[i]) {
                "+" -> result += currentNumber
                "-" -> result -= currentNumber
                else -> throw error

            }
        }
        return result
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("For input string: $expression")
    }
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val analysingString = str.split(" ")
    var previousWord = analysingString[0].toLowerCase()
    var result = -1
    var currentlenght = previousWord.length
    for (i in 1 until analysingString.size) {
        val currentWord = analysingString[i].toLowerCase()
        if (currentWord == previousWord) result = currentlenght + i - 1 - previousWord.length
        currentlenght += currentWord.length
        previousWord = currentWord
    }
    return result
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    val analysingString = description.split("; ", " ")
    var result = "" to 0.0
    val error = NumberFormatException("For input string: $description")
    try {
        if (analysingString.size % 2 != 0) throw error
        for (i in 1..analysingString.size step 2) {
            val product = analysingString[i - 1]
            val price = analysingString[i].toDouble()
            if (price < 0) throw error
            if (price > result.second) result = product to price
        }
        return result.first
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
/*
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",
        "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC",
        "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM",
        "M", "MM", "MMM"
*/
fun fromRoman(roman: String): Int {
    val dicUnits = listOf('I', 'X', 'C', 'M')
    val dicFives = listOf('V', 'L', 'D')
    var result = 0
    var minIndex = 4
    var newNumber = 0
    var previousIndex = 0
    for (currentChar in roman) {
        val currentIndex = when (currentChar) {
            in dicUnits -> dicUnits.indexOf(currentChar)
            in dicFives -> dicFives.indexOf(currentChar)
            else -> return -1
        }
        when {
            newNumber == 0 && currentIndex == 3 -> {
                result += 1000
                minIndex = currentIndex
            }
            newNumber == 0 && currentIndex < minIndex -> {
                previousIndex = currentIndex
                if (currentChar in dicUnits) {
                    newNumber += 1
                    result += 10.0.pow(currentIndex).toInt()
                } else {
                    result += 5 * 10.0.pow(currentIndex).toInt()
                }
            }
            newNumber != 0 && currentIndex <= minIndex -> {
                when (currentIndex) {
                    previousIndex + 1 -> {
                        result += 8 * 10.0.pow(previousIndex).toInt()
                        minIndex = previousIndex
                        newNumber = 0
                    }
                    previousIndex -> {
                        minIndex = previousIndex
                        if (currentChar in dicUnits) {
                            result += 10.0.pow(currentIndex).toInt()
                            if (newNumber < 2) newNumber += 1
                            else newNumber = 0
                        } else {
                            result += 3 * 10.0.pow(currentIndex).toInt()
                            newNumber = 0
                        }
                    }
                    else -> {
                        previousIndex = currentIndex
                        if (currentChar in dicUnits) {
                            newNumber = 1
                            result += 10.0.pow(currentIndex).toInt()
                        } else {
                            newNumber = 0
                            result += 5 * 10.0.pow(currentIndex).toInt()
                        }
                    }
                }
            }
            else -> return -1
        }
    }
    return result
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
