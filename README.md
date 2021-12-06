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

Check - and allow to execute - with:
```
`which chromedriver` --version
```
This will likely result in a security prompt warning of downloaded software.
You will need to allow it to execute via `System Preferences | Security and Privacy | General | Allow ...`.
To upgrade with `brew`: `brew upgrade chromedriver`. 
You can check your Chrome version via `Help | About ...`.  

Then verify that it's on your path with `which chromedriver`.

or, [download chromedriver](https://chromedriver.chromium.org/downloads). The `chromedriver` executable needs to be on your PATH (e.g. `export PATH=~/dev/chromedriver/:$PATH`)

### Source for login credentials

Unfortunately at present it is necessary to switch credentials between local auth and auth in the dev environment.

As e2e tests include login, valid user credentials and the necessary user role are required. 
Credentials for the tests are loaded from environment variables "SERENITY_USERNAME" and "SERENITY_PASSWORD", 
for local testing the code will default to the `PPUD_USER` when no env vars are specified, but if you want to run
against dev you should add your own credentials as env vars.

#### Credentials for local auth 
When run versus local services these e2e tests run against the docker container `hmpps-auth-e2e`.  As that runs with
`SPRING_PROFILES_ACTIVE=dev` in the current latest image *only* the above `PPUD_USER` user is
pre-defined with the required role, `MANAGE_RECALLS`, and should be used.

#### Credentials for environment auth
In contrast with the deployed `dev` instance of `hmpps-auth`,
i.e. [manage-recalls dev](https://manage-recalls-dev.hmpps.service.justice.gov.uk/)
, all team members on the project should have a personal login with the appropriate role
but `PPUD_USER` does not.  

We do also have a shared 'system' account which is valid versus `dev`:
```USERNAME=manage-recalls-test-user@digital.justice.gov.uk```
Ask the Dev team for the password.

## Running the tests

There are multiple options for running these tests from your local:

### 1. Run against deployed services
In this scenario the login credentials must be valid on the target environment. Hence, your env vars
needs to reflect the same.  See above. Also, need to add a valid noms number if running against preprod/prod. Use `NOMS_NUMBER=X1234YZ`.

Given valid credentials, to run against one of our deployed environments use simply:
```
./gradlew test -Denvironment={dev/preprod/prod}
```

It is also necessary for any data requirements implicit in the tests to be satisfied by our dependencies as deployed,
e.g. the `prisoner-offender-search-api`.  How that will work across environments is TBD but `dev` and `pre-prod`
are *not* maintained by similar processes, so we must expect data differences and data change over time between them.

If you are updating tests versus `dev` then the above is sufficient as a basis for valid testing.
To work versus changes _not_ yet on `dev` then service instances need to be run locally - as below.

### 2. Run against the latest Docker images (including UI and API) running locally with `fake` data

Note that normally these service images will also be the versions running in `dev` so, with the exception of control
over the data visible to the tests, 1., above, is a much simpler option.

With this option we create the target test environment via build/pull and start of docker images of the following:

* hmpps-auth
* redis
* manage-recalls-api
* manage-recalls-ui
* gotenberg (service to generate PDFs https://thecodingmachine.github.io/gotenberg)
* fake-prisoner-offender-search-api

The `fake-prisoner-offender-search-api` is a wiremock container (defined within this project) with some stub data
to allow the `manage-recalls-api` to search for prisoner data as if from a
real `prisoner-offender-search` service.

To start the above docker images locally run the following in sequence:

```
docker compose build
docker compose pull
docker compose up
```

Processes started with the above will log to the terminal from which docker is run.

Note: starting particularly `hmpps-auth` can take several minutes; you should be able to
login once `hmpps-auth-e2e` has logged e.g. `Completed initialization`.

Then, to run the tests versus local services, e.g. at a separate command prompt run:
```
./gradlew clean test 
```

### 3. Run tests while developing the UI and/or API locally

To maintain these tests versus in-progress changes, i.e. to `manage-recalls-ui` and/or `manage-recalls-api`, requires running against those local, under development, changes.

The script `build.sh` achieves this by:
* running `start-local-services.sh`
  * building and starting `manage-recalls-ui` and `manage-recalls-api` from   cloned source, as local siblings of this project,
  * starting remaining dependencies from docker images, and,
* executing the e2e tests via gradle, as above (`./gradlew clean test`).
* running `stop-local-services.sh`
  * stops all the components started by `start-local-services.sh`

Having started those services any subset of these e2e tests can be run, developed etc.,
whilst those services remain up.  
In the event of issues check the log files created by the script for each of `manage-recalls-ui` and `manage-recalls-api`.

#### Steps

Clone both [manage-recalls-ui](https://github.com/ministryofjustice/manage-recalls-ui) 
and [manage-recalls-api](https://github.com/ministryofjustice/manage-recalls-api).
Build both locally (see readme's for commands). Check that the local build passes
for both of those projects.  Potentially those builds will clash (e.g. wiremock port usage) with
services running for these tests, which will then need to be stopped.

Then use e.g. `./build.sh` as described above.

### Running tests in Intellij
To run or debug tests within IntelliJ/Idea it should be sufficient, 
in edit of your Run Config template for `Cucumber Java`, to
set `Main class` to `net.serenitybdd.cucumber.cli.Main`, set glue to `cucumber.steps`, 
set the feature path to `manage-recalls-e2e-tests/src/test/resources/features`, 
and set the classpath to `manage-recalls-e2e-tests.test`

There is a run file in the `e2e.run` directory which can be opened and run via Intellij/Idea
and achieves the above.

### Local login
However you have started the services locally, if successful you should be able to login
and investigate the UI/service at [manage-recalls](http://localhost:3000)
with `PPUD_USER` / `password123456`. This user has the `MANAGE_RECALLS` role that allows access
to the service.  See above re. valid credentials for other environments.

### Viewing the reports
Running the above command will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

## Autofill form filler chrome extension
The `autofill-extension-import.csv` file in the repo root can be imported into the Autofill chrome extension

* [Extension download](https://chrome.google.com/webstore/detail/autofill/nlmmgnhgdeffjkdckmikfpnddkbbfkkk)
* [Autofill import CSV page](chrome-extension://nlmmgnhgdeffjkdckmikfpnddkbbfkkk/options.html#importexport)

## Changing the person that E2E tests run against
On dev or pre-prod, change the NOMS_NUMBER_dev or NOMS_NUMBER_preprod env var accordingly ([CircleCI page](https://app.circleci.com/settings/project/github/ministryofjustice/manage-recalls-e2e-tests/environment-variables?return-to=https%3A%2F%2Fapp.circleci.com%2Fpipelines%2Fgithub%2Fministryofjustice%2Fmanage-recalls-e2e-tests))

## Troubleshooting

### Chromedriver version does not support Chrome version (running locally)

1. upgrade chromedriver eg `brew upgrade chromedriver`
2. on Mac, allow the updated file to be opened - `xattr -d com.apple.quarantine /usr/local/bin/chromedriver`

