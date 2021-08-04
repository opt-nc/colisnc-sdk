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
    <groupId>com.github.adriens</groupId>
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
    implementation 'com.github.adriens:colisnc-sdk:Tag'
}
```

## Code snippet


```java
ArrayList<ColisDataRow> coliadDetails = ColisCrawler.getColisRows(itemId);
System.out.println("Got <" + coliadDetails.size() + "> details pour <" + itemId + ">");
System.exit(0);
```
