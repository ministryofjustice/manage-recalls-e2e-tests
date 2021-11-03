FROM cimg/openjdk:16.0.0-browsers

USER root
WORKDIR /tmp

RUN apt-get update
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb -O /tmp/google-chrome-stable_current_amd64.deb
RUN apt install /tmp/google-chrome-stable_current_amd64.deb

RUN wget https://chromedriver.storage.googleapis.com/95.0.4638.54/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip
RUN mv chromedriver /usr/local/bin/chromedriver
RUN chown root:root /usr/local/bin/chromedriver
RUN chmod +x /usr/local/bin/chromedriver

WORKDIR /home/circleci/project
USER circleci
