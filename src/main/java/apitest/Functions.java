package apitest;

import java.io.StringReader;
import java.util.Random;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Functions {

	/**
	 * Create contract of type "Activation" based on the values provided by the
	 * customer
	 * 
	 * @param XML body of the API request received
	 * @return HTTP response with XML body
	 */
	public static Response activate(String xml_in) {
		try {
			// Generate XML source
			XPath xpath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml_in)));

			// Gather values from the XML file
			String providerName = xpath.evaluate("ActivationRequest/Provider/Name", document);
			String providerIdentifier = xpath.evaluate("ActivationRequest/Provider/Identifier", document);

			String imei = xpath.evaluate("ActivationRequest/IMEI", document);
			String iccid = xpath.evaluate("ActivationRequest/ICCID", document);

			String tariffProfile = xpath.evaluate("ActivationRequest/TariffProfile", document);
			String purchaser = xpath.evaluate("ActivationRequest/Purchaser", document);

			String kdAuftrNr = xpath.evaluate("ActivationRequest/CustomerNumber", document);

			// Validate file based on syntax
			validateXML(document, xpath);

			// Create order
			initOrder(imei, iccid, purchaser, providerName, providerIdentifier, tariffProfile, kdAuftrNr);

			return createResponseOK(true);
		} catch (XPathExpressionException xe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public static void validateXML(Document document, XPath xpath) throws Exception {
		try {
			System.out.println(xpath.evaluate("/", document));
		} catch (XPathExpressionException e) {
			throw new XPathExpressionException("Validation Failed");
		}
	}

	/**
	 * Generating an HTTP response with XML body with status 200 to return to the
	 * customer
	 * 
	 * @param orderNr true if orderNr should be included in response
	 * @return HTTP response with status 200 and XML body
	 */
	public static Response createResponseOK(boolean orderNr) {
		String response = "<ActivationResponse>";

		if (orderNr == true) {
			response += "<OrderNo>";

			/*
			 * In production this is where the order number of the new created order gets
			 * returned. In this example project the order number gets simulated
			 */
			String ordNrSim = "000";

			for (int i = 0; i < 7; i++) {
				Random rn = new Random();
				ordNrSim += rn.nextInt(10);
			}

			response += ordNrSim;
			response += "</OrderNo>";
		}

		response += "</ActivationResponse>";

		return Response.ok(response).build();
	}

	/**
	 * 
	 * @param status HTTP error code that should be returned to the customer
	 * @param HTTP   error message that should be returned to the customer
	 * @return HTTP response with custom HTTP code and body
	 */
//	public static Response createResponseError(Status status) {
//		return Response.status(status).build();
//	}

	/**
	 * An order gets created in the database. Based on the provided IMEI and ICCID
	 * provided it gets matched to a mobile record in the customers inventory
	 * 
	 * @param iccid     Unique id of the sim card
	 * @param imei      Unique id of the device
	 * @param purchaser Account name of the orderer
	 */
	public static void initOrder(String iccid, String imei, String purchaser, String providerName,
			String prividerIdentifier, String tariffProfile, String kdAuftrNr) {
	}
}
