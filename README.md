Apache Felix Scala Tutorial
============================

A Scala version of the Apache Felix Tutorial.

http://felix.apache.org/site/apache-felix-osgi-tutorial.html.

Disclaimer: The aim of this is NOT to teach Scala or OSGi. The code
is unsuitable to serve as template/boilerplate Scala code on how to 
use OSGi/Felix from Scala. The Scala code itself leaves room for 
improvement. The code also needs work to show the best way to use 
OSGi itself.

The aim of this is just to give you a starting point for experimenting
with how to use OSGi and Scala. Please feel free to improve the code
as you see fit.

Installation
------------

You need to have SBT installed (e.g. using macports: port install sbt).

Just git clone ... the repo and go to ./1 and run ..

>sbt clean publish-local osgi-bundle

This will also download/install the sbtosgi plugin ...

https://github.com/oscarvarto/sbtosgi-examples

You then need to download/install the Felix Framework Distribution from ...

http://felix.apache.org/site/downloads.cgi

You then need to put the Apache Felix Log Service Implemention ...

http://ftp.heanet.ie/mirrors/www.apache.org/dist//felix/org.apache.felix.log-1.0.1.jar

... and the OSGi-fied scala-library (2.9.1) implementation ...

http://mvnrepository.com/artifact/com.weiglewilczek.scala-lang-osgi/scala-library/2.9.1

.. into your <felix-framework-distribution>/bundle directory. This will allow you to
reset Felix easily (i.e. delete the felix-cache), but will make sure that the log service
and Scala are always available and will always be started.

CD to the Felix directory, start Felix ...

>java -jar ./bin/felix.jar

.. and install/start the Tutorial 1 bundle ...

g! install file://<where-you-cloned-the-repo>/apache-felix-scala-tutorial/1/target/scala-2.9.1/tutorial-example-1---service-listener_2.9.1-0.1.jar
g! start <bundle-id>

You should see a message like ... Starting Service Listener ... means you are ready
to go. Rinse and repeat with the rest of the tutorials. More information on what the
tutorials do and how to use them is obviously available through the original description
of the tutorial ...

http://felix.apache.org/site/apache-felix-osgi-tutorial.html

I did not bother to implement Tutorial 4 and took the liberty to simplify some of the
code. Tutorial 6 got notes in it to document the main shortcuts. But as I said above ...
... the intention is not to give you a polished result, but a working starting point
for your own experiemntations.

Enjoy :) ...