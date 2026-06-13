FROM ubuntu:26.04

# Avoid prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

RUN apt update && apt install -y --no-install-recommends \
    curl \
    git \
    ca-certificates \
    sudo \
    python3 \
    python3-pip \
    vim \
    openssh-client \
    openjdk-8-jdk

RUN rm -rf /var/lib/apt/lists/*

# Docker build arguments
ARG USER_ID=1000
ARG GROUP_ID=1000

# 1. Check if the default 'ubuntu' user exists and delete it to free up UID/GID 1000
# 2. Create the 'yot' group and user
RUN if getent passwd ubuntu; then userdel -r ubuntu; fi && \
    groupadd -g ${GROUP_ID} yot && \
    useradd -l -u ${USER_ID} -g yot -m -s /bin/bash yot

# Switch to the new user
USER yot

WORKDIR /test

RUN curl -O https://dlcdn.apache.org//ant/binaries/apache-ant-1.10.17-bin.tar.gz
RUN tar xvf apache-ant-1.10.17-bin.tar.gz
RUN <<CONFIG cat >> $HOME/.profile
ANT_HOME="/test/apache-ant-1.10.17"
PATH="\$ANT_HOME/bin:\$PATH"
CONFIG

WORKDIR /test/regtest

ENTRYPOINT ["bash", "-l", "test8-entry.sh"]
