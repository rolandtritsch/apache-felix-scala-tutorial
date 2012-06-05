/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example3

import org.osgi.framework.{BundleActivator, BundleContext, ServiceReference}

import tutorial.example2.service.DictionaryService

/**
 * This class implements a bundle that uses a dictionary
 * service to check for the proper spelling of a word by
 * check for its existence in the dictionary. This bundle
 * uses the first service that it finds and does not monitor
 * the dynamic availability of the service (i.e., it does not
 * listen for the arrival or departure of dictionary services).
 * When starting this bundle, the thread calling the start()
 * method is used to read words from standard input. You can
 * stop checking words by entering an empty line, but to start
 * checking words again you must stop and then restart the bundle.
 */
class Activator extends BundleActivator {
  /**
   * Implements BundleActivator.start(). Queries for
   * all available dictionary services. If none are found it
   * simply prints a message and returns, otherwise it reads
   * words from standard input and checks for their existence
   * from the first dictionary that it finds.
   * (NOTE: It is very bad practice to use the calling thread
   * to perform a lengthy process like this; this is only done
   * for the purpose of the tutorial.)
   * @param context the framework context for the bundle.
   */
  override def start(c: BundleContext) {
    val refs: Array[ServiceReference] = c.getServiceReferences("tutorial.example2.service.DictionaryService", "(Language=*)")
    val dictionary: DictionaryService = c.getService(refs.head).asInstanceOf[DictionaryService]
    assert(dictionary != null, "Could not get Dictionary Service")

    println("Enter words to to check. Enter <exit> to exit. Stop/start the bundle to restart ...")
    val words = io.Source.stdin.getLines
    var word = words.next
    while(word != "exit") {
      println(word + " is correct(" + dictionary.checkWord(word) + ")")
      word = words.next
    }
  }

  /**
   * Implements BundleActivator.stop(). Does nothing since
   * the framework will automatically unget any used services.
   * @param context the framework context for the bundle.
   */
  override def stop(c: BundleContext) {
    // NOTE: The service is automatically released.
  }
}
