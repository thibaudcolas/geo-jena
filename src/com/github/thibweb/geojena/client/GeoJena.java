package com.github.thibaudcolas.geojena.client;

import java.util.LinkedList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Classe d'entrée de l'application avec la méthode onModuleLoad appelée au chargement de la page.
 */
public class GeoJena implements EntryPoint {

	private final ExplorerServiceAsync explorerService = GWT.create(ExplorerService.class);

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		// L'essentiel de l'interface est sous forme d'onglets dans le TabPanel.
		TabPanel tp = new TabPanel();

		// On créé un VerticalPanel par requête.
		VerticalPanel allCodes = new VerticalPanel();
		VerticalPanel vegeCodes = new VerticalPanel();
		VerticalPanel codesCount = new VerticalPanel();
		VerticalPanel iceRegex = new VerticalPanel();
		VerticalPanel molecule = new VerticalPanel();
		VerticalPanel spJoint = new VerticalPanel();

		// Chaque panel a son label principal / titre + description.
		Label allCodesLabel = new Label("Tous les codes venant typer des Features");
		Label vegeCodesLabel = new Label("Tous les codes associés à la classe Végétation (V)");
		Label codesCountLabel = new Label("Nombre de types de features associés à chaque classe GeoNames");
		Label iceRegexLabel = new Label("Tous les codes ayant \"ice\" dans leur définition");
		Label moleculeLabel = new Label("Graphe en forme de molécule (deux étoiles) de lieux peuplés de France");
		Label spJointLabel = new Label("Patron de requête avec une jointe S-P : valeurs pour les propriétés affiliées à \"name\"");

		allCodesLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vegeCodesLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		codesCountLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		iceRegexLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		moleculeLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		spJointLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		// Chaque panel affiche sa requête dans un textarea.
		final TextArea allCodesQuery = new TextArea();
		final TextArea vegeCodesQuery = new TextArea();
		final TextArea codesCountQuery = new TextArea();
		final TextArea iceRegexQuery = new TextArea();
		final TextArea moleculeQuery = new TextArea();
		final TextArea spJointQuery = new TextArea();

		tp.setWidth("100%");
		allCodesQuery.setWidth("100%");
		vegeCodesQuery.setWidth("100%");
		codesCountQuery.setWidth("100%");
		iceRegexQuery.setWidth("100%");
		moleculeQuery.setWidth("100%");
		spJointQuery.setWidth("100%");
		allCodesQuery.setVisibleLines(8);
		vegeCodesQuery.setVisibleLines(8);
		codesCountQuery.setVisibleLines(8);
		iceRegexQuery.setVisibleLines(8);
		moleculeQuery.setVisibleLines(8);
		spJointQuery.setVisibleLines(8);

		// Les résultats sont placés dans une FlexTable.
		final FlexTable allCodesTable = new FlexTable();
		final FlexTable vegeCodesTable = new FlexTable();
		final FlexTable codesCountTable = new FlexTable();
		final FlexTable iceRegexTable = new FlexTable();
		final FlexTable moleculeTable = new FlexTable();
		final FlexTable spJointTable = new FlexTable();
		allCodesTable.addStyleName("FlexTable");
		vegeCodesTable.addStyleName("FlexTable");
		codesCountTable.addStyleName("FlexTable");
		iceRegexTable.addStyleName("FlexTable");
		moleculeTable.addStyleName("FlexTable");
		spJointTable.addStyleName("FlexTable");

		// ---------------------------------------------------------------------
		// Les appels qui suivent seront convertis en appels AJAX JavaScript.
		// La première de chacune des paires d'appels génère la requête placée dans la textarea.
		// La seconde renvoie les résultats de la requête avec l'ontologie.

		explorerService.writeQuery(ExplorerService.allCodesSelect, ExplorerService.allCodesWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				allCodesQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				allCodesQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.allCodesSelect, ExplorerService.allCodesWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				allCodesTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				allCodesTable.setText(0, 0, "URI");
				allCodesTable.setText(0, 1, "Classe");
				allCodesTable.setText(0, 2, "Label");
				allCodesTable.setText(0, 3, "Définition");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						allCodesTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		explorerService.writeQuery(ExplorerService.vegeCodesSelect, ExplorerService.vegeCodesWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				vegeCodesQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				vegeCodesQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.vegeCodesSelect, ExplorerService.vegeCodesWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				vegeCodesTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				vegeCodesTable.setText(0, 0, "URI");
				vegeCodesTable.setText(0, 1, "Définition");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						vegeCodesTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		explorerService.writeQuery(ExplorerService.codesCountSelect, ExplorerService.codesCountWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				codesCountQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				codesCountQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.codesCountSelect, ExplorerService.codesCountWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				codesCountTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				codesCountTable.setText(0, 0, "Classe");
				codesCountTable.setText(0, 1, "Nombre de codes");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						codesCountTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		explorerService.writeQuery(ExplorerService.iceRegexSelect, ExplorerService.iceRegexWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				iceRegexQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				iceRegexQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.iceRegexSelect, ExplorerService.iceRegexWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				iceRegexTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				iceRegexTable.setText(0, 0, "URI");
				iceRegexTable.setText(0, 1, "Définition");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						iceRegexTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		explorerService.writeQuery(ExplorerService.moleculeSelect, ExplorerService.moleculeWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				moleculeQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				moleculeQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.moleculeSelect, ExplorerService.moleculeWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				moleculeTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				moleculeTable.setText(0, 0, "Nom");
				moleculeTable.setText(0, 1, "Population");
				moleculeTable.setText(0, 2, "Code postal");
				moleculeTable.setText(0, 3, "Type");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						moleculeTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		explorerService.writeQuery(ExplorerService.spJointSelect, ExplorerService.spJointWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				spJointQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				spJointQuery.setText(query);
			}
		});

		explorerService.retrieveResult(ExplorerService.spJointSelect, ExplorerService.spJointWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
			public void onFailure(Throwable caught) {
				spJointTable.setText(0, 0, caught.toString());
			}
			public void onSuccess(LinkedList<LinkedList<String>> results) {
				spJointTable.setText(0, 0, "Propriété");
				spJointTable.setText(0, 1, "Valeur");
				int i = 1, j;
				for (LinkedList<String> result : results) {
					j = 0;
					for (String val : result) {
						spJointTable.setText(i, j++, val);
					}
					i++;
				}
			}
		});

		// ---------------------------------------------------------------------

		// Ajout de tous les éléments dans leurs VerticalPanel.
		allCodes.add(allCodesLabel);
		vegeCodes.add(vegeCodesLabel);
		codesCount.add(codesCountLabel);
		iceRegex.add(iceRegexLabel);
		molecule.add(moleculeLabel);
		spJoint.add(spJointLabel);
		// Descriptions.
		allCodes.add(new Label("Jointure S-S, graphe en étoile centré sur le code."));
		vegeCodes.add(new Label("Jointure S-S, graphe en étoile centré sur le code."));
		codesCount.add(new Label("Nécessite de préciser qu'on utilise SPARQL 1.1."));
		iceRegex.add(new Label("Cette recherche trouvera aussi bien \"My ice cream\" que \"service\"."));
		molecule.add(new Label("Utilise des données choisies arbitrairement : ville de Embrun et de Saint-Félix."));
		spJoint.add(new Label("Utilise les données de deux communes et d'une agglomération (Montpellier)."));
		allCodes.add(allCodesQuery);
		allCodes.add(allCodesTable);
		vegeCodes.add(vegeCodesQuery);
		vegeCodes.add(vegeCodesTable);
		codesCount.add(codesCountQuery);
		codesCount.add(codesCountTable);
		iceRegex.add(iceRegexQuery);
		iceRegex.add(iceRegexTable);
		molecule.add(moleculeQuery);
		molecule.add(moleculeTable);
		spJoint.add(spJointQuery);
		spJoint.add(spJointTable);

		// Ajout des VerticalPanel dans le TabPanel.
		tp.add(RootPanel.get("classDiagram").asWidget(), "Diagramme de classes");
		tp.add(RootPanel.get("objectDiagram").asWidget(), "Diagramme d'instances");
		tp.add(RootPanel.get("modelQuestions").asWidget(), "Questions");
		tp.add(allCodes, "Tous les codes");
		tp.add(vegeCodes, "Codes de végétation");
		tp.add(codesCount, "Nombre de codes");
		tp.add(iceRegex, "\"ice\"");
		tp.add(molecule, "Molécule");
		tp.add(spJoint, "Jointure S-P");

		tp.selectTab(0);
		RootPanel.get("tabPanel").add(tp);
	}
}
