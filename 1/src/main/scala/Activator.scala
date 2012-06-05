/**
 * Apache Felix OSGi tutorial (Scala Version)
 */

package tutorial.example1

import org.osgi.framework.{BundleActivator, BundleContext, ServiceListener, ServiceEvent}

/**
 * This class implements a simple bundle that utilizes the OSGi
 * framework's event mechanism to listen for service events. Upon
 * receiving a service event, it prints out the event's details.
 */
class Activator extends BundleActivator with ServiceListener {
  override def start(c: BundleContext) {
    println("Starting Service Listener ...")
    c.addServiceListener(this)
  }

  override def stop(c: BundleContext) {
    c.removeServiceListener(this)
    println("Stopped listening for service events ...")
  }

  override def serviceChanged(e: ServiceEvent) {
    val objectClass: String = e.getServiceReference().getProperty("objectClass").asInstanceOf[Array[String]].head

    if(e.getType == ServiceEvent.REGISTERED) {
      println("Ex1: Service of type " + objectClass + " registered ...")
    } else if(e.getType == ServiceEvent.UNREGISTERING) {
      println("Ex1: Service of type " + objectClass + " unregistered ...")
    } else if (e.getType == ServiceEvent.MODIFIED) {
      println("Ex1: Service of type " + objectClass + " modified.")
    } else {
      println("Ex1: Unknown service type ...")
    }
  }
}
