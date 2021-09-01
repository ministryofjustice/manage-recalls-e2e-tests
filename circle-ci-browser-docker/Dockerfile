FROM cimg/openjdk:16.0.0-browsers

USER root

RUN apt-get update
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb -O /home/circleci/project/google-chrome-stable_current_amd64.deb
RUN apt install /home/circleci/project/google-chrome-stable_current_amd64.deb

RUN wget https://chromedriver.storage.googleapis.com/91.0.4472.101/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip
RUN mv chromedriver /usr/local/bin/chromedriver
RUN chown root:root /usr/local/bin/chromedriver
RUN chmod +x /usr/local/bin/chromedriver

USER circleci
