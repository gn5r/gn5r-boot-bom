# Usage

## Clone This Repository

`git clone https://github.com/gn5r/gn5r-boot.git`

`mvn install`

## Create Java Project Using This Archetype

```
mvn archetype:generate -DarchetypeGroupId=com.github.gn5r \
-DarchetypeArtifactId=gn5r-spring-boot-archetype \
-DarchetypeVersion=1.0.0-SNAPSHOT \
-DgroupId=your project groupId \
-DartifactId=your project artifactId \
-Dname=your Application Name \
-Dversion=your project version \
-Dgn5r-boot-version=specify gn5r-spring-boot version \
-Dspring-boot-version=specify spring-boot version
```

# Properties

|        name         |         discription          |                  defaultValue                  |
| :-----------------: | :--------------------------: | :--------------------------------------------: |
|       groupId       |       Project GroupId        |                       -                        |
|     artifactId      |      Project ArtifactId      |                       -                        |
|       version       |       Project version        |                 1.0.0-SNAPSHOT                 |
|      packaging      |      Project packaging       |                      jar                       |
|       package       |     Project package FQCN     | \${groupId}.${artifactId.replaceAll("-", ".")} |
|        name         | Prefix for Application Class |                       -                        |
|  gn5r-boot-version  |   gn5r-spring-boot version   |                 1.0.0-SNAPSHOT                 |
| spring-boot-version |  spring-spring-boot version  |                     2.5.14                     |
