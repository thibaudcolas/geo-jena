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
		Label allCodesLabel = new Label("All GeoNames feature's codes");
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
				allCodesTable.setText(0, 1, "Class");
				allCodesTable.setText(0, 2, "Label");
				allCodesTable.setText(0, 3, "Definition");
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
		Label vegeCodesLabel = new Label("All GeoNames feature's codes");
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
	        vegeCodesTable.setText(0, 1, "Definition");
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
		
		tp.add(allCodes, "All codes");
		tp.add(vegeCodes, "Vegetation codes");
		tp.add(new Label("Baz"), "Codes count");
		tp.add(new Label("Baz"), "/ice/");
		tp.add(new Label("Baz"), "Molecule");
		tp.add(new Label("Baz"), "S-P Joint");
		
		RootPanel.get("tabPanel").add(tp);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("User Name ");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
//				explorerService.explorerServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
