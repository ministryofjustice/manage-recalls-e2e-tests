FROM cypress/included:9.2.0

ADD . /home/circleci/project

WORKDIR /home/circleci/project

RUN npm ci

CMD ["npx", "cypress", "run", "--config", "baseUrl=http://manage-recalls-ui:3000"]
