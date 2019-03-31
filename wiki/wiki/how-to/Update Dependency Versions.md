All versions can be found in the _pom.xml_ of the base directory. 

The only exclusion from this are the *Java* and *PHP* versions.

# Java Version

The Java is defined in the property `java-version` of the _pom.xml_, but you do have to decide which versions to build against, which is done in the _.travis.yml_. Both of these files are in the base directory.

The _README.md_ is updated automatically after a Maven build.

 

# PHP Version

The minimum PHP version is the lowest version to build against in the _.travis.yml_. However it should be defined in the  _README.md_, too.
