package com.github.thibweb.geojena.client;

import java.util.LinkedList;

import com.github.thibweb.geojena.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GeoJena implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final ExplorerServiceAsync explorerService = GWT.create(ExplorerService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabPanel tp = new TabPanel();
		tp.setWidth("100%");
		
		VerticalPanel allCodes = new VerticalPanel();
		Label allCodesLabel = new Label("Tous les codes venant typer des Features");
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
		allCodes.add(allCodesQuery);
		allCodes.add(allCodesTable);
		
		VerticalPanel vegeCodes = new VerticalPanel();
		Label vegeCodesLabel = new Label("Tous les codes associés à la classe Végétation (V)");
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
	    vegeCodes.add(vegeCodesQuery);
	    vegeCodes.add(vegeCodesTable);
	    
	    
		VerticalPanel codesCount = new VerticalPanel();
		VerticalPanel iceRegex = new VerticalPanel();
		VerticalPanel molecule = new VerticalPanel();
		VerticalPanel spJoint = new VerticalPanel();
	
		tp.add(RootPanel.get("classDiagram").asWidget(), "Diagramme de classes");
		tp.add(RootPanel.get("objectDiagram").asWidget(), "Diagramme d'instances");
		tp.add(RootPanel.get("modelQuestions").asWidget(), "Questions");
		tp.add(allCodes, "Tous les codes");
		tp.add(vegeCodes, "Codes de végétation");
		tp.add(new Label("Baz"), "Nombre de codes");
		tp.add(new Label("Baz"), "/ice/");
		tp.add(new Label("Baz"), "Molécule");
		tp.add(new Label("Baz"), "Jointure S-P");
		
		tp.selectTab(0);
		RootPanel.get("tabPanel").add(tp);
	}
}
