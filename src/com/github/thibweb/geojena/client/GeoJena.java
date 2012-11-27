package com.github.thibweb.geojena.client;

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
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GeoJena implements EntryPoint {

	private final ExplorerServiceAsync explorerService = GWT.create(ExplorerService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabPanel tp = new TabPanel();
		tp.setWidth("100%");
		
		VerticalPanel allCodes = new VerticalPanel();
		Label allCodesLabel = new Label("Tous les codes venant typer des Features");
		Label allCodesDescription = new Label("Jointure S-S, graphe en étoile.");
		allCodesLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		final TextArea allCodesQuery = new TextArea();
		allCodesQuery.setWidth("100%");
		allCodesQuery.setVisibleLines(8);
		explorerService.writeQuery(ExplorerService.allCodesVariables, ExplorerService.allCodesWhere, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				allCodesQuery.setText(caught.toString());
			}
			public void onSuccess(String query) {
				allCodesQuery.setText(query);
			}
		});
		
		final FlexTable allCodesTable = new FlexTable();
		allCodesTable.addStyleName("FlexTable");
		
		explorerService.retrieveResult(ExplorerService.allCodesVariables, ExplorerService.allCodesWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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
		
		allCodes.add(allCodesLabel);
		allCodes.add(allCodesDescription);
		allCodes.add(allCodesQuery);
		allCodes.add(allCodesTable);
		
		VerticalPanel vegeCodes = new VerticalPanel();
		Label vegeCodesLabel = new Label("Tous les codes associés à la classe Végétation (V)");
		Label vegeCodesDescription = new Label("Jointure S-S, graphe en étoile.");
	    vegeCodesLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	    
	    final TextArea vegeCodesQuery = new TextArea();
	    vegeCodesQuery.setWidth("100%");
	    vegeCodesQuery.setVisibleLines(8);
	    explorerService.writeQuery(ExplorerService.vegeCodesVariables, ExplorerService.vegeCodesWhere, new AsyncCallback<String>() {
	      public void onFailure(Throwable caught) {
	        vegeCodesQuery.setText(caught.toString());
	      }
	      public void onSuccess(String query) {
	        vegeCodesQuery.setText(query);
	      }
	    });
	    
	    final FlexTable vegeCodesTable = new FlexTable();
	    vegeCodesTable.addStyleName("FlexTable");
	    
	    explorerService.retrieveResult(ExplorerService.vegeCodesVariables, ExplorerService.vegeCodesWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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
	    
	    vegeCodes.add(vegeCodesLabel);
	    vegeCodes.add(vegeCodesDescription);
	    vegeCodes.add(vegeCodesQuery);
	    vegeCodes.add(vegeCodesTable);
	    
	    
		VerticalPanel codesCount = new VerticalPanel();
		Label codesCountLabel = new Label("Nombre de types de features associés à chaque classe GeoNames");
        codesCountLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        
        final TextArea codesCountQuery = new TextArea();
        codesCountQuery.setWidth("100%");
        codesCountQuery.setVisibleLines(8);
        explorerService.writeQuery(ExplorerService.codesCountVariables, ExplorerService.codesCountWhere, ExplorerService.codesCountSelect, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            codesCountQuery.setText(caught.toString());
          }
          public void onSuccess(String query) {
            codesCountQuery.setText(query);
          }
        });
        
        final FlexTable codesCountTable = new FlexTable();
        codesCountTable.addStyleName("FlexTable");
        
        explorerService.retrieveResult(ExplorerService.codesCountVariables, ExplorerService.codesCountWhere, ExplorerService.codesCountSelect, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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

        codesCount.add(codesCountLabel);
        codesCount.add(codesCountQuery);
        codesCount.add(codesCountTable);
       
		
		
		VerticalPanel iceRegex = new VerticalPanel();
	    Label iceRegexLabel = new Label("Tous les codes ayant \"ice\" dans leur définition");
	      iceRegexLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	      
	      final TextArea iceRegexQuery = new TextArea();
	      iceRegexQuery.setWidth("100%");
	      iceRegexQuery.setVisibleLines(8);
	      explorerService.writeQuery(ExplorerService.iceRegexVariables, ExplorerService.iceRegexWhere, new AsyncCallback<String>() {
	        public void onFailure(Throwable caught) {
	          iceRegexQuery.setText(caught.toString());
	        }
	        public void onSuccess(String query) {
	          iceRegexQuery.setText(query);
	        }
	      });
	      
	      final FlexTable iceRegexTable = new FlexTable();
	      iceRegexTable.addStyleName("FlexTable");
	      
	      explorerService.retrieveResult(ExplorerService.iceRegexVariables, ExplorerService.iceRegexWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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
	      
	      iceRegex.add(iceRegexLabel);
	      iceRegex.add(iceRegexQuery);
	      iceRegex.add(iceRegexTable);
	      
	      VerticalPanel molecule = new VerticalPanel();
	      Label moleculeLabel = new Label("Graphe en forme de molécule (deux étoiles) de lieux peuplés de France");
	        moleculeLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
	        
	        final TextArea moleculeQuery = new TextArea();
	        moleculeQuery.setWidth("100%");
	        moleculeQuery.setVisibleLines(8);
	        explorerService.writeQuery(ExplorerService.moleculeVariables, ExplorerService.moleculeWhere, new AsyncCallback<String>() {
	          public void onFailure(Throwable caught) {
	            moleculeQuery.setText(caught.toString());
	          }
	          public void onSuccess(String query) {
	            moleculeQuery.setText(query);
	          }
	        });
	        
	        final FlexTable moleculeTable = new FlexTable();
	        moleculeTable.addStyleName("FlexTable");
	        
	        explorerService.retrieveResult(ExplorerService.moleculeVariables, ExplorerService.moleculeWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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
	        
	        molecule.add(moleculeLabel);
	        molecule.add(moleculeQuery);
	        molecule.add(moleculeTable);
	        
	        VerticalPanel spJoint = new VerticalPanel();
	        Label spJointLabel = new Label("Patron de requête avec une jointe S-P : valeurs pour les propriétés affiliées à \"name\"");
	        spJointLabel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

	        final TextArea spJointQuery = new TextArea();
	        spJointQuery.setWidth("100%");
	        spJointQuery.setVisibleLines(8);
	        explorerService.writeQuery(ExplorerService.spJointVariables, ExplorerService.spJointWhere, new AsyncCallback<String>() {
	          public void onFailure(Throwable caught) {
	            spJointQuery.setText(caught.toString());
	          }
	          public void onSuccess(String query) {
	            spJointQuery.setText(query);
	          }
	        });

	        final FlexTable spJointTable = new FlexTable();
	        spJointTable.addStyleName("FlexTable");

	        explorerService.retrieveResult(ExplorerService.spJointVariables, ExplorerService.spJointWhere, new AsyncCallback<LinkedList<LinkedList<String>>>() {
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

	        spJoint.add(spJointLabel);
	        spJoint.add(spJointQuery);
	        spJoint.add(spJointTable);
	
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
