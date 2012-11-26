package com.github.thibweb.geojena.server;

import com.github.thibweb.geojena.client.InitService;
import com.github.thibweb.geojena.server.helper.QueryHelper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class InitServiceImpl extends RemoteServiceServlet implements InitService {
	
	public void init() {
		QueryHelper.readModel("rdf/ontology_v2.rdf");
	}
}
