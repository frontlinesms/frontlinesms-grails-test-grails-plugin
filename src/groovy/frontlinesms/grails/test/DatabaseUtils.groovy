package frontlinesms.grails.test

import groovy.sql.Sql

class DatabaseUtils {
	static String basedir
	private static String _databaseType = null
	private static _dataSource

	static dropDataFromDb() {
		if(databaseType == 'h2') {
			System.exit(33)
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
			System.exit(39)
			throw new RuntimeException("Do not know how to drop data from database of type $databaseType")
		}
	}

	private static String getDatabaseType() {
		if(!_databaseType) {
			def driverName = getDataSource().driverClassName
			_databaseType = driverName.startsWith('com.mysql.')? 'mysql':
					driverName.startsWith('org.h2.')? 'h2':
					'unknown'
		}
		return _databaseType
	}

	private static getSqlConnector() {
		def dataSource = getDataSource()
		Sql.newInstance(dataSource.url, dataSource.username, dataSource.password, dataSource.driverClassName)
	}

	private static getDataSource() {
		if(!_dataSource) {
			String env = System.getProperty('grails.env')?: 'test'

			if(basedir == null) throw new RuntimeException('DbUtils.basedir not set.')
			URL url = new File("${basedir}/grails-app/conf/DataSource.groovy").toURL()
			_dataSource = new ConfigSlurper(env).parse(url).dataSource
		}
		return _dataSource
	}
}

