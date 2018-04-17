grails.project.work.dir = 'target'
grails.project.target.level = 1.7
grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
	def gebVersion = '1.0'
	def spockVersion = '0.6'
	def groovyVersion = '1.8'
	def seleniumVersion = '2.53.0'

	inherits 'global'
	log 'warn'

        def SP = { key, _default='' -> [System.properties[key], System.env[key.toUpperCase().replace('.', '_')], _default].find { it != null } }


	repositories {
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
		mavenRepo('http://dev.frontlinesms.com/m2repo/') {
                        authentication(
                                username: SP('FRONTLINESMS_MAVEN_USERNAME'),
                                password: SP('FRONTLINESMS_MAVEN_PASSWORD_HTTP')
                        )
                }
                mavenRepo "http://repo.grails.org/grails/core"
		mavenRepo "http://dl.bintray.com/alkemist/maven/"
	}

	dependencies {
		compile "org.gebish:geb-spock:$gebVersion"
                compile "org.gebish:geb-junit4:$gebVersion"
		compile "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		compile "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		compile "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		compile "org.seleniumhq.selenium:selenium-remote-driver:$seleniumVersion"
		test 'org.apache.httpcomponents:httpclient:4.3.1' // Required for selenium 2.37.0 due to https://code.google.com/p/selenium/issues/detail?id=6432
	}

	plugins {
		build ':release:3.1.0'
		compile ":tomcat:7.0.42"

		compile ':build-test-data:2.2.1', {
			export = false
		}
		compile ":geb:$gebVersion"

		compile ':remote-control:2.0'

		compile ":code-coverage:1.2.6"
		compile ':codenarc:0.18.1'

		build ':bails:0.6'
	}
}

