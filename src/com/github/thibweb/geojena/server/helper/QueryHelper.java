package com.github.thibweb.geojena.server.helper;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * This class helps writing / executing SPARQL queries on the GeoNames ontology.
 * @author tcolas
 *
 */
public class QueryHelper {

	// Use the same line separator everywhere.
	public static final String NL = System.getProperty("line.separator");
	
	// Prefixes used in the GeoNames ontology.
	public static final String GN = "gn";
	public static final String GN_NS = "http://www.geonames.org/ontology#";
	public static final String SKOS = "core";
	public static final String SKOS_NS = "http://www.w3.org/2004/02/skos/core#";
	
	private static Model m = ModelFactory.createDefaultModel();
	
	private static Logger LOG = Logger.getLogger("QueryManager");
	
	public static int readModel(String filePath) {
		FileManager.get().readModel(m, filePath);
		LOG.info("Model loaded : " + filePath + " -> " + m.size());
		
		return (int)m.size();
	}
	
	public static String getQueryPrefix(String alias, String namespace) {
		return "PREFIX " + alias + ": <" + namespace + "> " + NL;
	}
	
	// Writes a query by combining variables to retrieve, the WHERE part and prefixes. No FROM keyword here.
	public static String writeQuery(String variables, String where) {
		return getQueryPrefix(GN, GN_NS)
				+ getQueryPrefix(SKOS, SKOS_NS)
				+ getQueryPrefix("rdf", RDF.getURI())
				+ getQueryPrefix("rdfs", RDFS.getURI())
				+ getQueryPrefix("owl", OWL.getURI())
				+ "SELECT " + variables + NL
				+ "WHERE {" + where + "}";
	}
	
	public static String writeQuery(String variables, String where, int limit) {
		return writeQuery(variables, where) + NL + "LIMIT " + limit;
	}
	
	public static ResultSet sendQuery(String queryString) {
		LOG.info("Query : " + queryString);
		Query q = QueryFactory.create(queryString);
		QueryExecution ex = QueryExecutionFactory.create(q, m);
		ResultSet rs = ex.execSelect();
		return rs;
	}
	
	// Here we assume that variables is formatted as : "?var1 ?var2 ?var3".
	private static LinkedList<String> getVariablesList(String variables) {
		LinkedList<String> ret = new LinkedList<String>();
		String[] tmpVars = variables.split("\\?");
		
		for(int i = 1; i < tmpVars.length; i++) {
			ret.add(tmpVars[i].trim());
		}
				
		return ret;
	}
	
	public static LinkedList<LinkedList<String>> retrieveResult(String variables, String where) {
		LinkedList<LinkedList<String>> ret = new LinkedList<LinkedList<String>>();
		LinkedList<String> variablesList = getVariablesList(variables);
		
		String query = writeQuery(variables, where, 100);
		
		ResultSet rs = sendQuery(query);
		LinkedList<String> tmp;
		QuerySolution qs;
		while (rs.hasNext()) {
			qs = rs.nextSolution();
			
			tmp = new LinkedList<String>();
			for (String var : variablesList) {
				tmp.add(qs.get(var).toString());
			}
			ret.add(tmp);
		}
		
		LOG.info("Query result size : " + ret.size());
		
		return ret;
	}
	
	public static LinkedList<LinkedList<String>> retrieveResult(String select, String variables, String where) {
		LinkedList<LinkedList<String>> ret = new LinkedList<LinkedList<String>>();
		LinkedList<String> variablesList = getVariablesList(variables);
		
		String query = writeQuery(select, where);
		
		ResultSet rs = sendQuery(query);
		LinkedList<String> tmp;
		QuerySolution qs;
		while (rs.hasNext()) {
			qs = rs.nextSolution();
			
			tmp = new LinkedList<String>();
			for (String var : variablesList) {
				tmp.add(qs.get(var).toString());
			}
			ret.add(tmp);
		}
		
		LOG.info("Query result size : " + ret.size());
		
		return ret;
	}

	public static void main( String[] args ) {
		readModel("ontology_v2.rdf");
		// FILTER ( lang(?label) = \"en\" )
		String q1 = "?code a gn:Code . ?code core:inScheme ?scheme . ?code core:prefLabel ?label . ?code core:definition ?definition";
		String v1 = "?code ?scheme ?label ?definition";
		//printResult(sendQuery(writeQuery(v1, q1)), v1);
		String q2 = "?code core:inScheme gn:V . ?code core:definition ?definition";
		String v2 = "?code ?definition";
		//printResult(sendQuery(writeQuery(v2, q2)), v2);
		String q3 = "?code core:inScheme ?scheme";
		String v3 = "?scheme ?count";
		//printResult(sendQuery("SELECT ?scheme (count(?code) AS ?count) WHERE {?code <http://www.w3.org/2004/02/skos/core#inScheme> ?scheme} GROUP BY ?scheme"), "?scheme ?count");
		//printResult(sendQuery("SELECT (count(?code) AS ?nombre) WHERE {?code a <http://www.geonames.org/ontology#Code> . } GROUP BY ?code"), "?nombre");
		String q4 = "?code a gn:Code . ?code core:definition ?definition FILTER regex(?definition, \"ice\")";
		String v4 = "?code ?definition";
		//printResult(sendQuery(writeQuery(v4, q4)), v4);
	}
}