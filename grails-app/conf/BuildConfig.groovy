grails.project.work.dir = 'target'
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
	def gebVersion = '0.7.2'
	def seleniumVersion = '2.32.0'

	inherits 'global'
	log 'warn'

	repositories {
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
		mavenRepo 'http://dev.frontlinesms.com/m2repo/'
	}

	dependencies {
		test "org.codehaus.geb:geb-spock:$gebVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-remote-driver:$seleniumVersion"
	}

	plugins {
		build ':release:2.2.1',
				':rest-client-builder:1.0.3', {
			export = false
		}
		compile ":tomcat:$grailsVersion"

		compile ':build-test-data:2.0.5'
		compile ':spock:0.6'
		compile ":geb:$gebVersion"
		compile ':remote-control:1.4'

		compile ':codenarc:0.18.1'
	}
}
