package de.dhbw.sms.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletContext;

import org.mindswap.pellet.jena.PelletReasonerFactory;
//import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

import de.dhbw.sms.HomeController;

@Component
public class OntologyProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	OntModel model; 
	
	public void initialize(String pathToOntologyFile)
	{
		model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
		 //final DefaultResourceLoader loader = new DefaultResourceLoader();
		InputStream is = null;
		try {
			is = new FileInputStream(pathToOntologyFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 logger.info("Path to Ontology is: {}" ,pathToOntologyFile);
         if(is != null) 
         {
        	 model.read(new InputStreamReader(is), null);
        	 logger.info("Loaded Model" ,"");
         }
	}
	
	public void searchFor(IAbstractSyntaxTreeNode root)
	{
		
	}
	
	public void Test()
	{
		
         DoRelationQuery();
         DoSubComponentQuery();
		
	}
	
	private void DoRelationQuery()
	{
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
         		+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
         		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
         		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
         		+ "PREFIX sms: <http://www.semanticweb.org/dep04965/ontologies/2015/0/sms#>"
         		+ "SELECT *"
         		+ " WHERE { ?relation sms:hasRelationSymbol ?symbol }" ;
         com.hp.hpl.jena.query.Query que = QueryFactory.create(queryString) ;
         QueryExecution qexec = null;
         try {
        	 qexec= QueryExecutionFactory.create(que, model);
           com.hp.hpl.jena.query.ResultSet results = qexec.execSelect() ;
           for ( ; results.hasNext() ; )
           {
             QuerySolution soln = results.nextSolution() ;
             RDFNode l = soln.get("symbol") ;       // Get a result variable by name.
             //Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
             //Literal l = soln.getLiteral("y") ;   // Get a result variable - must be a literal
             logger.info("symbol:{}", l.toString());
             RDFNode r = soln.get("relation") ;
             logger.info("relation:{}", r.toString());
           }
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
         finally
         {
        	 if(qexec != null)qexec.close();
         }
	}
	
	private void DoSubComponentQuery()
	{
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
         		+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
         		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
         		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
         		+ "PREFIX sms: <http://www.semanticweb.org/dep04965/ontologies/2015/0/sms#>"
         		+ "SELECT ?x ?y ?q"
         		+ " WHERE { ?x sms:hasSubExpression ?y ."
         		//+ "         ?y sms:hasRelationSymbol ?z ."
         		+ "         ?x sms:hasSubExpression ?q ."
         		+ "         FILTER(?y!=?q)"
         		+ " }" ;
         com.hp.hpl.jena.query.Query que = QueryFactory.create(queryString) ;
         QueryExecution qexec = null;
         try {
        	 qexec= QueryExecutionFactory.create(que, model);
           com.hp.hpl.jena.query.ResultSet results = qexec.execSelect() ;
           for ( ; results.hasNext() ; )
           {
             QuerySolution soln = results.nextSolution() ;
             RDFNode x = soln.get("x") ;       // Get a result variable by name.
             logger.info("tolevel:{}", x.toString());
             RDFNode y = soln.get("y") ;
             logger.info("subexpression1:{}", y.toString());
             RDFNode q = soln.get("q") ;
             logger.info("subexpression2:{}", q.toString());
           }
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
         finally
         {
        	 if(qexec != null)qexec.close();
         }
	}

}
