import java.io.InputStream;
import java.io.StringWriter;
import org.geneontology.dtds.go.*;
import org.w3._1999._02._22_rdf_syntax_ns.*;
import javax.xml.namespace.QName;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import  javax.xml.transform.stream.StreamSource;


public class TestGo
    {
    public static void main(String[] args) throws Exception
        {
	JAXBContext jaxbCtxt=JAXBContext.newInstance("org.geneontology.dtds.go:org.w3._1999._02._22_rdf_syntax_ns");
	Marshaller marshaller = jaxbCtxt.createMarshaller();
	Unmarshaller unmarshaller=jaxbCtxt.createUnmarshaller();
        marshaller.setProperty("jaxb.formatted.output",true);
        Object go=unmarshaller.unmarshal(new java.io.File("go.xml"));
        marshaller.marshal(go, System.out);
        }
    }
