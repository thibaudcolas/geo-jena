# GeoJena Explorer : Exploration de l'ontologie GeoNames avec Jena ##

Cette application est une expérimentation réalisée sur l'ontologie GeoNames de classification de lieux (features).

Adresse de l'application : [http://geo-jena.appspot.com/](http://geo-jena.appspot.com/)

## Fonctionnalités ##

GeoJena est constituée d'une unique page divisée en plusieurs onglets. Les trois premiers onglets sont des réponses aux questions du projet, et les six suivantes sont des requêtes SPARQL.

Pour chaque requête, on affiche le contenu exact de la requête et ses résultats.

## Architecture ##

GeoJena a été construit avec Jena et GWT (Google Web Toolkit), un framework Java de développement web. GWT divise le code en deux parties :

* Client : du code Java converti en JavaScript à la compilation, qui sera chargé d'appeler le serveur en AJAX (asynchrone).
* Serveur : un ou plusieurs services web écrits en Java qui vont être appelés (REST) par un ou plusieurs clients.

### Client ###

Le code client est situé dans la classe GeoJena, contenant la méthode onModuleLoad() qui lance les appels côté client.

Exemple tiré de GeoJena.java :  

```java
explorerService.writeQuery(ExplorerService.moleculeSelect, ExplorerService.moleculeWhere, new AsyncCallback<String>() {
  public void onFailure(Throwable caught) {
    moleculeQuery.setText(caught.toString());
  }
  public void onSuccess(String query) {
    moleculeQuery.setText(query);
  }
});
```

### Serveur ###

Le code serveur est constitué de deux services web :

* InitService, un service lancé au démarrage de l'application pour charger les fichiers RDF dans le modèle Jena.
* ExplorerService, le service principal qui génère et exécute les requêtes.

Ces deux services utilisent les fonctions d'une classe QueryHelper qui simplifie l'usage de l'API Jena ARQ. On y utilise principalement les méthodes writeQuery() et retrieveResults().

Exemple tiré de QueryHelper.java :  

```java
// Lance une requête à l'aide de l'API Jena ARQ.
public static ResultSet sendQuery(String queryString) {
  LOG.info("Query : " + queryString);
  // On précise la syntaxe SPARQL 1.1 pour profiter des capacités de ARQ.
  Query q = QueryFactory.create(queryString, Syntax.syntaxSPARQL_11);
  QueryExecution ex = QueryExecutionFactory.create(q, m);
  ResultSet rs = ex.execSelect();
  return rs;
}
```

## Hébergement ##

GeoJena est hébergé sur le [Google App Engine](https://developers.google.com/appengine/), un PaaS proposant d'héberger des applications Java, Go et Python.
