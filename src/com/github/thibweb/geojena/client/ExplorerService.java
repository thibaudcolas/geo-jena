package com.github.thibweb.geojena.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ExplorerService extends RemoteService {
	public static final String allCodesVariables = "?code ?scheme ?label ?definition";
	public static final String allCodesWhere = "?code a gn:Code . ?code core:inScheme ?scheme . ?code core:prefLabel ?label . ?code core:definition ?definition";
	
	public static final String vegeCodesVariables = "?code ?definition";
	public static final String vegeCodesWhere = "?code core:inScheme gn:V . ?code core:definition ?definition";
	
	public static final String codesCountSelect = "?scheme (count(DISTINCT ?code) AS ?count)";
	public static final String codesCountVariables = "?scheme ?count";
	public static final String codesCountWhere = "?code core:inScheme ?scheme";
	
	public static final String iceRegexVariables = "?code ?definition";
	public static final String iceRegexWhere = "?code a gn:Code . ?code core:definition ?definition FILTER regex(str(?definition), 'ice')"; 
	
	public static final String moleculeVariables = "?name ?pop ?postalcode ?label";
	public static final String moleculeWhere = "?feature a gn:Feature . ?feature gn:name ?name . ?feature gn:population ?pop . ?feature gn:postalCode ?postalcode . ?feature gn:countryCode \"FR\" . ?feature gn:featureCode gn:P.PPL . gn:P.PPL core:prefLabel ?label";
	
	public static final String spJointVariables = "?prop ?obj";
	public static final String spJointWhere = "?prop a owl:DatatypeProperty . ?prop rdfs:comment ?com . ?suj ?prop ?obj";

	String writeQuery(String variables, String where);
	
	String writeQuery(String variables, String where, String select);
	
	LinkedList<LinkedList<String>> retrieveResult(String variables, String where);
	
	LinkedList<LinkedList<String>> retrieveResult(String variables, String where, String select);
}
