.PHONY: dev-run dev-view run test build jar clean docker-build docker-run

# Local dev: starts (or creates) the sf-db Postgres container, waits for it and runs the API against it
dev-run:
	docker start sf-db || docker run -d --name sf-db -p 5432:5432 -e POSTGRES_DB=studentfinance -e POSTGRES_USER=sf -e POSTGRES_PASSWORD=dev postgres:17
	until docker exec sf-db pg_isready -U sf -d studentfinance; do sleep 1; done
	DB_URL=jdbc:postgresql://localhost:5432/studentfinance DB_USER=sf DB_PASSWORD=dev ./gradlew bootRun

# Shows what is stored in the local DB
dev-view:
	docker exec sf-db psql -U sf -d studentfinance -c 'select * from expense;'

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
