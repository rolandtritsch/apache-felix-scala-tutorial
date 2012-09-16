/**
 * Apache Felix OSGi tutorial.
 */

package tutorial.example6

import org.osgi.framework.{BundleActivator, BundleContext}
import org.osgi.framework.{ServiceRegistration, ServiceReference}
import org.osgi.framework.{ServiceListener, ServiceEvent}
import org.osgi.service.log.{LogEntry, LogListener, LogReaderService, LogService}

import tutorial.example2.service.DictionaryService
import tutorial.example6.service.SpellChecker

/**
 * This class implements a bundle that implements a spell
 * checker service. The spell checker service uses all available
 * dictionary services to check for the existence of words in
 * a given sentence. This bundle not only monitors the dynamic
 * availability of dictionary services, but it manages the
 * aggregation of all available dictionary services as they
 * arrive and depart. The spell checker service is only registered
 * if there are dictionary services available, thus the spell
 * checker service will appear and disappear as dictionary
 * services appear and disappear, respectively.
 */
class Activator extends BundleActivator with ServiceListener {
  // NOTE: I did not find a good/easy way to determine the classname from a trait
  // and/or directly from class (without creating an instance first) 
  private final val SPELL_CLAZZ = "tutorial.example6.service.SpellChecker"
  private final val DICT_CLAZZ = "tutorial.example2.service.DictionaryService"

  private var logSvc: LogService = null

  // NOTE: Having these vars around is probably unnecessary, but I kept them so that
  // the source code looks similar to the original
  private var context: BundleContext = null
  private var availableDictServices: Map[ServiceReference, DictionaryService] = null
  private var spellChecker: ServiceRegistration = null

  /**
   * Implements BundleActivator.start(). Adds itself
   * as a service listener and queries for all currently
   * available dictionary services. Any available dictionary
   * services are added to the service reference list. If
   * dictionary services are found, then the spell checker
   * service is registered.
   * 
   * @param context - the framework context for the bundle.
   */
  override def start(c: BundleContext ) {
    assert(c != null); context = c

    // NOTE: Added the log service to allow/support/show more/better
    // information while playing around with the code
    val logReaderRef = context.getServiceReference("org.osgi.service.log.LogReaderService")
    assert(logReaderRef != null)
    val logReaderSvc = context.getService(logReaderRef).asInstanceOf[LogReaderService]
    logReaderSvc.addLogListener(new LogWriter())

    val logRef = context.getServiceReference("org.osgi.service.log.LogService")
    assert(logRef != null)
    logSvc = context.getService(logRef).asInstanceOf[LogService]
    assert(logSvc != null)

    // NOTE: Omitted the synchronisation from the original tutorial

    logSvc.log(LogService.LOG_INFO, "Register listener for events pertaining to dictionary services ...")
    context.addServiceListener(this, "(&(objectClass=" + DICT_CLAZZ + ")(Language=*))")

    logSvc.log(LogService.LOG_INFO, "Query for all dictionary services ...")
    val allRefs = context.getServiceReferences(DICT_CLAZZ, "(Language=*)")
    assert(allRefs != null, "No Dictionary Services found!!!")
    availableDictServices = (for(r <- allRefs; s = context.getService(r).asInstanceOf[DictionaryService]) yield (r, s)).toMap
    logSvc.log(LogService.LOG_DEBUG, "Found services: " + availableDictServices.mkString("!!!"))

    logSvc.log(LogService.LOG_INFO, "If there are any dictionary services, register the spellchecker ...")
    if(!availableDictServices.isEmpty) {
      spellChecker = context.registerService(SPELL_CLAZZ, new SpellCheckerImpl(), null)
    }
    println("Started SpellChecker Service ...")
  }

  /**
   * Implements BundleActivator.stop(). Does nothing since
   * the framework will automatically unregister any registered services,
   * release any used services, and remove any event listeners.
   * 
   * @param context - the framework context for the bundle.
   */
  override def stop(c: BundleContext) {
    // NOTE: The services automatically released.
  }

  /**
   * Implements ServiceListener.serviceChanged(). Monitors
   * the arrival and departure of dictionary services, adding and
   * removing them from the service reference list, respectively.
   * In the case where no more dictionary services are available,
   * the spell checker service is unregistered. As soon as any
   * dictionary service becomes available, the spell checker service
   * is reregistered.
   * 
   * @param e - the fired service event.
   */
  override def serviceChanged(e: ServiceEvent): Unit = {
    assert(e != null)
    val r = e.getServiceReference; val s = context.getService(r).asInstanceOf[DictionaryService]
    e.getType match {
      case ServiceEvent.REGISTERED => registerDictService(r, s)
      case ServiceEvent.UNREGISTERING => unregisterDictService(r, s)
      case _ => assert(true) // ignore all other events
    }
  }

  private def registerDictService(r: ServiceReference, s: DictionaryService): Unit = {
    assert(r != null); assert(s != null)
    assert(!availableDictServices.contains(r))
    availableDictServices = availableDictServices + (r -> s)
    
    // if this is the first dictionary service that becomes available,
    // then we also can/need to make the spellchecker available
    if(spellChecker == null) {
      assert(availableDictServices.size == 1)
      spellChecker = context.registerService(SPELL_CLAZZ, new SpellCheckerImpl(), null)
    }
  }

  private def unregisterDictService(r: ServiceReference, s: DictionaryService): Unit = {
    assert(r != null); assert(s != null)
    assert(availableDictServices.contains(r))
    availableDictServices = availableDictServices - r
    context.ungetService(r)

    // if this was the last dict service that was available,
    // then we also need to take down the spellchecker
    if(availableDictServices.isEmpty) {
      assert(spellChecker == null)
      spellChecker.unregister
      spellChecker = null
    }
  }

  /**
   * A private inner class that implements a spell checker service.
   * See SpellChecker for details of the service.
   */
  private class SpellCheckerImpl extends SpellChecker {
    /**
     * Implements SpellChecker.check(). Checks the
     * given passage for misspelled words.
     * 
     * @param passage - the passage to spell check.
     * @return An array of misspelled words.
     */
    override def check(passage: String): Array[String] = {
      assert(passage != null)

      // Tokenize the passage using spaces and punctionation.
      val words = passage.split("\\s|\\.|\\,|\\!|\\?|\\;|\\:")

      // Get the known/unknow words
      val checked = words.map(checkAllDicts(_)).zip(words)
      for(c <- checked; correct = c._1; word = c._2; if(!correct)) yield word
    }

    private def checkAllDicts(w: String): Boolean = {
      (for(s <- availableDictServices.values) yield s.checkWord(w)).toList.contains(true)
    }
  }

  private class LogWriter extends LogListener {
    override def logged(e: LogEntry): Unit = {
      // println(e.getLevel + ": " + e.getBundle + ": " + e.getMessage)
    }
  }
}
