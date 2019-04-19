There was a lot of trouble to get PEAR to work with a current PHP version.

The [old PEAR file "go-pear.phar"](https://github.com/slothsoft/maven-php-plugin/blob/522fd716233e028cb1dca30d7fce126b191ad3ca/pear-java/pear-java-impl/src/main/resources/org/phpmaven/pear/library/impl/go-pear.phar) was supposedly version 1.0.0 and didn't work with PHP 7, while the current PEAR version is 1.1.0 and doesn't work with PHP 5.3.

It didn't help that some PHP versions had stubs for downloading and installing PEAR, while others (especially Windows versions) didn't.

To fix this, [multiple PHP versions](https://windows.php.net/downloads/releases/archives/) where tested to see which PHP version worked with which PEAR version:

| PHP        | PEAR 1.0.0    | PEAR 1.1.0    |
| -----------| ------------- | ------------- |
| 5.3.0      | ✅             | ✖             |
| 5.4.0      | ✅             | ✅             |
| 5.6.40     | ✖             | ✅             |
| 7.3.4      | ✖             | ✅             |

The **conclusion** from these tests was: the minimum supported PHP version is 5.4.
