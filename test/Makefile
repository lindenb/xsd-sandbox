JAVA_HOME:=$(dir $(shell which java))..
JAVAC=${JAVA_HOME}/bin/javac
JAVA=${JAVA_HOME}/bin/java
XJC=${JAVA_HOME}/bin/xjc
GOXSD=../schemas/bio/go/go.xsd
DOIDXSD=../schemas/bio/doid/doid.xsd


.PHONY=all doid go clean
all:doid go
	$(MAKE) clean

go: go.xml
	#xmllint  --noout --schema $(GOXSD) $<
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(GOXSD))) -d tmp $(GOXSD)
	${JAVAC} -d tmp -sourcepath tmp  TestSchema.java \
		tmp/org/w3/_1999/_02/_22_rdf_syntax_ns_/ObjectFactory.java \
		tmp/org/geneontology/dtds/go/ObjectFactory.java
	${JAVA}  -cp tmp TestSchema null \
		"org.geneontology.dtds.go:org.w3._1999._02._22_rdf_syntax_ns_"  $< |\
		head -n 20
	${JAVA}  -cp tmp org.geneontology.dtds.go.Go $< GO:0010566
	rm -rf tmp

go.xml:
	curl "http://archive.geneontology.org/latest-termdb/go_daily-termdb.rdf-xml.gz" |\
	gunzip -c | grep -v "<!DOCTYPE " > $@

doid: doid.owl
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(DOIDXSD))) -d tmp $(DOIDXSD) 
	${JAVAC} -d tmp -sourcepath tmp  TestSchema.java \
		./tmp/org/w3/_1999/_02/_22_rdf_syntax_ns_/ObjectFactory.java \
		./tmp/org/w3/_2002/_07/owl/ObjectFactory.java 
	${JAVA}  -cp tmp TestSchema null \
		"org.w3._2002._07.owl:org.w3._1999._02._22_rdf_syntax_ns_"  $< |\
		head -n 20
	${JAVA}  -cp tmp org.w3._1999._02._22_rdf_syntax_ns_.RDF $< DOID:0000000 DOID:114
	rm -rf tmp

doid.owl:
	curl "http://www.berkeleybop.org/ontologies/$@" > $@

clean:
	rm -rf tmp go.xml doid.owl *~
