[![](https://jitpack.io/v/opt-nc/colisnc-sdk.svg)](https://jitpack.io/#opt-nc/colisnc-sdk)

# colisnc-sdk
SDK pour les colis en Nouvelle-calédonie

# Utiliser


## Ajout de la dépendance (maven)

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



## Ajout de la dépendance (gradle)

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

## Code snippet


```java
ArrayList<ColisDataRow> coliadDetails = ColisCrawler.getColisRows(itemId);
System.out.println("Got <" + coliadDetails.size() + "> details pour <" + itemId + ">");
System.exit(0);
```
