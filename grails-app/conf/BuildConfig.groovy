grails.project.work.dir = 'target'
grails.project.target.level = 1.7

grails.project.dependency.resolution = {
	def gebVersion = '0.7.2'
	def spockVersion = '0.6'
	def groovyVersion = '1.8'
	def seleniumVersion = '2.45.0'

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
		compile "org.codehaus.geb:geb-spock:$gebVersion"
		compile "org.spockframework:spock-grails-support:$spockVersion-groovy-$groovyVersion"
		compile "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
		compile "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		compile "org.seleniumhq.selenium:selenium-remote-driver:$seleniumVersion"
		test 'org.apache.httpcomponents:httpclient:4.3.1' // Required for selenium 2.37.0 due to https://code.google.com/p/selenium/issues/detail?id=6432
	}

	plugins {
		build ':release:2.2.1',
				':rest-client-builder:1.0.3', {
			export = false
		}
		compile ":tomcat:$grailsVersion"

		compile ':build-test-data:2.0.5'
		compile ":spock:$spockVersion", {
			exclude 'spock-grails-support'
		}
		compile ":geb:$gebVersion"
		compile ':remote-control:1.4'

		compile ":code-coverage:1.2.6"
		compile ':codenarc:0.18.1'

		build ':bails:0.6'
	}
}

