import java.util.Observable;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.FileManager;

/**
 * Cette classe est le modèle de l'application
 * @author mathilde
 *
 */

public class Modele extends Observable  {
	private String NL = System.getProperty("line.separator");
	private String prefixe;
	private InfModel m;
	private String geonames = "ontology_v2.rdf";
	private String lieux_fr = "villes.rdf";
	private Object[][] donnees;
	private Requete req;

	public InfModel getModele(){
		return this.m;
	}
	
	public Object[][] getDonnees() {
		return this.donnees;
	}
	
	public String[] getIntitules() {
		return req.getSelection();
	}
	
	public  Modele() {
		super();
		Model m = ModelFactory.createDefaultModel();
		FileManager.get().readModel(m,this.geonames);
		FileManager.get().readModel(m,this.lieux_fr);
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();	
		this.m = ModelFactory.createInfModel(reasoner,m);

	}
	
	
	
	public String getPrefixe(String espace_nom){
		if (espace_nom.equals("ontology")) {
			this.prefixe = "PREFIX ontology: <"+m.getNsPrefixURI("ontology")+">";
		} else if (espace_nom.equals("core")) {
			this.prefixe = "PREFIX core: <"+m.getNsPrefixURI("core")+">";
		} else if (espace_nom.equals("owl")) {
			this.prefixe = "PREFIX owl: <"+m.getNsPrefixURI("owl")+">";
		} else if (espace_nom.equals("rdf")) {
			this.prefixe = "PREFIX rdf: <"+m.getNsPrefixURI("rdf")+">";
		} else if (espace_nom.equals("rdfs")) {
			this.prefixe = "PREFIX rdfs: <"+m.getNsPrefixURI("rdfs")+">";
		} else {
			this.prefixe = "";
		}
		return this.prefixe;
	}
	
	
	
	public String getChainePrefixes() {
		 String p_ontology = this.getPrefixe("ontology");
		 String p_core = this.getPrefixe("core");
		 String p_owl = this.getPrefixe("owl");
		 String p_rdf = this.getPrefixe("rdf");
		 String p_rdfs = this.getPrefixe("rdfs");
		 m.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		 String p_foaf = "PREFIX foaf: <"+m.getNsPrefixURI("foaf")+">";
		 String prefixes = p_rdf + NL  + p_owl + NL  + p_core 
		 	+ NL  +  p_rdfs + NL  + p_ontology + NL 
		 	+ p_foaf + NL ;
		 return prefixes;
	}
	
	
	
	public void interrogerModele(Requete requete) {
		this.req = requete;
		 String req = this.getChainePrefixes() + " " +requete.getRequete();
		 Query query = QueryFactory.create(req, Syntax.syntaxARQ) ;
		 QueryExecution qexec = QueryExecutionFactory.create(query, this.m) ;
		 int nb_selection = requete.getSelection().length;
		 try {
	            ResultSet resultats = qexec.execSelect() ;
	            int j = 0;
	            this.donnees = new Object[100][nb_selection];
	           
	            for ( ; resultats.hasNext() ; )
	            {
	            	QuerySolution qs = resultats.nextSolution();
	            	RDFNode ref = null;
	            	for (int i = 0 ; i< nb_selection ; i++) {
	            		 ref = qs.get(requete.getSelection()[i]);
	            		this.donnees[j][i] = ref;
	            		

	            	}
	            	j++;
	            }
	            setChanged();
        		notifyObservers();
	        }
	        finally
	        {
	            qexec.close() ;
	        }
	        
	      
	     
	}
	
	
	
	
}
