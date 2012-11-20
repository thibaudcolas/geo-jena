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
	
	public static final String codesCountSelect = "?scheme (count(?code) AS ?count";
	public static final String codesCountVariables = "?scheme ?count";
	public static final String codesCountWhere = "?code core:inScheme ?scheme";
	
	public static final String iceRegexVariables = "?code ?definition";
	public static final String iceRegexWhere = "?code a gn:Code . ?code core:definition ?definition FILTER regex(?definition, \"ice\")"; 
	
	int initializeExplorer();
	String explorerServer(String name) throws IllegalArgumentException;
	String writeQuery(String variables, String where) throws IllegalArgumentException;
	
	LinkedList<LinkedList<String>> retrieveResult(String variables, String where);
}
