package com.github.thibweb.geojena.server;

import java.util.LinkedList;

import com.github.thibweb.geojena.client.ExplorerService;
import com.github.thibweb.geojena.server.helper.QueryHelper;
import com.github.thibweb.geojena.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ExplorerServiceImpl extends RemoteServiceServlet implements ExplorerService {
	
	public int initializeExplorer() {
		return QueryHelper.readModel("ontology_v2.rdf");
	}

	public String explorerServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent
				+ "<br>Oh by the way:<br> " + RDF.getURI();
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	public String writeQuery(String variables, String where) throws IllegalArgumentException {
		return QueryHelper.writeQuery(variables, where, 100);
	}
	
	public LinkedList<LinkedList<String>> retrieveResult(String variables, String where) {
		return QueryHelper.retrieveResult(variables, where);
	}
}
