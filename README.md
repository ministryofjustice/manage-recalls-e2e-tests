# manage-recalls-e2e-tests
A suite of tests to validate end-to-end functionality (PPUD Replacement)

## Overview
This project runs "end-to-end" tests against our services.  It creates the tested environment 
via build/pull and start of docker images of the following:
* hmpps-auth
* redis
* manage-recalls-api
* manage-recalls-ui
* fake-prisoner-offender-search-api

The `fake-prisoner-offender-search-api` is a wiremock container with some stub data
to allow the `manage-recalls-api` to search for prisoner data as if from a 
real `prisoner-offender-search` service.

## Running locally
To start the above docker images locally run the following in sequence:

```
docker compose build
docker compose pull
docker compose up
```

Note: starting particularly hmpps-auth can take several minutes; you should be able to
login once `hmpps-auth-e2e` has logged e.g. `Completed initialization`.

### Local login
Given the above docker steps you should be able to login at [manage-recalls](http://localhost:3000) 
with `PPUD_USER` / `password123456`. This user has the `MANAGE_RECALLS` role that allows access 
to the service.  See below re. valid credentials.


## Running the tests

### Login credentials
Credentials for the tests are loaded from file referenced
as `serenity.credentials` in `serenity.properties`, e.g. under your home directory
`~/.serenity/credentials.properties`.  This should take the form:

```
USERNAME=PPUD_USER
PASSWORD=password123456
```

#### Credentials source
As e2e tests including login, valid user credentials and the necessary user role are required.  
Locally these e2e tests run against the docker container `hmpps-auth-e2e`.  As that runs with
`SPRING_PROFILES_ACTIVE=dev` in the current latest image *only* the above `PPUD_USER` user is
pre-defined with the required role, `MANAGE_RECALLS`.

Team members on the project should have the appropriate role/s versus the deployed Dev instance,
[manage-recalls dev](https://manage-recalls-dev.hmpps.service.justice.gov.uk/) but not for running
these tests locally.

### Run

    ./gradlew test 

By default, this will run tests against a local environment (see above to start it).

To run against another system add `-Denvironment={dev/preprod/prod}`

### Test development
To run or debug tests within Intellij/Idea it should be sufficient, 
in edit of your Run Config template for `Cucumber Java`, to
set `Main class` to `net.serenitybdd.cucumber.cli.Main`, set glue to `cucumber.steps`, set the feature path to `manage-recalls-e2e-tests/src/test/resources/features`, and set the classpath to `manage-recalls-e2e-tests.test`

There is a run file in the `e2e.run` directory

### Viewing the reports
Running the above command will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

