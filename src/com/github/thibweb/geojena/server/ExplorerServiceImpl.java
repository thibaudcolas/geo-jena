package com.github.thibweb.geojena.server;

import java.util.LinkedList;

import com.github.thibweb.geojena.client.ExplorerService;
import com.github.thibweb.geojena.server.helper.QueryHelper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ExplorerServiceImpl extends RemoteServiceServlet implements ExplorerService {
	
	public String writeQuery(String variables, String where) {
		return QueryHelper.writeQuery(variables, where, 100);
	}
	
	public String writeQuery(String variables, String where, String select) {
		return QueryHelper.writeQuery(select, where) + QueryHelper.NL + "GROUP BY " + select.split(" ")[0];
	}
	
	public LinkedList<LinkedList<String>> retrieveResult(String variables, String where) {
		return QueryHelper.retrieveResult(variables, where);
	}
	
	public LinkedList<LinkedList<String>> retrieveResult(String variables, String where, String select) {
		return QueryHelper.retrieveResult(variables, where, select);
	}
}
