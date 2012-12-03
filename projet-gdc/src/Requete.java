import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;



public class Requete {
	private String requete = "";
	private String[] selection = null ;
	private String where  = "";
	
	
	public String[] getSelection() {
		return this.selection;
	}
	
	public String getRequete() {
		return this.requete;
	}
	
	public String getCondition() {
		return this.where;
	}
	
	public Requete(String requete){
		try{   this.requete = requete;
			   Pattern p = Pattern .compile("SELECT (?:DISTINCT)* *(.+) WHERE \\{(.+)\\}");
			   Matcher m = p.matcher(requete);
			   while (m.find()) {
				   this.selection = m.group(1).replaceAll("\\?","").replaceAll("(?:\\( *count\\(.+\\) as )","").replaceAll("\\)","").split(" ");
				   this.where = m.group(2);
				  
			   }
			} catch(PatternSyntaxException pse){
				pse.printStackTrace();
			}
	}
	
	///test
	public static void main( String[] args ) {
		Requete r1 = new Requete("SELECT ?sousClasses ?jou (count(h) as v)  WHERE { ?sousClasses rdfs:subClassOf <http://xmlns.com/foaf/0.1/Document> . }");
		for (int i = 0; i< r1.getSelection().length;i++) {
			System.out.println(r1.selection[i]);
		}
	}
}
