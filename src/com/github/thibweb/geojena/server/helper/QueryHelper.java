package com.github.thibweb.geojena.server.helper;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Cette classe aide à la manipulation de requêtes SPARQL et de leurs résultats.
 * @author tcolas
 *
 */
public class QueryHelper {
	public static final String NL = System.getProperty("line.separator");

	// Préfixes utilisés dans GeoNames.
	public static final String GN = "gn";
	public static final String GN_NS = "http://www.geonames.org/ontology#";
	public static final String SKOS = "core";
	public static final String SKOS_NS = "http://www.w3.org/2004/02/skos/core#";
	
	private QueryHelper() {
		
	}

	private static Model m = ModelFactory.createDefaultModel();

	private static Logger LOG = Logger.getLogger("QueryManager");

	// Prend un fichier RDF et l'ajoute à notre modele.
	public static int readModel(String filePath) {
		FileManager.get().readModel(m, filePath);
		LOG.info("Model loaded : " + filePath + " -> " + m.size());

		return (int) m.size();
	}

	public static String getQueryPrefix(String alias, String namespace) {
		return "PREFIX " + alias + ": <" + namespace + ">" + NL;
	}

	// Écrit une requête en comparant ses différentes parties.
	public static String writeQuery(String select, String where) {
		String ret =  getQueryPrefix(GN, GN_NS)
				+ getQueryPrefix(SKOS, SKOS_NS)
				+ getQueryPrefix("rdf", RDF.getURI())
				+ getQueryPrefix("rdfs", RDFS.getURI())
				+ getQueryPrefix("owl", OWL.getURI())
				+ "SELECT " + select + NL
				+ "WHERE {" + where + "}";
		// Si le select contient un count, on utilise le premier élément renvoyé comme groupement pour le count.
		// TODO trouver un moyen plus propre.
		if (select.contains("COUNT") || select.contains("count")) {
			ret += NL + "GROUP BY " + select.split(" ")[0];
		}

		return ret;
	}

	// Écriture de requêtes avec ajout d'une limite.
	public static String writeQuery(String select, String where, int limit) {
		return writeQuery(select, where) + NL + "LIMIT " + limit;
	}

	// Lance une requête à l'aide de l'API Jena ARQ.
	public static ResultSet sendQuery(String queryString) {
		LOG.info("Query : " + queryString);
		// On précise la syntaxe SPARQL 1.1 pour profiter des capacités de ARQ.
		Query q = QueryFactory.create(queryString, Syntax.syntaxSPARQL_11);
		QueryExecution ex = QueryExecutionFactory.create(q, m);
		ResultSet rs = ex.execSelect();
		return rs;
	}

	// Transformation de la liste de variables d'une requête dans un format plus simple.
	private static LinkedList<String> getVariablesList(Iterator<String> varNames) {
		LinkedList<String> ret = new LinkedList<String>();
		while (varNames.hasNext()) {
			ret.add(varNames.next());
		}

		return ret;
	}

	/**
	 * Écrit, lance et répond à une requête SPARQL SELECT définie par ses sections SELECT et WHERE.
	 * Utilise la syntaxe SPARQL 1.1.
	 * @param select Le SELECT de la requête SPARQL.
	 * @param where Le WHERE de la requête.
	 * @return Les résultats sous un format simple à réutiliser : liste de listes.
	 */
	public static LinkedList<LinkedList<String>> retrieveResult(String select, String where) {
		LinkedList<LinkedList<String>> ret = new LinkedList<LinkedList<String>>();
		LinkedList<String> variablesList;

		// Écriture de la requête.
		String query = writeQuery(select, where, 100);

		// Lancement.
		ResultSet rs = sendQuery(query);
		LinkedList<String> tmp;
		QuerySolution qs;

		while (rs.hasNext()) {
			qs = rs.nextSolution();
			// Récupération des variables contenues dans les résultats.
			variablesList = getVariablesList(qs.varNames());
			tmp = new LinkedList<String>();
			// Ajout dans la liste.
			for (String var : variablesList) {
				tmp.add(qs.get(var).toString());
			}
			ret.add(tmp);
		}

		LOG.info("Query result size : " + ret.size() + " triples for " + where);

		return ret;
	}
}