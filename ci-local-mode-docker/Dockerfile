FROM cypress/included:8.5.0

RUN mkdir /e2e

ADD . /e2e

WORKDIR /e2e

ENV DEBUG=cypress:*

ENTRYPOINT []
CMD ["cypress", "run", "--config", "baseUrl=http://manage-recalls-ui:3000", "--browser", "chrome", "--key", "e883dffd-b644-4431-989d-0181ae34d0e6"]
