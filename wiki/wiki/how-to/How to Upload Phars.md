After merging all kinds of projects together it turned out some of the Maven dependencies to PHARs were missing. They were uploaded into the package `de.slothsoft.phars` as well.

_(See [#17 Upload PHARs](https://github.com/slothsoft/maven-php-plugin/issues/17) for the struggle on what the packaging type "phar" meant.)_



# Install Locally

To install a PHAR into a local repository, the following script was used:

```
mvn install:install-file -DgroupId=<group-id> -Dpackaging=<packaging> -Dfile=<path-to-file> -DartifactId=<artifactId> -Dversion=<version>
```

For example:

```
mvn install:install-file -DgroupId=de.slothsoft.phars -Dpackaging=phar -Dfile=phpunit-7.5.8.phar -DartifactId=phpunit -Dversion=7.5.8
```



# Deploy to Nexus

```
mvn deploy:deploy-file -DgeneratePom=false -DrepositoryId=nexus -Durl=http://localhost:8081/nexus/content/repositories/releases -DpomFile=pom.xml -Dfile=target/project-1.0.0.jar
```



# Uploaded PHARs

## PHPUnit

- **Source:** [PHPUnit](https://phar.phpunit.de/)
- **Uploaded Versions:** 3.7.10 (the closest to the version used in the build), 7.5.8

```xml
<dependency>
	<groupId>de.slothsoft.phars</groupId>
	<artifactId>phpunit</artifactId>
	<version>3.7.10</version>
	<scope>test</scope>
	<type>phar</type>
</dependency>
```



# External Links

- [Guide to installing 3rd party JARs](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)
- [How can I programmatically upload an artifact into Nexus 2?](https://support.sonatype.com/hc/en-us/articles/213465818-How-can-I-programmatically-upload-an-artifact-into-Nexus-2-)