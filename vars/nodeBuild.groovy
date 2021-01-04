import com.two57.jenkins.sharedlib.NodeJSBuild

NodeJSBuild call(def scriptReference, String projectName) {
    return new NodeJSBuild(scriptReference, projectName, env['BRANCH_NAME'])
}

return this