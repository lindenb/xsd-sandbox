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
		`find tmp -name "ObjectFactory.java"` 
	${JAVA}  -cp tmp TestSchema null \
		"generated.org.genontology.go:generated.org.genontology.rdf"  $< |\
		head -n 20
	${JAVA}  -cp tmp generated.org.genontology.go.Go $< GO:0010566
	rm -rf tmp

go.xml:
	curl "http://archive.geneontology.org/latest-termdb/go_daily-termdb.rdf-xml.gz" |\
	gunzip -c | grep -v "<!DOCTYPE " > $@

doid: doid.owl
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(DOIDXSD))) -d tmp $(DOIDXSD) 
	${JAVAC} -d tmp -sourcepath tmp  TestSchema.java \
		`find tmp -name "ObjectFactory.java"` 
	${JAVA}  -cp tmp TestSchema null \
		"generated.org.diseaseontology.owl:generated.org.diseaseontology.rdf"  $< |\
		head -n 20
	${JAVA}  -cp tmp generated.org.diseaseontology.rdf.RDF $< DOID:0000000 DOID:114
	rm -rf tmp

doid.owl:
	curl "http://www.berkeleybop.org/ontologies/$@" > $@

clean:
	rm -rf tmp go.xml doid.owl *~
