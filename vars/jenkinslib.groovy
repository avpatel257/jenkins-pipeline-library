import com.two57.jenkins.sharedlib.JenkinsBuilder

JenkinsBuilder call(def scriptReference, String projectName) {
    return new JenkinsBuilder(scriptReference,projectName,env['BRANCH_NAME'])
}

return this
