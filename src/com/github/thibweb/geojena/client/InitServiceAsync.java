package com.github.thibweb.geojena.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Pendant asynchrone du service d'initialisation du modèle.
 */
public interface InitServiceAsync {
	
	void init(AsyncCallback callback);
}
