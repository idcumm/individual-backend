.PHONY: run test build jar clean docker-build docker-run

run:
	./gradlew bootRun

test:
	./gradlew test

build:
	./gradlew bootJar

jar: build
	java -jar build/libs/*.jar

clean:
	./gradlew clean

docker-build: build
	docker build -t student-finance-api .

docker-run:
	docker run --rm -p 8080:8080 student-finance-api
