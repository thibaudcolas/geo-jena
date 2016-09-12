package com.github.thibaudcolas.geojena.server;

import java.util.LinkedList;

import com.github.thibaudcolas.geojena.client.ExplorerService;
import com.github.thibaudcolas.geojena.server.helper.QueryHelper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implémentation du service d'écriture / exécution des requêtes.
 */
@SuppressWarnings("serial")
public class ExplorerServiceImpl extends RemoteServiceServlet implements ExplorerService {

	// Écriture de requêtes simplifiée.
	public String writeQuery(String select, String where) {
		return QueryHelper.writeQuery(select, where, 100);
	}

	// Envoi de requêtes simplifié.
	public LinkedList<LinkedList<String>> retrieveResult(String select, String where) {
		return QueryHelper.retrieveResult(select, where);
	}
}
