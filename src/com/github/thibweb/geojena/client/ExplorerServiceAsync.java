package com.github.thibweb.geojena.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ExplorerService</code>.
 */
public interface ExplorerServiceAsync {
	
	void initializeExplorer(AsyncCallback<Integer> callback);
	
	void explorerServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void writeQuery(String variables, String where, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void retrieveResult(String variables, String where, AsyncCallback<LinkedList<LinkedList<String>>> callback);
}
