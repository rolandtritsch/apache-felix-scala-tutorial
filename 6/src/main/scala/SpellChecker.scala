/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example6.service

/**
 * A simple service interface that defines a spell checker service.
 * A spell checker service checks the spelling of all words in a
 * given passage. A passage is any number of words separated by
 * a space character and the following punctuation marks: comma,
 * period, exclamation mark, question mark, semi-colon, and colon.
 */
trait SpellChecker {
    /**
     * Checks a given passage for spelling errors. A passage is any
     * number of words separated by a space and any of the following
     * punctuation marks: comma (,), period (.), exclamation mark (!),
     * question mark (?), semi-colon (;), and colon(:).
     * 
     * @param passage - the passage to spell check.
     * @return An array of misspelled words or null if no
     *         words are misspelled.
     */
    def check(passage: String): Array[String]
}
