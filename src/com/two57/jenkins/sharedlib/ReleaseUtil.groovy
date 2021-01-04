package com.two57.jenkins.sharedlib

class ReleaseUtil {
    private final def script

    ReleaseUtil(def script) {
        this.script = script
    }

    def String getGitTag() {
        // here `steps` is the global object for Jenkinsfile
        // you can use other pipeline step here by `steps`

        script.echo 'test use pipeline echo outside Jenkinsfile'
        return script.sh(returnStdout: true, script: "git tag --sort version:refname | tail -1").trim()
    }
}
