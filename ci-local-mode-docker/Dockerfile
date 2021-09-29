FROM quay.io/hmpps/manage-recalls-e2e-tests:latest

ADD . /home/circleci/project

WORKDIR /home/circleci/project

CMD ["/home/circleci/project/gradlew", "clean", "test", "-Denvironment=ci-local", "-Dchrome.switches=\"--enable-logging;--v=1;--no-sandbox;--disable-dev-shm-usage;--verbose\""]
