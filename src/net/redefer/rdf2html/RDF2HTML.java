package net.redefer.rdf2html;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RDF2HTML{
	/**
	 * 
	 */
	private String transformation = "xsl/rdf2html.xsl"; // Default transformation to apply

	public void performTask(String input) throws IOException{
		PrintWriter out = new PrintWriter(new File("xsl/result.html"));
		try {

			String language = "en";
			String mode = "html";
			String namespaces = "false";
			InputSource iSource = new InputSource(new StringReader(input));
			Model data = ModelFactory.createDefaultModel();
			try {
				URL rdfURL = new URL(input);
				HttpURLConnection urlConn = (HttpURLConnection) rdfURL.openConnection();
				urlConn.setRequestProperty("Accept", "application/rdf+xml, application/xml;q=0.1, text/xml;q=0.1");
				data.read(urlConn.getInputStream(), input, "RDF/XML");
				ByteArrayOutputStream o = new ByteArrayOutputStream();
				data.write(o, "RDF/XML-ABBREV");
				o.flush();
				String rdfxml = o.toString("UTF8");
				iSource = new InputSource(new StringReader(rdfxml));
			} catch (MalformedURLException e) {
				// If rdf is not an URL, consider it is directly the RDF to render
				data.read(new StringReader(input), "", "RDF/XML");
				ByteArrayOutputStream o = new ByteArrayOutputStream();
				data.write(o, "RDF/XML-ABBREV");
				o.flush();
				String rdfxml = o.toString("UTF8");
				System.out.println(rdfxml);
				iSource = new InputSource(new StringReader(rdfxml));
			}
			System.out.println(iSource.getCharacterStream().toString());
			SAXParserFactory pFactory = SAXParserFactory.newInstance();
			pFactory.setNamespaceAware(true);
			pFactory.setValidating(false);
			XMLReader xmlReader = pFactory.newSAXParser().getXMLReader();

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslSource = new StreamSource(new File(transformation));
			// Generate the transformer
			Transformer transformer = tFactory.newTransformer(xslSource);
			transformer.setParameter("language", language);
			transformer.setParameter("mode", mode);
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD XHTML+RDFa 1.0//EN");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd");
			transformer.setParameter("namespaces", namespaces);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF8");
			// Perform the transformation, sending the result to output
			SAXSource a = new SAXSource(xmlReader, iSource);
			transformer.transform(a, new StreamResult(out));

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String [] args) {
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
		RDF2HTML r = new RDF2HTML();
		try {
			r.performTask(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
