For a detailed overview of all the updates done to this fork (or these forks), take a look at the [Git history](https://github.com/slothsoft/maven-php-plugin/commits/master).

The last comit of [php-maven](https://github.com/php-maven) was on November 22, 2012, the first of [slothsoft](https://github.com/slothsoft) on March 14, 2019. 

This document contains:

- [Merged Repositories](#merged-repositories) - the repositories that were merged into this fork
- [Notable Other Changes](#notable-other-changes) - a list of general changes that were done



# Merged Repositories

This repository is a fork of <https://github.com/php-maven/maven-php-plugin>. Additionally, the following repositories were merged into it as well:

- <https://github.com/php-maven/phpmaven-common-parent>
- <https://github.com/php-maven/phpmaven-var-parent>
- <https://github.com/php-maven/phpexec-java>
- <https://github.com/php-maven/pear-java>

The changes to them are visible in the [Git history](https://github.com/slothsoft/maven-php-plugin/commits/master) as well.

The reasons for merging all these repositories are

- there is no plan to maintain these projects individually
- in fact there is no plan to maintain anything; it should just work as before
- multiple repositories mean problematic changes will do be noticed later in the pipeline - and by then it might be to late to find out what was the change that broke the build



## Merge Script

This command line script was used to merge the repositories into one.

<details><summary>Merge Script</summary>
<p>

```batch
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
 
:: Now move to the project all the repositories should be moved to
cd../maven-php-plugin/
:: Add the other directory as a branch and pull
git remote add repo-branch ../%repository%
git pull repo-branch master --allow-unrelated-histories
git remote rm repo-branch
  
:: Clean up and delete the created folder
cd..
rmdir %repository% /Q /s
goto:eof
```

</p>
</details>



# Notable Other Changes

_You can review [Milestone 0.9.0](https://github.com/slothsoft/maven-php-plugin/milestone/1?closed=1) to see all issues._

| Change        | Reason        |
| ------------- | ------------- |
| Removed Cobertura and checkstyle  | no real code changes are planned and if they are, these tools or similar ones will be enabled again  |
| removed `maven-site-plugin`  | there will be no custom website for this plug-in, only this wiki  |
| removed distribution management  | these plug-ins will be released into Maven Central in the future  |
| decluttered all the _pom.xml_ files <br> (removed everything I don't know or care for)  | to make them easier to maintain / review  |
| modified Maven parent structure  | makes versions easier to maintain, since they're supposed to be stored in one single file (the uppermost _pom.xml_)  |
| changed version to 0.9  | these projects need a re-release, but something might have been broken during the update process, so it's not a 1.0.0 (yet)  |
| changed group ID  | this project should be released to Maven Central, but Slothsoft can only release into the group `de.slothsoft` per definition  |
| added `@author` | to comply with the [Apache License 2.0](https://github.com/php-maven/phpmaven-common-parent/blob/master/LICENSE) 4 (b) _"You must cause any modified files to carry prominent notices stating that You changed the files."_ | 
| added [archetype examples](https://github.com/slothsoft/maven-php-plugin/tree/master/examples) and a [generator for them](https://github.com/slothsoft/maven-php-plugin/tree/master/archetypes/generate-examples) | to link to them from the documentation; for change traceability; as blueprint for creating new projects; to debug archetypes  |

