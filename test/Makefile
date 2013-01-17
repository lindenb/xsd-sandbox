JAVA_HOME:=$(dir $(shell which java))..
JAVAC=${JAVA_HOME}/bin/javac
JAVA=${JAVA_HOME}/bin/java
XJC=${JAVA_HOME}/bin/xjc
GOAXSD=../schemas/bio/goa/goa.xsd
GOXSD=../schemas/bio/go/go.xsd
DOIDXSD=../schemas/bio/doid/doid.xsd
GENERIFXSD=../schemas/bio/do_rif/dorif.xsd
GENSENLABXSD=../schemas/bio/jensenlab/jensenlab.xsd

.PHONY=all jensenlab gene_rif goa doid go clean
all:jensenlab gene_rif goa doid go 
	$(MAKE) clean

jensenlab:
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(GENSENLABXSD))) -d tmp $(GENSENLABXSD)
	${JAVAC} -d tmp -sourcepath tmp  `find tmp -name "*.java"`
	curl -s "http://download.jensenlab.org/human_disease_textmining_filtered.tsv" |\
		${JAVA}  -cp tmp generated.org.jensenlab.GeneDiseaseAssociations > tmp/jensenlab.xml
	xmllint  --noout --schema $(GENSENLABXSD) tmp/jensenlab.xml
	#rm -rf tmp

gene_rif:
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(GENERIFXSD))) -d tmp $(GENERIFXSD)
	${JAVAC} -d tmp -sourcepath tmp  `find tmp -name "*.java"`
	curl -s "http://projects.bioinformatics.northwestern.edu/do_rif/do_rif.human.txt" |\
		${JAVA}  -cp tmp generated.edu.northwestern.bioinformatics.projects.do_rif.GeneRifDatabase > tmp/generif.xml
	xmllint  --noout --schema $(GENERIFXSD) tmp/generif.xml
	rm -rf tmp



goa: gene_association.goa_human
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(GOAXSD))) -d tmp $(GOAXSD)
	${JAVAC} -d tmp -sourcepath tmp  `find tmp -name "*.java"`
	${JAVA}  -cp tmp generated.uk.ac.ebi.goa.Associations $< > tmp/goa.xml
	xmllint  --noout --schema $(GOAXSD) tmp/goa.xml
	rm -rf tmp

gene_association.goa_human:
	curl "ftp://ftp.ebi.ac.uk/pub/databases/GO/goa/HUMAN/$@.gz" |\
	gunzip -c  > $@
	

go: go.xml
	#xmllint  --noout --schema $(GOXSD) $<
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(GOXSD))) -d tmp $(GOXSD)
	${JAVAC} -d tmp -sourcepath tmp  TestSchema.java \
		`find tmp -name "*.java"` 
	${JAVA}  -cp tmp TestSchema null \
		"generated.org.geneontology.go:generated.org.geneontology.rdf"  $< |\
		head -n 20
	${JAVA}  -cp tmp generated.org.geneontology.go.Go descendants $< GO:0010566
	${JAVA}  -cp tmp generated.org.geneontology.go.Go shortestpath $< GO:1900848
	rm -rf tmp

go.xml:
	curl "http://archive.geneontology.org/latest-termdb/go_daily-termdb.rdf-xml.gz" |\
	gunzip -c | grep -v "<!DOCTYPE " > $@

doid: doid.owl
	mkdir -p tmp
	${XJC} -extension -Xinject-code -b $(addsuffix .jxb,$(basename $(DOIDXSD))) -d tmp $(DOIDXSD) 
	${JAVAC} -d tmp -sourcepath tmp  TestSchema.java \
		`find tmp -name "*.java"` 
	${JAVA}  -cp tmp TestSchema null \
		"generated.org.diseaseontology.owl:generated.org.diseaseontology.rdf"  $< |\
		head -n 20
	${JAVA}  -cp tmp generated.org.diseaseontology.rdf.RDF $< DOID:0000000 DOID:114
	rm -rf tmp

doid.owl:
	curl "http://www.berkeleybop.org/ontologies/$@" > $@

clean:
	rm -rf tmp go.xml doid.owl gene_association.goa_human *~
