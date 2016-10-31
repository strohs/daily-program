import java.io.File
import java.nio.file.Files
import java.util.*
import kotlin.text.Regex

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 11/9/2015
 * Time: 1:45 PM
 *
 *
 * The legend, propagated by email and message boards, purportedly demonstrates that readers can understand the
 * meaning of words in a sentence even when the interior letters of each word are scrambled. As long as all the
 * necessary letters are present, and the first and last letters remain the same, readers appear to have little trouble
 * reading the text.
 *
 * Input Description
 * -----------------
 * Any string of words with/without punctuation.
 *
 * Output Description
 * ------------------
 * A scrambled form of the same sentence but with the word's first and last letter's positions intact.
 */

val PUNCTUATION = Regex("[.,;]")

fun shuffle( s: String ) : String {
    if (s.length < 2) {
        return s
    } else {
        val firstChar = s.first()
        var middleChars: List<Char>
        val lastChars: String
        if ( s.get( s.length-2 ) == '\'') {
            middleChars = s.drop(1).dropLast(2).toList()
            lastChars = s.takeLast(2);
        } else if (s.contains( PUNCTUATION) ) {
            middleChars = s.drop(1).dropLast(2).toList()
            lastChars = s.takeLast(2)
        } else {
            middleChars = s.drop(1).dropLast(1).toList()
            lastChars = s.takeLast(1)
        }
        Collections.shuffle( middleChars )
        val midStr = String(middleChars.toCharArray() )
        return firstChar + midStr + lastChars
    }
}

fun main(args: Array<String>) {
    val path = "data/typoglycemia.txt"

    val lines = File(path).readLines()
    println(":${lines.first().split(" ")}:")

    lines.forEach { line ->
        println( line.split(" ").map { shuffle(it) }.joinToString(" ") )
    }


}
