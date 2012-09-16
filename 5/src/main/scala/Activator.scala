package tutorial.example5

import org.osgi.framework.{BundleActivator, BundleContext}
import org.osgi.util.tracker.ServiceTracker

import tutorial.example2.service.DictionaryService

/**
 * This class implements a bundle that uses a dictionary
 * service to check for the proper spelling of a word by
 * checking for its existence in the dictionary. This bundle
 * is more complex than the bundle in Example 3 because it
 * monitors the dynamic availability of the dictionary
 * services. In other words, if the service it is using
 * departs, then it stops using it gracefully, or if it needs
 * a service and one arrives, then it starts using it
 * automatically. As before, the bundle uses the first service
 * that it finds and uses the calling thread of the
 * start() method to read words from standard input.
 * You can stop checking words by entering an empty line, but
 * to start checking words again you must stop and then restart
 * the bundle.
 */
class Activator extends BundleActivator {
  /**
   * Implements BundleActivator.start(). Creates a service
   * tracker to monitor dictionary services and starts its "word
   * checking loop". It will not be able to check any words until
   * the service tracker find a dictionary service; any discovered
   * dictionary service will be automatically used by the client.
   * It reads words from standard input and checks for their
   * existence in the discovered dictionary.
   * (NOTE: It is very bad practice to use the calling thread
   * to perform a lengthy process like this; this is only done
   * for the purpose of the tutorial.)
   * @param context the framework context for the bundle.
   */
  override def start(c: BundleContext) {
    // Create a service tracker to monitor dictionary services ...
    val tracker = new ServiceTracker(c, c.createFilter("(&(objectClass=tutorial.example2.service.DictionaryService)(Language=*))"), null)
    tracker.open()

    println("Enter words to to check. Enter <exit> to exit. Stop/start the bundle to restart ...")
    val words = io.Source.stdin.getLines
    var word = words.next
    while(word != "exit") {
      val dictionary: Option[DictionaryService] = Option(tracker.getService.asInstanceOf[DictionaryService])
      dictionary match {
	case Some(dictionary) => println(word + " is correct(" + dictionary.checkWord(word) + ")")
	case None => println("Dictionary Service is offline. Try again ...")
      }
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
