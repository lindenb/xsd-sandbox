import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;


public class TestSchema
    {
    public static void main(String[] args) throws Exception
        {
        if(args.length!=3)
        	{
        	System.err.println("Usage  (schema.xsd or 'null') (packages) file.xml");
        	System.exit(-1);
        	}
       
	JAXBContext jaxbCtxt=JAXBContext.newInstance(args[1]);
	Marshaller marshaller = jaxbCtxt.createMarshaller();
	Unmarshaller unmarshaller=jaxbCtxt.createUnmarshaller();
        marshaller.setProperty("jaxb.formatted.output",true);
        if(!args[0].equals("null"))
        	{
		SchemaFactory factory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema= factory.newSchema(new StreamSource(new java.io.File(args[0])));
		unmarshaller.setSchema(schema);
		}
        Object o=unmarshaller.unmarshal(new java.io.File(args[2]));
        marshaller.marshal(o, System.out);
        }
    }
