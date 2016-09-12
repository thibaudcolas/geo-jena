package com.github.thibaudcolas.geojena.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service RPC appelé au lancement du serveur pour charger les données dans le modèle.
 */
@RemoteServiceRelativePath("init")
public interface InitService extends RemoteService {
	void init();
}
