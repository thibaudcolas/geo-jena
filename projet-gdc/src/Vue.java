import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

/**
 * Cette classe est la vue de l'application
 * @author mathilde
 *
 */


public class Vue  extends JFrame implements Observer  {

	private static final long serialVersionUID = 1L;
	  
	private JPanel container;
	  
	  private JComboBox select = new JComboBox();

	  private Manipulation ctrl;
	  private Modele m;
	  private Object[][] donnees = null;
	  private String[] intitules = null;
	  
	  private String intitule_req = "";
	  
	 
	  public  void setDonnees(Object[][] donnees) {
			this.donnees = donnees;
		}
	  
	  public  Object[][] getDonnees() {
			return this.donnees ;
		}
		
		public  void setIntitules(String[] intitule) {
			this.intitules = intitule;
		}
	  
	  public JComboBox getSelect() {
		  return select;
	  }
	  
	  public final Manipulation getControleur(){
			return ctrl;
		}
	  
	  public Vue(Manipulation ctrl){
		this.ctrl = ctrl;
		this.m = ctrl.getModele();
	    this.setTitle("Geonames");
	    this.setSize(1100, 1000);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    container = new JPanel();
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		afficherSelectionRequete();
	    afficherResultats();
		this.setContentPane(container);
		this.setVisible(true);
	  }

	  public void afficherElements(){
		  container = new JPanel();
		  container.setBackground(Color.white);
		  container.setLayout(new BorderLayout());
		  afficherSelectionRequete();
		  afficherResultats();
		  this.setContentPane(container);
	  }
	  
	
	  
	  private void afficherResultats( ) {
		  if ( this.donnees != null && this.intitules != null) {
			  //intitulé requête
			  JLabel intitule_req = new JLabel(""+this.intitule_req,SwingConstants.CENTER);
			  intitule_req.setSize(new Dimension(1100,60));
			  
			  //tableau résultats
			  JTable resultat = new JTable(this.donnees , this.intitules);
			  resultat.setPreferredScrollableViewportSize(new Dimension(1000,800));
			  JScrollPane jp = new JScrollPane(resultat);
			  jp.setSize(800,800);
			  
			  //panel
			  JPanel resultats_a = new JPanel(new BorderLayout());
			  resultats_a.setBackground(Color.white);
			  resultats_a.add(intitule_req,BorderLayout.NORTH);
			  resultats_a.add(jp,BorderLayout.CENTER);
			  resultats_a.setSize(800, 800);
			  container.add(resultats_a, BorderLayout.CENTER);
		  }
	  }
	  

	  private void afficherSelectionRequete() {
		 if ( select.getItemCount() != 0 ) 
		 { 
			 select.removeAllItems();
		  }
		  JPanel selection = new JPanel();
		  selection.setBackground(Color.white);
		  select.setPreferredSize(new Dimension(1000, 20));
		  select.addActionListener(new ItemAction());
		  select.addItem("Sélectionnez une requête.");
		  select.addItem("1.  Quelles sont les sous-classes de foaf:Documents ?");
		  select.addItem("2.  Quelles sont les propriétés qui relient deux features ?");
		  select.addItem("3.  Quelles sont les propriétés DataType dont le domaine est une Feature ?");
		  select.addItem("4.  Quelle est la définition du code #H.ANCH ?");
		  select.addItem("5.  Quels codes sont liés à la ressource A  ?");
		  select.addItem("6.  Donner tous les codes venant typer les features, leur définition, "
				          + "leur label préféré en anglais et leur classe d'appartenance");
		  select.addItem("7.  Lister pour la classe geonames V les types de feature associés (code) et leur définition.");
		  select.addItem("8.  Renvoyer pour chaque classe geonames, le nombre de types de feature associés");
		  select.addItem("9.  Renvoyer les codes et définitions de type feature ayant \"ice\" dans leur définition");
		  select.addItem("10  jointure S-P : tous les triplets où le  prédicat est de type datatype et l'objet un nombre.");
		  select.addItem("11  jointure molécule : Donner des infos sur la ville 'Mont-de-Marsan' et des infos sur sa featureClass.");
	      selection.add(select);
	      selection.setSize(new Dimension(1100, 50));
	      container.add(selection, BorderLayout.NORTH);
	  }
	  
	  public void update(Observable source, Object value) {
		  if ( m.getDonnees() != null && m.getIntitules() != null) {
			this.donnees = m.getDonnees();
			this.intitules = m.getIntitules();
			this.intitule_req = ""+select.getSelectedItem();
			afficherElements();
		  }
	  }
	
	  class ItemAction implements ActionListener{

		    public void actionPerformed(ActionEvent e) {
		    ctrl.selectionnerRequete(select.getSelectedIndex());
		    
		    }               
		  }

	  

}
