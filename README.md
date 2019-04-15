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
for apache Proxy add 
```
#Your ElasticSearch API URL
db-info.url=
#Your ElasticSearch API PORT
db-info.port=9243
#Your ElasticSearch API protocol
db-info.protocol=https
#ElasticSearch index name to store evals
db-info.index=tr_eval 
#Your ElasticSearch Users (empty if no authentification required)
db-info.user=tr
#Your ElasticSearch Password (empty if no authentification required)
db-info.password=pass
#The Front-End URL
fe-info.cross=http://talentrate.clicketcloud.com
```

