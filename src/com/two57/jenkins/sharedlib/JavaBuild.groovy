package com.two57.jenkins.sharedlib

import groovy.transform.PackageScope

class JavaBuild extends BaseBuild {
    private String agentName
    private String mavenVersion
    private String jdkVersion
    private String nodeVersion

    JavaBuild(script, String projectName, String branchName) {
        super(script, projectName, branchName)
    }

    JavaBuild withMavenVersion(String mavenVersion) {
        this.mavenVersion = mavenVersion
        return this
    }

    JavaBuild withJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion
        return this
    }

    void execute() {
        pipeline {
            node {
                try {
                    maven()
                    jdk()
                    stage('Checkout') {
                        ansiColor {
                            checkout()
                        }
                    }
                    stage('Build Maven') {
                        ansiColor {
                            mavenBuild()
                        }
                    }
                } finally {
                    script.chuckNorris()
                }
            }
        }
    }


    @PackageScope
    def maven() {
        if (mavenVersion) {
            script.tool(type: 'maven', name: mavenVersion)
        }
    }

    @PackageScope
    def mavenBuild() {
        if (mavenVersion) {
            script.withMaven(
                    // Maven installation declared in the Jenkins "Global Tool Configuration"
                    maven: mavenVersion, jdk: jdkVersion) {

                // Run the maven build
                script.sh "mvn clean package"

            }
        }
    }

    @PackageScope
    def jdk() {
        if (jdkVersion) {
            script.tool(type: 'jdk', name: jdkVersion)
        }
    }
}