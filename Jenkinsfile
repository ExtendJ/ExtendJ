#!/usr/bin/env groovy

// This Jenkinsfile builds ExtendJ with Gradle.
pipeline {
  agent any

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  tools {
    jdk 'jdk-8'
  }

  triggers {
    pollSCM('H/10 * * * *')
  }

  stages {
    stage('Clean') {
      steps {
        sh './gradlew clean'
      }
    }

    stage('ExtendJ4') {
      steps {
        sh './gradlew :java4:jar'
      }
    }

    stage('ExtendJ5') {
      steps {
        sh './gradlew :java5:jar'
      }
    }

    stage('ExtendJ6') {
      steps {
        sh './gradlew :java6:jar'
      }
    }

    stage('ExtendJ7') {
      steps {
        sh './gradlew :java7:jar'
      }
    }

    stage('ExtendJ8') {
      steps {
        sh './gradlew :java8:jar'
      }
    }

    stage('ExtendJ9') {
      steps {
        sh './gradlew :java9:jar'
      }
    }

    stage('ExtendJ10') {
      steps {
        sh './gradlew :java10:jar'
      }
    }

    stage('ExtendJ11') {
      steps {
        sh './gradlew :java11:jar'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: '**/extendj.jar', followSymlinks: false
      }
    }
  }
}
