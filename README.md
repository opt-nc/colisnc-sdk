[![](https://jitpack.io/v/opt-nc/colisnc-sdk.svg)](https://jitpack.io/#opt-nc/colisnc-sdk)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)
![Build](https://github.com/opt-nc/colisnc-sdk/actions/workflows/maven-build.yml/badge.svg)

# :grey_question:colisnc-sdk

SDK Java pour suivre suivre les colis en Nouvelle-calédonie, se base sur les données de [la page de suivi des envois de OPT-NC](https://webtracking-nca.ptc.post/).

# :point_right:Utiliser


## 🚀(J)Bang! sur les colis !

Pour une expérience optimale depuis le votre shell qui vous permettra de trouver
le dernier status d'un colis.

```shell
jbang alias add --name colis-nc https://github.com/opt-nc/colisnc-sdk/blob/master/jbang.java
# Check des alias
jbang alias list
# Appel de excuses
jbang colis-nc -c 8Z00136833343
```

Pour mettre à jour le cache Jbang (et récupérer la toute dernière version) :

```shell
jbang cache clear
```

## :heavy_check_mark:Ajout de la dépendance `maven`

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```

puis :

```xml
<dependency>
    <groupId>com.github.opt-nc</groupId>
    <artifactId>colisnc-sdk</artifactId>
    <version>Tag</version>
</dependency>
```



## :heavy_check_mark:Ajout de la dépendance `gradle`

```yaml
allprojects {
    repositories {
    ...
    maven { url 'https://www.jitpack.io' }
    }
}
```

puis :

```yaml
dependencies {
    implementation 'com.github.opt-nc:Tag'
}
```

## :rocket:Code snippet

```java
ArrayList<ColisDataRow> coliadDetails = ColisCrawler.getColisRows(itemId);
System.out.println("Got <" + coliadDetails.size() + "> details pour <" + itemId + ">");
System.exit(0);
```
