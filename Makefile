START:
	$(MAKE) build

compile:
	mvn compile

clean:
	mvn clean

package:
	mvn package

lint:
	mvn -pl '!utils,!api' spotless:check

lint-fix:
	mvn -pl '!utils,!api' spotless:apply

version:
	mvn versions:set
	mvn versions:commit

proguard:
	sh ./scripts/obfuscate.sh

build-package:
	$(MAKE) package

build-dev:
	$(MAKE) build-package
	sh ./scripts/create-dev-jar.sh

build:
	$(MAKE) build-package
	$(MAKE) proguard
