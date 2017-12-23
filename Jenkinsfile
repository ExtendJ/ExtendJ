#!/usr/bin/env groovy

// This Jenkinsfile builds ExtendJ with Gradle.

node {
	env.JAVA_HOME="${tool 'oracle-jdk-8'}"
	env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

	stage('Checkout') {
		checkout scm
	}

	stage('Build') {
		sh "./gradlew clean jar"
	}

	stage('Archive') {
		archive 'java4/extendj.jar'
		archive 'java5/extendj.jar'
		archive 'java6/extendj.jar'
		archive 'java7/extendj.jar'
		archive 'java8/extendj.jar'
	}
}
