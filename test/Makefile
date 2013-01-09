JAVA_HOME:=$(dir $(shell which java))..
JAVAC=${JAVA_HOME}/bin/javac
JAVA=${JAVA_HOME}/bin/java
XJC=${JAVA_HOME}/bin/xjc

.PHONY=all go clean
all:go
	$(MAKE) clean

go: go.xml
	xmllint  --noout --schema ../schemas/bio/go/go.xsd $<
	mkdir -p tmp
	${XJC} -d tmp ../schemas/bio/go/go.xsd 
	${JAVAC} -d tmp -sourcepath tmp  TestGo.java \
		tmp/org/w3/_1999/_02/_22_rdf_syntax_ns_/ObjectFactory.java \
		tmp/org/geneontology/dtds/go/ObjectFactory.java
	${JAVA}  -cp tmp TestGo  | head -n 100
	rm -rf tmp

go.xml:
	curl "http://archive.geneontology.org/latest-termdb/go_daily-termdb.rdf-xml.gz" |\
	gunzip -c | grep -v "<!DOCTYPE " > $@

clean:
	rm -rf tmp go.xml *~
