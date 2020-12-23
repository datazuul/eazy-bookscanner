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

This software is based on [Apache NetBeans Platform ](http://netbeans.apache.org/kb/docs/platform/index.html).
Main part of application is in submodule `application` and is easy to understand if you are familiar with [Swing](https://docs.oracle.com/javase/tutorial/uiswing/index.html).

## Creating distribution Package

After executing `mvn clean install` in parent package, a ZIP-distribution package is created,
located in `application/target/`, e.g. named `eazy-bookscanner-app-0.2.0-SNAPSHOT.zip`.

This package contains the whole application and all dependencies, ready for execution after unpacking (see contained `bin` folder).

Problem: Depending on development environment (?) the contained configuration file `etc/eazybookscanner.conf` contains wrong default values.

Solution: Edit file `application/target/eazybookscanner/etc/eazybookscanner.conf` manually and
make sure that default values are:

```
default_userdir="${DEFAULT_USERDIR_ROOT}"
default_cachedir="${DEFAULT_CACHEDIR_ROOT}"
```

and create ZIP file manually by zipping directory `application/target/eazybookscanner`.

Linux zip example:

```
$ zip -r eazy-bookscanner-app-0.2.0-SNAPSHOT.zip eazybookscanner/
```