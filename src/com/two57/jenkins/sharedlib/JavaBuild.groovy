package com.two57.jenkins.sharedlib

import groovy.transform.PackageScope
import java.util.*
import groovy.util.XmlParser

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
//                    stage('Build Docker'){
//
//                    }
                    if(git.isMasterBranch()) {
                        log("Branch == Master")
                    } else {
                        log("Branch is not master: " + git.currentBranchName() )
                        log("Tag: " + releaseUtil.gitTag)
                    }
                    log("Commit Hash: " + git.commitHash() + " - " + git.commitAuthor())
//                    if(branchName == 'master' || branchName == 'main') {
//                        stage('Docker Push') {
//
//                        }
//                    }
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
    def dockerBuild() {
        script.docker
    }

    @PackageScope
    def jdk() {
        if (jdkVersion) {
            script.tool(type: 'jdk', name: jdkVersion)
        }
    }

    private def getProjectVersion(){
        def project = new XmlSlurper().parseText(new File('pom.xml').getText('UTF-8'))
        return project.version.text()
        def pom = new File('pom.xml').getText('utf-8')
        def doc = new XmlParser().parseText(pom)
        def version = doc.version.text()

        version
    }
}