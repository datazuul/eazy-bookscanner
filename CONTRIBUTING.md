# Contributing to this repository

## Testing

Please test the application with your cameras and report success/failure under <https://github.com/datazuul/eazy-bookscanner/discussions/categories/show-and-tell>

## Based on

* Canon Hack Development Kit (on camera): <https://chdk.fandom.com/wiki/CHDK>
* CHDK-PTP-Java: Our fork <https://github.com/datazuul/CHDK-PTP-Java> of <https://github.com/acamilo/CHDK-PTP-Java>
* USB4Java: <http://usb4java.org/usb4java-javax/>

## Development

Development was done

* JDK: OpenJDK 11.0.8
* Build: Apache Maven 3
* IDE: Apache NetBeans 12.1
* Snapshot Libraries (clone them, too for local build/customization):

  * CHDK-PTP-Java: <https://github.com/datazuul/CHDK-PTP-Java>

Make sure source code at least compiles with `mvn clean install`.

Main part of application is easy to understand if you are familiar with [Swing](https://docs.oracle.com/javase/tutorial/uiswing/index.html).

## Creating distribution Package

After executing `mvn clean install` in parent package, an executable JAR created in `/target/`, e.g. named `eazy-bookscanner-1.0.0-SNAPSHOT.jar`.

This package contains the whole application and all dependencies, ready for execution with

```
java -jar eazy-bookscanner-1.0.0-SNAPSHOT.jar
```
