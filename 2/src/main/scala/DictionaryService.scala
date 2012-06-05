/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example2.service

/**
 * A simple service interface that defines a dictionary service.
 * A dictionary service simply verifies the existence of a word.
 */
trait DictionaryService {
  /**
   * Check for the existence of a word.
   * @param word the word to be checked.
   * @return true if the word is in the dictionary,
   *         false otherwise.
   */
  def checkWord(word: String): Boolean
}
