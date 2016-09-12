package com.github.thibaudcolas.geojena.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service RPC principal qui écrit / exécute les requêtes et renvoie leurs réponses.
 */
@RemoteServiceRelativePath("greet")
public interface ExplorerService extends RemoteService {
	// Req1. Pretty simple.
	String allCodesSelect = "?code ?scheme ?label ?definition";
	String allCodesWhere = "?code a gn:Code . ?code core:inScheme ?scheme . ?code core:prefLabel ?label . ?code core:definition ?definition";

	// Req2. Even simpler.
	String vegeCodesSelect = "?code ?definition";
	String vegeCodesWhere = "?code core:inScheme gn:V . ?code core:definition ?definition";

	// Req3. Beware the power of count.
	String codesCountSelect = "?scheme (count(DISTINCT ?code) AS ?count)";
	String codesCountWhere = "?code core:inScheme ?scheme";

	// Req4. Power comes with a price.
	String iceRegexSelect = "?code ?definition";
	String iceRegexWhere = "?code a gn:Code . ?code core:definition ?definition FILTER regex(str(?definition), 'ice')";

	// Req5. On limite aux lieux peuplés (#P.PPL) de France ('FR')
	String moleculeSelect = "?name ?pop ?postalcode ?label";
	String moleculeWhere = "?feature a gn:Feature . ?feature gn:name ?name . ?feature gn:population ?pop . ?feature gn:postalCode ?postalcode . ?feature gn:countryCode \"FR\" . ?feature gn:featureCode gn:P.PPL . gn:P.PPL core:prefLabel ?label";

	// Req6. Les DatatypeProperties contenant 'name' dans les commentaires sont name, shortName, alternateName, officialName.
	String spJointSelect = "?prop ?obj";
	String spJointWhere = "?prop a owl:DatatypeProperty . ?prop rdfs:comment ?com . ?suj ?prop ?obj FILTER regex(str(?com), 'name', 'i')";

	String writeQuery(String select, String where);

	LinkedList<LinkedList<String>> retrieveResult(String select, String where);
}
