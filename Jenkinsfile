#!/usr/bin/env groovy

// This Jenkinsfile builds ExtendJ with Gradle.
pipeline {
  agent any

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  triggers {
    pollSCM('H/10 * * * *')
  }

  stages {
    stage('Clean') {
      steps {
        sh './gradlew clean'
        dir("rtest/lib") {
          sh "curl -sSLO https://repo1.maven.org/maven2/junit/junit/4.11/junit-4.11.jar"
          sh "curl -sSLO https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"
          sh "curl -sSLO https://repo1.maven.org/maven2/org/apache/ant/ant/1.10.5/ant-1.10.5.jar"
          sh "curl -sSLO https://repo1.maven.org/maven2/org/apache/ant/ant-junit/1.10.5/ant-junit-1.10.5.jar"
        }
      }
    }

    stage('Build') {
      tools {
        jdk 'jdk-8'
      }
      steps {
        sh './gradlew jar'
      }
    }

    stage('Test8') {
      tools {
        jdk 'jdk-8'
        ant 'ant-1.10.5'
      }
      steps {
        sh "cp java8/extendj.jar rtest/"
        dir("rtest") {
          sh "ant clean"
          sh "rm -r reports"
          sh "ant java8"
          junit 'reports/**/*.xml'
        }
      }
    }

    stage('Test9') {
      tools {
        jdk 'jdk-9'
        ant 'ant-1.10.5'
      }
      steps {
        sh "cp java9/extendj.jar rtest/"
        dir("rtest") {
          sh "ant clean"
          sh "rm -r reports"
          sh "ant java9"
          junit 'reports/**/*.xml'
        }
      }
    }

    stage('Test10') {
      tools {
        jdk 'jdk-10'
        ant 'ant-1.10.5'
      }
      steps {
        sh "cp java10/extendj.jar rtest/"
        dir("rtest") {
          sh "ant clean"
          sh "rm -r reports"
          sh "ant java10"
          junit 'reports/**/*.xml'
        }
      }
    }

    stage('Test11') {
      tools {
        jdk 'jdk-11'
        ant 'ant-1.10.5'
      }
      steps {
        sh "cp java11/extendj.jar rtest/"
        dir("rtest") {
          sh "ant clean"
          sh "rm -r reports"
          sh "ant java11"
          junit 'reports/**/*.xml'
        }
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: '**/extendj.jar', followSymlinks: false
      }
    }
  }
}
