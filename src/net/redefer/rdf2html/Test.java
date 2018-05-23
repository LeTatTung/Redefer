package net.redefer.rdf2html;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
				"<!DOCTYPE rdf:RDF [\n" + 
				"  <!ENTITY swrl  \"http://www.w3.org/2003/11/swrl#\" >\n" + 
				"  <!ENTITY owl \"http://www.w3.org/2002/07/owl#\">\n" + 
				"  <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" + 
				"  <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\">\n" + 
				"  <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\">\n" + 
				"  <!ENTITY sourcecode \"http://hut.edu.vn/ontology/sourcecode#\" >\n" + 
				"  <!ENTITY document \"http://hut.edu.vn/ontology/document#\" >\n" + 
				"  <!ENTITY ruleml  \"http://www.w3.org/2003/11/ruleml#\" >\n" + 
				"]>\n" + 
				"<rdf:RDF\n" + 
				"  xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n" + 
				"  xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" + 
				"            xmlns:semanticdoc=\"http://hut.edu.vn/ontology/semanticdoc\"\n" + 
				"  xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" + 
				"  xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n" + 
				"  xmlns:ruleml=\"http://www.w3.org/2003/11/ruleml#\">\n" + 
				"\n" + 
				"  <swrl:Variable rdf:about=\"#javadocTopic\"/>\n" + 
				"  <swrl:Variable rdf:about=\"#javadocModel\"/>\n" + 
				"  <swrl:Variable rdf:about=\"#javadocFunction\"/>\n" + 
				"\n" + 
				"  <swrl:Imp rdf:about=\"#semanticdoc\">\n" + 
				"    <swrl:head rdf:parseType=\"Collection\">\n" + 
				"      <swrl:IndividualPropertyAtom>\n" + 
				"        <swrl:propertyPredicate rdf:resource=\"http://hut.edu.vn/ontology/sourcecode#WSPTest.Test1.src.letung.test.Config\"/>\n" + 
				"        <swrl:argument1 rdf:resource=\"#javadocTopic\"/>\n" + 
				"        <swrl:argument2 rdf:resource=\"#javadocModel\"/>\n" + 
				"        <swrl:argument1 rdf:resource=\"#javadocFunction\"/>\n" + 
				"      </swrl:IndividualPropertyAtom>\n" + 
				"    </swrl:head>\n" + 
				"\n" + 
				"    <swrl:body rdf:parseType=\"Collection\">\n" + 
				"      <rdf:Description rdf:about=\"http://hut.edu.vn/ontology/sourcecode#javadocTopic\">\n" + 
				"        <semanticdoc:value>Test</semanticdoc:value>\n" + 
				"      </rdf:Description>\n" + 
				"\n" + 
				"      <rdf:Description rdf:about=\"http://hut.edu.vn/ontology/sourcecode#javadocModel\">\n" + 
				"        <semanticdoc:value>Test</semanticdoc:value>\n" + 
				"      </rdf:Description>\n" + 
				"\n" + 
				"      <rdf:Description rdf:about=\"http://hut.edu.vn/ontology/sourcecode#javadocFunction\">\n" + 
				"        <semanticdoc:value>Test</semanticdoc:value>\n" + 
				"      </rdf:Description>\n" + 
				"    </swrl:body>\n" + 
				"  </swrl:Imp>\n" + 
				"\n" + 
				"</rdf:RDF>\n" + 
				"";
		String fileNameOutput = "/home/tung/Data/result.html";
		RDF2HTML rdfConvert = new RDF2HTML(input, fileNameOutput);
		try {
			rdfConvert.performTask();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
