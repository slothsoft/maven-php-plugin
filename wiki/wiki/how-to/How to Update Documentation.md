The documentation for the wiki and the _README.md_ is stored in the (wiki folder)[https://github.com/slothsoft/maven-php-plugin/tree/master/wiki] of this repository. 

Both files are processed during the Maven build and can contain Maven variables, e.g.

- `\${mavenPluginGroupId}` - the group ID of (maven-php-plugin)[https://github.com/slothsoft/maven-php-plugin/tree/master/maven-plugins/maven-php-plugin]
- `\${mavenPluginArtifactId}` - the artifact ID of (maven-php-plugin)[https://github.com/slothsoft/maven-php-plugin/tree/master/maven-plugins/maven-php-plugin]
- `\${java-version}` - for the minimum Java version
- ...

The _README.md_ is compiled into the base folder, while the wiki files are compiled into _target/wiki_. The later folder can be changed by using the argument `wikiFolder` on the Maven command line, e.g. `mvn clean verify -DwikiFolder="C:\temp"`.

