package com.github.thibaudcolas.geojena.server;

import com.github.thibaudcolas.geojena.client.InitService;
import com.github.thibaudcolas.geojena.server.helper.QueryHelper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Ce servlet charge les données dans le modèle.
 */
@SuppressWarnings("serial")
public class InitServiceImpl extends RemoteServiceServlet implements InitService {

	public void init() {
		QueryHelper.readModel("rdf/ontology_v2.rdf");
		QueryHelper.readModel("rdf/embrun.rdf");
		// Attention : agglomération de Montpellier et non ville de Montpellier.
		QueryHelper.readModel("rdf/montpellier.rdf");
		QueryHelper.readModel("rdf/saint-felix.rdf");
	}
}
