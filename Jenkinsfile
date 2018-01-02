#!/usr/bin/env groovy

// This Jenkinsfile builds ExtendJ with Gradle.

node {
	env.JAVA_HOME="${tool 'oracle-jdk-8'}"
	env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

	stage('Checkout') {
		checkout scm
	}

	stage('Clean') {
		sh "./gradlew clean"
	}

	stage('ExtendJ4') {
		sh "./gradlew :java4:jar"
	}

	stage('ExtendJ5') {
		sh "./gradlew :java5:jar"
	}

	stage('ExtendJ6') {
		sh "./gradlew :java6:jar"
	}

	stage('ExtendJ7') {
		sh "./gradlew :java7:jar"
	}

	stage('ExtendJ8') {
		sh "./gradlew :java8:jar"
	}

	stage('Archive') {
		archive 'java4/extendj.jar'
		archive 'java5/extendj.jar'
		archive 'java6/extendj.jar'
		archive 'java7/extendj.jar'
		archive 'java8/extendj.jar'
	}
}
