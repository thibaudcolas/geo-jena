package com.github.thibweb.geojena.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ExplorerService</code>.
 */
public interface ExplorerServiceAsync {

	void writeQuery(String variables, String where, AsyncCallback<String> callback);
	
	void writeQuery(String variables, String where, String select, AsyncCallback<String> callback);
	
	void retrieveResult(String variables, String where, AsyncCallback<LinkedList<LinkedList<String>>> callback);
	
	void retrieveResult(String variables, String where, String select, AsyncCallback<LinkedList<LinkedList<String>>> callback);
}
