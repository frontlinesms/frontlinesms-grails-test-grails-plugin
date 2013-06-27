import groovy.sql.Sql

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
	def specMeta = Class.forName("spock.lang.Specification").metaClass.static
	specMeta.getTODO = { throw new RuntimeException('TODO: Not yet implemented.') }
	specMeta.TODO = { throw new RuntimeException('TODO: Not yet implemented.') }
	specMeta.TODO = { m -> throw new RuntimeException("TODO: $m") }
}

eventTestStart = { testName ->
	if(currentTestPhase == 'unit') {
		if(!metaClassModifiers) {
			try {
				metaClassModifiers = Class.forName('frontlinesms2.MetaClassModifiers')
			} catch(ClassNotFoundException ex) { println 'Events.eventTestPhaseStart() :: frontlinesms2.MetaClassModifiers not found.  Ignoring.' }
		}
		if(metaClassModifiers) {
			println 'Events.eventTestStart(unit) :: Adding standard FrontlineSMS metaclass modifications...'
			metaClassModifiers.addAll()
			println 'Events.eventTestStart(unit) :: testing string truncate...'
			println "Events.eventTestStart(unit) :: ${('a' * 100).truncate(20)}"
		}
	}

	if(currentTestPhase == 'integration') {
		dropDataFromDb()
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

String _databaseType
def getDatabaseType() {
	if(!_databaseType) {
		def driverName = getDataSource().driverClassName
		_databaseType = driverName.startsWith('com.mysql.')? 'mysql':
				driverName.startsWith('org.h2.')? 'h2':
				'unknown'
	}
	return _databaseType
}

def getSqlConnector() {
	def dataSource = getDataSource()
	Sql.newInstance(dataSource.url, dataSource.username, dataSource.password, dataSource.driverClassName)
}

def _dataSource
def getDataSource() {
	if(!_dataSource) {
		String env = System.getProperty('grails.env')?: 'test'

		URL url = new File("${basedir}/grails-app/conf/DataSource.groovy").toURL()
		_dataSource = new ConfigSlurper(env).parse(url).dataSource
	}
	return _dataSource
}

def dropDataFromDb() {
	if(databaseType == 'h2') {
		def sql = getSqlConnector()
		sql.execute "SET REFERENTIAL_INTEGRITY FALSE"
		sql.eachRow("SHOW TABLES") { table -> sql.execute('DELETE FROM ' + table.TABLE_NAME) } 
		sql.execute "SET REFERENTIAL_INTEGRITY TRUE"
	} else if(databaseType == 'mysql') {
		def sql = getSqlConnector()
		sql.execute 'SET FOREIGN_KEY_CHECKS = 0'
		sql.eachRow('SHOW TABLES') { table ->
			def tableName = table[0]
			sql.execute "DELETE FROM $tableName".toString()
		}
		sql.execute 'SET FOREIGN_KEY_CHECKS = 1'
	} else {
		throw new RuntimeException("Do not know how to drop data from database of type $databaseType")
	}
}

