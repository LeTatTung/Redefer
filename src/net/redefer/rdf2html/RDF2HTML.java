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
	private String transformation = "/home/tung/Data/rdf2html.xsl"; // Default transformation to apply
	private String input;
	
	
	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}

	public RDF2HTML(String input) {
		this.input = input;
	}
	public void performTask(String input) throws IOException{
		PrintWriter out = new PrintWriter(new File("/home/tung/Data/result.html"));
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

}
