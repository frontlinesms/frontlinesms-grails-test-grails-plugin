eventDefaultStart = {
	createUnitTest = { Map args = [:] ->
		createSpec('unit', args)
	}
	createIntegrationTest = { Map args = [:] ->
		createSpec('integration', args)
	}
	createSpec = { String type, Map args ->
		def superClass
		// map test superclass to Spock equivalent
		switch (args["superClass"]) {
			case "Controller${type.capitalize()}TestCase":
				superClass = "ControllerSpec"
				break
			case "TagLibUnitTestCase":
				superClass = "TagLibSpec"
				break
		// TODO add a case for Camel Route integration test case
			default:
				superClass = "${type.capitalize()}Spec"
		}
		createArtifact name: args["name"], suffix: "${args['suffix']}Spec", type: "Spec", path: "test/${type}", superClass: superClass
	}
}

String currentTestPhase
def metaClassModifiers
eventTestPhaseStart = { phaseName ->
	currentTestPhase = phaseName

	//> Configure test templates
	def testTemplateSrcDir = 'test/conf'
	if(new File(testTemplateSrcDir).exists()) {
		junitReportStyleDir = testTemplateSrcDir
	}

	//> Configure TODO methods on tests
	def todoMixin = { className ->
		def meta = Class.forName(className).metaClass.static
		meta.getTODO = { throw new RuntimeException('TODO: Not yet implemented.') }
		meta.TODO = { throw new RuntimeException('TODO: Not yet implemented.') }
		meta.TODO = { m -> throw new RuntimeException("TODO: $m") }
	}
	todoMixin('spock.lang.Specification')
	todoMixin('grails.test.spock.IntegrationSpec')
}

eventTestStart = { testName ->
	if(currentTestPhase == 'unit') {
		if(!metaClassModifiers) {
			try {
				metaClassModifiers = classLoader.loadClass('frontlinesms2.MetaClassModifiers')
			} catch(ClassNotFoundException ex) {
				grailsConsole.info 'Events.eventTestPhaseStart() :: frontlinesms2.MetaClassModifiers not found.  Ignoring.' }
		}
		if(metaClassModifiers) {
			metaClassModifiers.addAll()
		}
	}
}

eventTestPhaseEnd = { phaseName ->
	if (phaseName == 'functional' && new File('target/test-reports/geb').exists()) {
		def report = new File('target/test-reports/html/screenshots.html')
		new File('target/test-reports/geb').eachFileRecurse { f ->
			if(!f.name.endsWith('.png')) return
			report.append '<img height="120" src="..' +
			f.path.substring('target/test-reports'.size()) + '"/>\n'
		}
	}
}

