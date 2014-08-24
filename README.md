QMTools is a collection of Java-based tools to modify QuickMod files.

## Building (UNIX, should be similar on others)

```
git clone git@github.com:02JanDal/QMTools.git
cd QMTools
gradle qmToolJar versionAppenderJar
```

## Running

```
java -jar QMTool/build/libs/QMTool-<version>-standalone.jar --help
```
or
```
java -jar VersionAppender/build/libs/VersionAppender-<version>-standalone.jar --help
```

### Example for creating a new QuickMod file and adding a version to it

```
java -jar QMTool/build/libs/QMTool-<version>-standalone.jar --uid "me.mytestmod" --repo "org.mysite" --name "MyTestMod" --description "This is a test of the QuickMod system" MyTestMod.qm
java -jar VersionAppender/build/libs/VersionAppender-<version>-standalone.jar --name "1.2" --type "Alpha" --urls "http://mysite.org/downloads/mytestmod-1.2.jar:direct" --sha1 /var/www/mysite/downloads/mytestmod-1.2.jar
```
