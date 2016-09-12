package com.github.thibaudcolas.geojena.client;

import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Version asynchrone du service principal.
 */
public interface ExplorerServiceAsync {

	void writeQuery(String select, String where, AsyncCallback<String> callback);

	void retrieveResult(String select, String where, AsyncCallback<LinkedList<LinkedList<String>>> callback);
}
