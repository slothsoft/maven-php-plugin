After merging all kinds of projects together it turned out some of the Maven dependencies to PHARs were missing. They were uploaded into the package `de.slothsoft.phars` as well.

_(See [#17 Upload PHARs](https://github.com/slothsoft/maven-php-plugin/issues/17).)_



# Install Locally

To install a PHAR into a local repository, the following script was used:

```
mvn install:install-file -DgroupId=de.slothsoft.phars -Dpackaging=phar -Dfile=<path-to-file> -DartifactId=<artifactId> -Dversion=<version>
```

For example:

```
mvn install:install-file -DgroupId=de.slothsoft.phars -Dpackaging=phar -Dfile=phpunit-7.5.8.phar -DartifactId=phpunit -Dversion=7.5.8
```



# Deploy to Nexus

```
mvn deploy:deploy-file -DgeneratePom=false -DrepositoryId=nexus -Durl=http://localhost:8081/nexus/content/repositories/releases -DpomFile=pom.xml -Dfile=target/project-1.0.0.jar
```

# External Links

- [PHPUnit Phars](https://phar.phpunit.de/)
- [Guide to installing 3rd party JARs](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)
- [How can I programmatically upload an artifact into Nexus 2?](https://support.sonatype.com/hc/en-us/articles/213465818-How-can-I-programmatically-upload-an-artifact-into-Nexus-2-)