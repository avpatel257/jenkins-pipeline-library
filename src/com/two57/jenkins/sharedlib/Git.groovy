package com.two57.jenkins.sharedlib

class Git implements Serializable {

    private final def script

    Git(def script) {
        this.script = script
    }

    String commitMessage() {
        trimOutput("git log --format=%B -n 1 HEAD | head -n 1", 180)
    }

    String commitAuthor() {
        trimOutput("git log --format=\'%an\' -n 1 HEAD", 80)
    }

    String commitHash() {
        trimOutput("git rev-parse HEAD", 7)
    }

    private String trimOutput(String script, int maxLength) {
        String content = this.script.sh(script: script, returnStdout: true)
        content.substring(0, Math.min(maxLength, content.length())).trim()
    }

    String currentBranchName() {
        return script.scm.branches[0].name
        //script.env.GIT_BRANCH
    }
    boolean isMasterBranch() {
        script.scm.branches[0].name == 'master'
    }


    String getGitTag() {
        return script.sh(returnStdout: true, script: "git tag --sort version:refname | tail -1").trim()
    }

    def createRelease(String tag) {
        script.sh(returnStdout: false, script: "git tag " + tag)
        script.sh(returnStdout: false, script: "git push origin " + tag)
    }

}