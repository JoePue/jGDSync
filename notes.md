
# Google Drive API
## Guides
* Performance Tips, https://developers.google.com/drive/v3/web/performance
* Batching Requests, https://developers.google.com/drive/v3/web/batch
* Turn on the Drive API, https://developers.google.com/drive/api/v3/quickstart/java

## Reference
* Files, https://developers.google.com/drive/v3/reference/files
* Ascii Table, http://www.torsten-horn.de/techdocs/ascii.htm
* Box Drawing, http://jrgraphix.net/r/Unicode/2500-257F

# Link summary
* printf, http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_04_010.htm

### Gradle properties
* gradle init --type java-library
```
C:\Users\joerg.puettner\.gradle\wrapper\dists\gradle-2.9-all\1aw2ic01pldw5fkvoq6t1fsz4\gradle-2.9\samples\signing\maven\gradle.properties
C:\Users\joerg.puettner\.gradle\wrapper\dists\gradle-2.9-all\1aw2ic01pldw5fkvoq6t1fsz4\gradle-2.9\samples\userguide\tutorial\properties\gradle.properties
E:\dev-tools\groovy\groovy-2.4.7\src\gradle.properties
```

## Bug
````
[Error] An error occurred at command execution. error: The user has not granted the app 886573689157 read access to the file 1Vr5S-kAiWIhbSBHnrrnrCGxO506bDbEP.
````
### Apps mit Zugriff auf mein Konto
* https://myaccount.google.com/permissions?pli=1


# Todo
* Implement cache clean commmand
* Implement download command
* Implement upload command
* Implement ls command
* Implement lazy loading of folders for recursive ls command
* Define synchronisation stages
  * Init, Diff, Download, Upload


