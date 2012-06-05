/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example2

import org.osgi.framework.{BundleActivator, BundleContext, ServiceListener, ServiceEvent}

import tutorial.example2.service.DictionaryService

/**
 * This class implements a simple bundle that uses the bundle
 * context to register an English language dictionary service
 * with the OSGi framework. The dictionary service interface is
 * defined in a separate class file and is implemented by an
 * inner class.
 */
class Activator extends BundleActivator {
  /**
   * Implements BundleActivator.start(). Registers an
   * instance of a dictionary service using the bundle context;
   * attaches properties to the service that can be queried
   * when performing a service look-up.
   * @param context the framework context for the bundle.
   */
  override def start(c: BundleContext) {
    println("Starting Dictionary Service ...")
    val p = new java.util.Properties(); p.setProperty("Language", "english")
    c.registerService("tutorial.example2.service.DictionaryService", new DictionaryImpl(), p)
  }

  /**
   * Implements BundleActivator.stop(). Does nothing since
   * the framework will automatically unregister any registered services.
   * @param context the framework context for the bundle.
   */
  override def stop(c: BundleContext) {
    // NOTE: The service is automatically unregistered.
  }

  /**
   * A private inner class that implements a dictionary service;
   * see DictionaryService for details of the service.
   */
  private class DictionaryImpl extends DictionaryService {
    // The set of words contained in the dictionary.
    val dictionary = Array("welcome", "to", "the", "osgi", "tutorial")

    /**
     * Implements DictionaryService.checkWord(). Determines
     * if the passed in word is contained in the dictionary.
     * @param word the word to be checked.
     * @return true if the word is in the dictionary,
     *         false otherwise.
     */
    override def checkWord(word: String): Boolean = {
      dictionary.contains(word.toLowerCase)
    }
  }
}
