import com.two57.jenkins.sharedlib.JavaBuild

JavaBuild call(def scriptReference, String projectName) {
    return new JavaBuild(scriptReference, projectName, env['BRANCH_NAME'])
}

return this