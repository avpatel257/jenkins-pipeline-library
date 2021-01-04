package com.two57.jenkins.sharedlib

import groovy.transform.PackageScope


class NodeJSBuild extends BaseBuild {
    private String nodeVersion

    NodeJSBuild(def script, String projectName, String branchName) {
        super(script, projectName, branchName)
    }

    NodeJSBuild withNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion
        return this
    }


    void execute() {
        pipeline {
            node {
                try {
                    nodeEnv()

                    stage('Checkout') {
                        ansiColor {
                            checkout()
                        }
                    }
                    if (nodeVersion) {
                        stage('Build Node') {
                            ansiColor {

                                nodeBuild()

                            }
                        }
                    }
                } finally {
                    script.chuckNorris()
                }
            }


        }
    }

    @PackageScope
    def nodeBuild() {
        if (nodeVersion) {
            script.nodejs(
                    nodeJSInstallationName: nodeVersion) {
                script.sh "npm install"
                script.sh "npm run-script build"
            }
        }
    }


    @PackageScope
    def nodeEnv() {
        if (nodeVersion) {
            script.tool(type: 'nodejs', name: nodeVersion)
        }
    }
}
