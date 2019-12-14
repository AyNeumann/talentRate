# talentRate

## Required elastic mapping:
*** Warning to creeate an index do not forget the 's' at mappings ! ***
```
{
  "mappings": {
    "_doc": {
      "properties": {
        "category": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "homework": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "module": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "obtainable": {
          "type": "short",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "promotion": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "school": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "score": {
          "type": "short",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "skill": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword",
              "ignore_above": 256
            }
          }
        },
        "student": {
          "type": "nested",
          "dynamic": "true",
          "properties": {
            "name": {
              "type": "text",
              "fields": {
                "keyword": {
                  "type": "keyword",
                  "ignore_above": 256
                }
              }
            }
          }
        }
      }
    }
  }
}

```


## Config

To use custom configuration : ``java -jar server-0.0.1-SNAPSHOT.jar --spring.config.location=file:xxxxx.properties``

More info about properties with SpringFramework here : https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-application-property-files.

This file should be simillar to the src/main/ressources/application.properties.

### Custom Front-end
for apache Proxy configure the front-end url to allow corss origin configuration 
```
#The Front-End URL
fe-info.cross=http://talentrate.clicketcloud.com
```
