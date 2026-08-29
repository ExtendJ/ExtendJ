# Base image with JDK (JDK_VERSION) and Ant installed.
FROM ubuntu:26.04 AS base

# Avoid prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    git \
    ca-certificates \
    sudo \
    python3 \
    python3-pip \
    vim \
    openssh-client \
    locales \
    && rm -rf /var/lib/apt/lists/*

# Use UTF-8 locale so that UTF-8 codepoints are printed correctly in Java stdout tests.
RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen \
    && locale-gen

ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

WORKDIR /test

# Obtain Apache Ant
RUN curl -O https://archive.apache.org/dist/ant/binaries/apache-ant-1.10.17-bin.tar.gz
RUN tar xvf apache-ant-1.10.17-bin.tar.gz

# Install JDK package
ARG JDK_VERSION=8
ENV JDK_VERSION=${JDK_VERSION}

# Java level under test.
ARG TEST_LEVEL=${JDK_VERSION}
ENV TEST_LEVEL=${TEST_LEVEL}

RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-${JDK_VERSION}-jdk \
    && rm -rf /var/lib/apt/lists/*

# Optional JDK 6, used for Java 5/6 regression testing.
ARG JDK6_URL=
RUN if [ -n "$JDK6_URL" ]; then \
      curl -fLo jdk6.tar.gz "$JDK6_URL" \
      && mkdir /opt/jdk6 \
      && tar -xzf jdk6.tar.gz -C /opt/jdk6 --strip-components=1 \
      && rm jdk6.tar.gz; \
    fi

# Extra Ant flags baked into the image.
ARG ANT_FLAGS=
ENV ANT_FLAGS=${ANT_FLAGS}

# Docker build arguments
ARG USER_ID=1000
ARG GROUP_ID=1000

# 1. Check if the default 'ubuntu' user exists and delete it to free up UID/GID 1000
# 2. Create the 'yot' group and user
RUN if getent passwd ubuntu; then userdel -r ubuntu; fi && \
    groupadd -g ${GROUP_ID} yot && \
    useradd -l -u ${USER_ID} -g yot -m -s /bin/bash yot

RUN mkdir -p /test/rtest && chown yot:yot /test/rtest

# Switch to the new user
USER yot

RUN <<CONFIG cat >> $HOME/.profile
ANT_HOME="/test/apache-ant-1.10.17"
PATH="\$ANT_HOME/bin:\$PATH"
CONFIG

# Container arguments are forwarded to Ant.
RUN <<'SCRIPT' cat > $HOME/entrypoint.sh
target="java${TEST_LEVEL}"
for arg; do
  case "$arg" in -*) ;; *) target="";; esac
done
exec ant $target ${ANT_FLAGS} "$@"
SCRIPT

WORKDIR /test/rtest

ENTRYPOINT ["bash", "-l", "/home/yot/entrypoint.sh"]

# Test image with the test framework and tests.
# The extendj.jar and the reports directory are bind mounted.
FROM base AS test

COPY --chown=yot:yot build.xml build.xml
COPY --chown=yot:yot lib lib
COPY --chown=yot:yot framework framework
COPY --chown=yot:yot src src
COPY --chown=yot:yot tests tests
