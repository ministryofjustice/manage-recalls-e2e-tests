# manage-recalls-e2e-tests
A suite of tests to validate end-to-end functionality (PPUD Replacement)

## One-off setup

### Java

Version 16 is required:

```
brew install openjdk@16
```

### Chromedriver
A version of Chromedriver is required. One matching your local version of chrome is recommended.

You can install either with Homebrew:
```
brew install chromedriver
```

Then verify that it's on your path with `which chromedriver`.

or, [download chromedriver](https://chromedriver.chromium.org/downloads). The `chromedriver` executable needs to be on your PATH (e.g. `export PATH=~/dev/chromedriver/:$PATH`)

### Store login credentials
Credentials for the tests are loaded from a file referenced
as `serenity.credentials` in `serenity.properties`, e.g. under your home directory
`~/.serenity/credentials.properties`.  This should take the form:

```
USERNAME=PPUD_USER
PASSWORD=password123456
```

As e2e tests include login, valid user credentials and the necessary user role are required.  
Locally these e2e tests run against the docker container `hmpps-auth-e2e`.  As that runs with
`SPRING_PROFILES_ACTIVE=dev` in the current latest image *only* the above `PPUD_USER` user is
pre-defined with the required role, `MANAGE_RECALLS`.

Team members on the project should have the appropriate role/s versus the deployed Dev instance,
[manage-recalls dev](https://manage-recalls-dev.hmpps.service.justice.gov.uk/) but not for running these tests locally.

### Local login
However you run the tests (see below), you should be able to login at [manage-recalls](http://localhost:3000)
with `PPUD_USER` / `password123456`. This user has the `MANAGE_RECALLS` role that allows access
to the service.  See above re. valid credentials.

## Running the tests

There are two options for running the tests locally:

### 1. Run against the latest Docker images (including UI and API)

It creates the tested environment via build/pull and start of docker images of the following:
* hmpps-auth
* redis
* manage-recalls-api
* manage-recalls-ui
* fake-prisoner-offender-search-api
* gotenberg (service to generate PDFs https://thecodingmachine.github.io/gotenberg)

The `fake-prisoner-offender-search-api` is a wiremock container with some stub data
to allow the `manage-recalls-api` to search for prisoner data as if from a
real `prisoner-offender-search` service.

To start the above docker images locally run the following in sequence:

```
docker compose build
docker compose pull
docker compose up
```

Note: starting particularly hmpps-auth can take several minutes; you should be able to
login once `hmpps-auth-e2e` has logged e.g. `Completed initialization`.

Then, to run the tests:
```
./gradlew test 
```

#### Running against other environments

To run against another environment use:
```
./gradlew test -Denvironment={dev/preprod/prod}
```

### 2. Run tests while developing UI and/or API

To maintain these tests versus in-progress changes, i.e. to `manage-recalls-ui` and/or `manage-recalls-api`, requires running against those local, under development, changes.

The script `build.sh` achieves this by:
* running `start-local-services.sh`
  * building and starting `manage-recalls-ui` and `manage-recalls-api` from   cloned source, as local siblings of this project,
  * starting remaining dependencies from docker images, and,
* executing the e2e tests via gradle, as above.
* running `stop-local-services.sh`
  * stops all the components started by `start-local-services.sh`

Having started those services any subset of these e2e tests can be run, developed etc.,
whilst those services remain up.  In the event of issues check the log files created by the script for each of `manage-recalls-ui` and `manage-recalls-api`.

#### Steps

Clone both [manage-recalls-ui](https://github.com/ministryofjustice/manage-recalls-ui) and [manage-recalls-api](https://github.com/ministryofjustice/manage-recalls-api).
Build both locally (see readme's for commands). Check that the local build passes
for both of those projects.  Potentially those builds will clash (e.g. wiremock port usage) with
services running for these tests, which will then need to be stopped.

To start the two services and run all tests:
```
./build-local.sh
```

### Running tests in Intellij
To run or debug tests within Intellij/Idea it should be sufficient, 
in edit of your Run Config template for `Cucumber Java`, to
set `Main class` to `net.serenitybdd.cucumber.cli.Main`, set glue to `cucumber.steps`, set the feature path to `manage-recalls-e2e-tests/src/test/resources/features`, and set the classpath to `manage-recalls-e2e-tests.test`

There is a run file in the `e2e.run` directory which can be opened and run via Intellij/Idea
and achieves the above.

### Viewing the reports
Running the above command will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

