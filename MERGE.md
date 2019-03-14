
merged from

https://github.com/php-maven/maven-php-plugin

https://github.com/php-maven/phpmaven-common-parent
https://github.com/php-maven/phpmaven-var-parent
https://github.com/php-maven/phpexec-java
https://github.com/php-maven/pear-java






call:moveRepositoryToSubfolder "php-maven" "phpmaven-var-parent"
call:moveRepositoryToSubfolder "php-maven" "phpmaven-common-parent"
call:moveRepositoryToSubfolder "php-maven" "phpexec-java"
call:moveRepositoryToSubfolder "php-maven" "pear-java"
goto:eof
  
:: This a function
:moveRepositoryToSubfolder
SET user=%~1
SET repository=%~2
 
:: Clone the repository that should be moved
git clone https://github.com/%user%/%repository%
cd %repository%
:: Remove the origin so we don't push on accident
git remote rm origin
 
:: Move everything into a subfolder and commit
mkdir %repository%
move ./* %repository%
FOR /D %%G IN (*) DO IF NOT (%%G==%repository%) move %%G %repository%
git add .
git commit -m "moved %repository% into its own folder"
 
:: Now moe to the project all the repositories should be moved to
cd../maven-php-plugin/
:: Add the other directory as a branch and pull
git remote add repo-branch ../%repository%
git pull repo-branch master --allow-unrelated-histories
git remote rm repo-branch
  
:: Clean up and delete the created folder
cd..
rmdir %repository% /Q /s
goto:eof