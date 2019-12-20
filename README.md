# talentRate

## DataBase

### Create an index
Use Elastic API : ``PUT /eval6``


### using this mappings
**Warning** to create an index do not forget the **'s'** at mappings !

```Json
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

This file should be simillar to the __src/main/ressources/application.properties__.


### Properties configuration sample : 

```sh
#The Front-End URL
fe-info.cross = http://localhost:4200
#Your ElasticSearch API URL
db-info.url=localhost
#Your ElasticSearch API PORT
db-info.port=9200
#Your ElasticSearch API protocol
db-info.protocol=http
#ElasticSearch index name to store evals
db-info.index=eval6
#Your ElasticSearch Users (empty if no authentification required)
db-info.user=
#Your ElasticSearch Password (empty if no authentification required)
db-info.password=


##toDisable KeyCloak, use ONLY in DEV environement !
#spring.autoconfigure.exclude = org.keycloak.adapters.springboot.KeycloakAutoConfiguration
#keycloak.enabled=false

#Keycloak: Name of the realm. REQUIRED.
keycloak.realm = talentraterealm
#Keycloak:The base URL of the Keycloak server. All other Keycloak pages and REST service endpoints are derived from this. REQUIRED.
keycloak.auth-server-url = http://127.0.0.1:8888/auth
#Keycloak: Ensures that all communication to and from the Keycloak server is over HTTPS. OPTIONAL
keycloak.ssl-required = external
#Keycloak: The client-id of the application. Each application has a client-id that is used to identify the application. REQUIRED.
keycloak.resource = TalentRateCli
#Keycloak: Secret credential for application authentication to Keycloak server.
keycloak.credentials.secret=ddc80455-d9e4-4fea-96cd-229a8c74c336
# ??
#keycloak.use-resource-role-mappings=false

keycloak.securityConstraints[0].authRoles[0]=prof
keycloak.securityConstraints[0].authRoles[1]=etudiant
keycloak.securityConstraints[0].securityCollections[0].name=consulterEval
keycloak.securityConstraints[0].securityCollections[0].patterns[0]=/eval/

keycloak.securityConstraints[1].authRoles[0]=prof
keycloak.securityConstraints[1].securityCollections[0].name=gererEval
keycloak.securityConstraints[1].securityCollections[0].patterns[0]=/eval/
```


### Custom Front-end
for apache Proxy : configure the front-end url to allow cross origin configuration 
```
#The Front-End URL
fe-info.cross=http://talentrate.clicketcloud.com
```


#Data Samples

To add an evaluation : 

```Json
{
"school":"TestSchool1", 
"module":"Java", 
"promotion":"Avril2019", 
"category":"CategorieC", 
"skill":"Skill5", 
"homework":"Homework1", 
"student": {
	"name":"Student1"
}, 
"score":12, 
"obtainable":20,
"given":"2019-12-14T10:58:39"
}
```

#Client Library
##Java
A Java client library is aviallable here : <https://github.com/jderuette/TalentRate_clientLibrary>

