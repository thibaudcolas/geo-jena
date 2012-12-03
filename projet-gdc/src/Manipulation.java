/**
 * Cette classe est contrôleur de l'application
 * @author mathilde
 *
 */

public class Manipulation {
	
	private Requete[] requetes = new Requete[12];
	private Modele modele;
	private Vue vue;
	
	
	public Vue getVue() {
		return this.vue;
	}
	public Modele getModele() {
		return this.modele;
	}

	
	public Requete[] getRequete() {
		return this.requetes;
	}
	
	
	public Manipulation(Modele m) {
		this.modele = m;	
		this.vue = new Vue(this);
		 m.addObserver(this.vue);
		 // toutes les requêtes possibles
		this.requetes[1] = new Requete("SELECT ?sousClasses  WHERE { ?sousClasses rdfs:subClassOf <http://xmlns.com/foaf/0.1/Document> . }");
		this.requetes[2] = new Requete(" SELECT  ?p  WHERE { " +
			 	"  ?p  rdfs:domain <http://www.geonames.org/ontology#Feature> ." +
			 	"  ?p  rdfs:range <http://www.geonames.org/ontology#Feature> . " +
			 	"  } LIMIT 100");
		this.requetes[3] = new Requete(" SELECT DISTINCT ?prop WHERE { " +
			 	" ?prop rdf:type owl:DatatypeProperty ." +
			 	" ?prop rdfs:domain <http://www.geonames.org/ontology#Feature> .  }");
		this.requetes[4] = new Requete(" SELECT  ?def  WHERE { " +
			 	"    <http://www.geonames.org/ontology#H.ANCH> core:definition ?def . " +
			 	"   } LIMIT 100");
		this.requetes[5] = new Requete(" SELECT DISTINCT ?code  WHERE { " +
			 	"   ?code rdf:type <http://www.geonames.org/ontology#Code>  . " +
			 	"   ?code core:inScheme <http://www.geonames.org/ontology#A>  . } LIMIT 100");
		this.requetes[6] = new Requete(" SELECT DISTINCT ?code ?label ?def ?class  WHERE { " +
			 	"  ?code rdf:type <http://www.geonames.org/ontology#Code>  . " +
			 	"  ?code core:definition ?def . " +
			 	"  ?code core:prefLabel ?label ." +
			 	"  ?code rdf:type ?class  . } LIMIT 100");
		this.requetes[7] = new Requete(" SELECT DISTINCT ?code ?def WHERE { " +
			 	" ?code core:inScheme <http://www.geonames.org/ontology#V> . " +
			 	" ?code core:definition ?def .} LIMIT 100");
		this.requetes[8] = new Requete(" SELECT ?clas (count(?code) as ?nombre) WHERE { " +
			 	" ?code rdf:type <http://www.geonames.org/ontology#Code> ." +
			 	" ?code core:inScheme ?clas . } GROUP BY ?clas ");
		this.requetes[9] = new Requete(" SELECT DISTINCT ?code ?def WHERE { " +
			 	" ?code rdf:type <http://www.geonames.org/ontology#Code> . " +
			 	" ?code core:definition ?def ." +
			 	" FILTER regex(str(?def) , \"ice\", \"i\") . }  LIMIT 100");
		this.requetes[10] = new Requete(" SELECT ?s ?p ?o  WHERE { ?p rdf:type owl:DatatypeProperty . ?s ?p ?o . " +
		"FILTER regex(str(?o), \"^[0-9]+$\") } LIMIT 100");
		this.requetes[11] = new Requete(" SELECT DISTINCT ?codePostal ?population ?latitude ?longitude ?class ?commentaireClass " +
				"   WHERE { ?s <http://www.geonames.org/ontology#name> \"Mont-de-Marsan\". " +
				"	?s <http://www.geonames.org/ontology#postalCode> ?codePostal ." +
				"	?s <http://www.geonames.org/ontology#population> ?population ." +
				"   ?s <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?latitude ." +
				"   ?s <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?longitude ." +
				"	?s <http://www.geonames.org/ontology#featureClass> ?class . " +
				"	?class rdfs:comment ?commentaireClass . " +
				"	} LIMIT 100");
		
	}
	
	// l'utilisateur a choisi une requête, le modèle est interrogé par le contrôleur
	public void selectionnerRequete(int demande) {
		if (demande != 0) {
			 modele.interrogerModele(requetes[demande]);
		} 
	}
	
	
	
	
}
