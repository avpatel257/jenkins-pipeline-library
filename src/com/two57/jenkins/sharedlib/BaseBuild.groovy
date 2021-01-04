package com.two57.jenkins.sharedlib

import groovy.transform.PackageScope

class BaseBuild {
    protected final def script
    protected final String projectName
    protected final String branchName
    protected final Git git
    protected String agentName

    BaseBuild(script, String projectName, String branchName) {
        this.script = script
        this.projectName = projectName
        this.branchName = branchName
        this.git = new Git(script)
    }

    BaseBuild withAgent(String agent) {
        this.agentName = agent
        return this
    }

    @PackageScope
    def checkout() {
        script.checkout(script.scm)
    }

    @PackageScope
    def stage(String title, Closure closure) {
        script.stage(title) {
            closure.call()
        }
    }

    @PackageScope
    def node(String label = null, Closure closure) {
        if (label) {
            script.node(label) {
                closure.call()
            }
        } else {
            script.node {
                closure.call()
            }
        }
    }

    @PackageScope
    def ansiColor(Closure closure) {
        script.timestamps {
            script.ansiColor('xterm') {
                closure.call()
            }
        }
    }

    @PackageScope
    def pipeline(Closure closure) {

        script.pipeline {
            closure.call()
        }

    }
    @PackageScope
    def log(message) {
        println(message)
    }
}