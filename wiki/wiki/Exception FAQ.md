
# Channel "pear.php.net" is not responding

```
Channel "pear.php.net" is not responding over http://, failed with message: Connection to `ssl://pear.php.net:443' failed: Unable to find the socket transport "ssl" - did you forget to enable it when you configured PHP?
```

<details><summary>Entire Stacktrace...</summary>
<p>

```
Updating channel "pear.php.net"
Channel "pear.php.net" is not responding over http://, failed with message: Connection to `ssl://pear.php.net:443' failed: Unable to find the socket transport "ssl" - did you forget to enable it when you configured PHP?
Trying channel "pear.php.net" over https:// instead
Cannot retrieve channel.xml for channel "pear.php.net" (Connection to `ssl://pear.php.net:443' failed: Unable to find the socket transport "ssl" - did you forget to enable it when you configured PHP?)

	at org.phpmaven.phpexec.cli.PhpExecutable.execute(PhpExecutable.java:306)
	at org.phpmaven.phpexec.cli.PhpExecutable.execute(PhpExecutable.java:318)
	at org.phpmaven.phpexec.cli.PhpExecutableConfiguration$CachedExecutable.execute(PhpExecutableConfiguration.java:340)
	at org.phpmaven.pear.library.impl.PearUtility.executePearCmd(PearUtility.java:280)
	at org.phpmaven.pear.library.impl.Package.install(Package.java:196)
	at org.phpmaven.pear.library.impl.PackageVersion.install(PackageVersion.java:1059)
	at org.phpmaven.pear.library.test.BaseTest.testPackageInstallation(BaseTest.java:263)
```

</p>
</details>

How to fix this problem:

1. Find out where your _php.ini_ is located (either by knowing or by using `phpinfo();` or `echo php_ini_loaded_file();`)
2. Open it or copy a new one into the folder (the PHP folder usually contains blueprints like _php.ini-production_)
3. Search for `;extension=php_openssl.dll` and remove the **;** that comments the line out  
4. If PHP is not in the root folder, you might have to change the definition of _php_openssl.dll_ to point to the correct folder 
5. Rerun the code that produced the above error

