/*
 * Apache Felix OSGi tutorial.
 */

package tutorial.example7

import org.osgi.framework.{BundleActivator, BundleContext}
import org.osgi.util.tracker.ServiceTracker

import scala.io.Source

import tutorial.example6.service.SpellChecker

/**
 * This class implements a bundle that uses a spell checker
 * service to check the spelling of a passage. This bundle
 * is essentially identical to Example 5, in that it uses the
 * Service Tracker to monitor the dynamic availability of the
 * spell checker service. When starting this bundle, the thread
 * calling the start() method is used to read passages from
 * standard input. You can stop spell checking passages by
 * entering an empty line, but to start spell checking again
 * you must stop and then restart the bundle.
 */

class Activator extends BundleActivator {
  /**
   * Implements BundleActivator.start(). Creates a Service
   * Tracker object to monitor spell checker services. Enters
   * a spell check loop where it reads passages from standard
   * input and checks their spelling using the spell checker service.
   * (NOTE: It is very bad practice to use the calling thread
   * to perform a lengthy process like this; this is only done
   * for the purpose of the tutorial.)
   *
   * @param context the framework context for the bundle.
   */
  override def start(c: BundleContext): Unit = {
    assert(c != null)

    // Create a service tracker to monitor spellchecker services ...
    val tracker = new ServiceTracker(c, c.createFilter("(&(objectClass=tutorial.example6.service.SpellChecker))"), null)
    tracker.open()

    println("SpellChecker: Enter passage to to check (one line at a time). Enter <exit> to exit. Stop/start the bundle to restart ...")
    val passages = io.Source.stdin.getLines
    var passage = ""
    print("spell> "); passage = passages.next
    while (passage != "exit") {
      println("Checking >" + passage + "< ...") 
      val spellChecker: Option[SpellChecker] = Option(tracker.getService.asInstanceOf[SpellChecker])
      spellChecker match {
        case Some(spellChecker) => println("Found the following wrong words >" + spellChecker.check(passage).mkString(", ") + "< ...")
        case None => println("SpellChecker Service is offline. Try again ...")
      }
      print("spell> "); passage = passages.next
    }
  }

  /**
   * Implements BundleActivator.stop(). Does nothing since
   * the framework will automatically unget any used services.
   * 
   * @param context the framework context for the bundle.
   */
  override def stop(c: BundleContext): Unit = {
  }
}
