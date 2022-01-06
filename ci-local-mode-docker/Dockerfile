FROM cypress/included:9.2.0

RUN mkdir /e2e

ADD . /e2e

WORKDIR /e2e

ENV DEBUG=cypress:*

RUN npm ci

ENTRYPOINT []
CMD ["./ci-local-mode-docker/run-tests.sh"]
