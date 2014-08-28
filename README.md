[![Build Status](https://travis-ci.org/02JanDal/QMTools.svg?branch=master)](https://travis-ci.org/02JanDal/QMTools)
[![Coverage Status](https://img.shields.io/coveralls/02JanDal/QMTools.svg)](https://coveralls.io/r/02JanDal/QMTools?branch=master)

QMTools is a collection of Java-based tools to modify QuickMod files.

## Building (UNIX, should be similar on others)

```
git clone git@github.com:02JanDal/QMTools.git
cd QMTools
gradle installApp
```

## Running

### Linux

```
build/install/bin/QMTools --help
```

### Windows

```
build/install/bin/QMTools.bat --help
```

### Example for creating a new QuickMod file and adding a version to it

```
build/install/bin/QMTools --uid "me.mytestmod" --repo "org.mysite" --name "MyTestMod" --description "This is a test of the QuickMod system" MyTestMod.qm
build/install/bin/QMTools --name "1.2" --type "Alpha" --urls "http://mysite.org/downloads/mytestmod-1.2.jar:direct" --sha1 /var/www/mysite/downloads/mytestmod-1.2.jar
```
