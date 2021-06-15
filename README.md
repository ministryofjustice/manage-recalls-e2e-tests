# manage-recalls-e2e-tests
A suite of tests to validate end-to-end functionality (PPUD Replacement)

### Running services locally in docker
To start all required services with 'fake' apis you can run the following:

```
docker compose build
docker compose pull
docker compose up
```

This builds/pulls and starts the following dockers images:
* hmpps-auth 
* redis
* manage-recalls-api
* manage-recalls-ui
* fake-prisoner-search-offender-api

The `fake-prisoner-search-offender-api` is a wiremock container with some stub data to allow the `manage-recalls-api` to search for prisoner data

You can login locally to [manage-recalls](http://localhost:3000) with `PPUD_USER` / `password123456`, this user has the `MANAGE_RECALLS` role that allows access to the service.

## Use Gradle

Open a command window and run:

    gradlew test 


## Viewing the reports

Both of the commands provided above will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

