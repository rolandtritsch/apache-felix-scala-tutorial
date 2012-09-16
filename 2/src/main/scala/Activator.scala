/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example2

import scala.io.Source

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
    println("Starting English Dictionary Service ...")
    val pEnglish = new java.util.Properties(); pEnglish.setProperty("Language", "english")
    c.registerService("tutorial.example2.service.DictionaryService", new DictionaryImplEnglish(), pEnglish)

    println("Starting French Dictionary Service ...")
    val pFrench = new java.util.Properties(); pFrench.setProperty("Language", "french")
    c.registerService("tutorial.example2.service.DictionaryService", new DictionaryImplFrench(), pFrench)

    println("Starting German Dictionary Service ...")
    val pGerman = new java.util.Properties(); pGerman.setProperty("Language", "german")
    c.registerService("tutorial.example2.service.DictionaryService", new DictionaryImplGerman(), pGerman)
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
  private class DictionaryImplEnglish extends DictionaryService {
    // NOTE: Uncomment this to get a bigger english dictionary (works on the Mac)
    // val dictionary = Source.fromFile("/usr/share/dict/words").getLines.toArray
    val dictionary = Array("welcome", "to", "the", "osgi", "tutorial")

    override def checkWord(word: String): Boolean = {
      val foundIt = dictionary.contains(word.toLowerCase)
      // println("Checking (english): " + word + "/" + foundIt + " ...")
      foundIt
    }
  }

  private class DictionaryImplFrench extends DictionaryService {
    val dictionary = Array("bienveue", "au", "tutorial", "osgi")

    override def checkWord(word: String): Boolean = {
      val foundIt = dictionary.contains(word.toLowerCase)
      // println("Checking (french): " + word + "/" + foundIt + " ...")
      foundIt
    }
  }

  private class DictionaryImplGerman extends DictionaryService {
    val dictionary = Array("willkommen", "zum", "osgi", "tutorial")

    override def checkWord(word: String): Boolean = {
      val foundIt = dictionary.contains(word.toLowerCase)
      // println("Checking (german): " + word + "/" + foundIt + " ...")
      foundIt
    }
  }
}
