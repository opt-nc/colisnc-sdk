[![](https://jitpack.io/v/opt-nc/colisnc-sdk.svg)](https://jitpack.io/#opt-nc/colisnc-sdk)

# :grey_question:colisnc-sdk

SDK Java pour suivre suivre les colis en Nouvelle-calédonie, se base sur les données de [la page de suivi des envois de OPT-NC](https://webtracking-nca.ptc.post/).

# :point_right:Utiliser


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
