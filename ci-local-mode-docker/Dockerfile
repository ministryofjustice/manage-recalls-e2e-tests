FROM cimg/node:16.13

ADD . /home/circleci/project

WORKDIR /home/circleci/project

RUN npm ci

CMD ["npx", "cypress", "run"]
