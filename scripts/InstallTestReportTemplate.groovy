target('default':'Copy test report templates to the top-level project.') {
	targetDir = "${basedir}/test/conf"
	ant.mkdir dir:targetDir

	println "################"
	println "frontlinesmsGrailsTestPluginDir = $frontlinesmsGrailsTestPluginDir"
	println "################"

	ant.copy todir:targetDir, {
		dirset dir:"${frontlinesmsGrailsTestPluginDir}/src/templates/test-reports"
	}
}

