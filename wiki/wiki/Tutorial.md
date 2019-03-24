
"C:\Program Files (x86)\Maven\maven-3.6.0\bin\mvn" archetype:generate -DarchetypeGroupId=de.slothsoft.phpmaven -DarchetypeArtifactId=php5-doctrine-archetype -DarchetypeVersion=0.9.0-SNAPSHOT -DgroupId=org.acme -DartifactId=php5-doctrine -Dversion=0.1.2-SNAPSHOT -Dpackage=org.acme.doctrine -DinteractiveMode=false


"C:\Program Files (x86)\Maven\maven-3.6.0\bin\mvn" archetype:generate -DarchetypeGroupId=de.slothsoft.phpmaven -DarchetypeArtifactId=php5-lib-archetype -DarchetypeVersion=0.9.0-SNAPSHOT -DgroupId=org.acme -DartifactId=php5-lib -Dversion=0.1.2-SNAPSHOT -Dpackage=org.acme.lib -DinteractiveMode=false

"C:\Program Files (x86)\Maven\maven-3.6.0\bin\mvn" archetype:generate -DarchetypeGroupId=de.slothsoft.phpmaven -DarchetypeArtifactId=php5-web-archetype -DarchetypeVersion=0.9.0-SNAPSHOT -DgroupId=org.acme -DartifactId=php5-web -Dversion=0.1.2-SNAPSHOT -Dpackage=org.acme.lib -DinteractiveMode=false


"C:\Program Files (x86)\Maven\maven-3.6.0\bin\mvn" archetype:generate -DarchetypeGroupId=de.slothsoft.phpmaven -DarchetypeArtifactId=php5-zend-archetype -DarchetypeVersion=0.9.0-SNAPSHOT -DgroupId=org.acme -DartifactId=php5-zend -Dversion=0.1.2-SNAPSHOT -Dpackage=org.acme.lib -DinteractiveMode=false


# Table of Contents

- [Checklist](#checklist) 



# Checklist

Those being already familiar with Maven should read the following list:

- Use the existing archetypes
- Use packaging "php" with build extension `${mavenPluginGroupId}:${mavenPluginArtifactId}` to create a PHP project
- See the configuration below for controlling the behavior of Maven for PHP
- See the mojo report for details on `${mavenPluginArtifactId}` mojos



# Configuration Overview

As the most Maven plug-ins the configuration is placed within the build section of your _pom.xml_. Depending on the configuration option you want to change add the following to your POM:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>${mavenPluginGroupId}</groupId>
			<artifactId>${mavenPluginArtifactId}</artifactId>
			<extensions>true</extensions>
			
			<configuration>
				<configurationOption>ConfigurationValue</configurationOption>
			</configuration>
		</plugin>
	</plugins>
</build>
```

The versions are managed by the POM parent: org.phpmaven:php-parent-pom:2.0.2. All plugins in php-maven have the same version number. So if you use version 2.0.2 of maven-php-plugin you should use the same version on all other maven-php plugins to have a compatible setup.
Configuration - PHP.EXE

At first you should choose the best plugin to influence the php.exe. See the following hints:

- maven-php-plugin will influence the php.exe configuration for some of the goals. Not all goals support setting the php executable configuration.
- maven-php-xxxx will influence the php.exe for all tasks this plugin is performing. For example maven-php-phpunit will influence the executable configuration for phpunit invocations.
- maven-php-project will influence the php.exe for each project invocation. Project invocations are each phpunit tests and each invocation to a cli script.
- maven-php-exec will influence the php.exe for everything. Be careful to use this because if you do some mistake php-maven will not be working any more. Even the packing of phar files may fail.

As you see we have several locations for setting the php.exe. You should always be careful where to set command line options or additional defines. If you only need them for phpunit then use the phpunit plugin for configuration.

PHP-Maven assumes that you find PHP.EXE on path variable. However this may be a problem on your system or you want to use an alternative php.exe. In this case you should use the maven-php-exec configuration and change the phpExecutable.
maven-php-exec

See javadoc for details. This configuration is used for every invocation of php (including phar and pear). see the following example:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-exec</artifactId>
				
				<configuration>
					<executable>/path/to/your/php</executable>
					<errorReporting>E_ALL &amp;!E_NOTICE</errorReporting>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

This example uses a different executable and overwrites the error_reporting for any php invocation.
maven-php-project

See javadoc for details. This configuration is used for every invocation of php for project related stuff; currently php cli invocations and phpunit invocations. See the following example to add a custom include path:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-project</artifactId>
				
				<configuration>
					<executableConfig>
						<includePath>
							<path>${project.basedir}/thirdparty</path>
						</includePath>
					</executableConfig>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

# maven-php-pear

See javadoc for details. This configuration is used for every invocation of pear packages. Notice: You do not need to configure anything for pear if you are using pear project as dependencies. Pear packages found in the official repository are simple php projects. See the following example to change the pear installation folder:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-pear</artifactId>
				
				<configuration>
					<installDir>${project.basedir}/target/pear-install</installDir>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

# maven-php-phpunit

See javadoc for details. This configuration is used for every phpunit invocation. See the following example to add a bootstrap file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-phpunit</artifactId>
				
				<configuration>
					<executableConfig>
						<additionalPhpParameters>-d auto_prepend_file=phpunit-autoloading.php</additionalPhpParameters>
					</executableConfig>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

# maven-php-phpdoc

See javadoc for details. This configuration is used for every phpdoc report generation. See the following example for setting the phpdoc version that will be used (downloads phpdoc from maven).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-phpdoc</artifactId>
				
				<configuration>
					<phpdocVersion>1.4.4</phpdocVersion>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

See the following example for using the phpdoc from command line (your local pear installation should be on the path variable).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-phpdoc</artifactId>
				
				<configuration>
					<phpdocService>PHP_EXE</phpdocService>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

#maven-php-phar

See javadoc for details. This configuration is used for every phar invocation for both, packing phar archives and unpacking phar archives. See the following example for setting a custom stub file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	...
	<build>
		<plugins>
			<plugin>
				<groupId>org.phpmaven</groupId>
				<artifactId>maven-php-phar</artifactId>
				
				<configuration>
					<pharConfig>
						<stub>
echo "***** ${artifactId} V${version}".PHP_EOL;
echo "***** starting application...";
function __autoload($class)
{
    include 'phar://me.phar/' . str_replace('_', '/', $class) . '.php';
}
try {
    Phar::mapPhar('me.phar');
    include 'phar://me.phar/startup.php';
} catch (PharException $e) {
    echo $e->getMessage();
    die('Cannot initialize Phar');
}
						</stub>
					</pharConfig>
				</configuration>
			</plugin>
		</plugins>
	</build>
	...
</project>
```

