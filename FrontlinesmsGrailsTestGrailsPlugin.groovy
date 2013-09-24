import frontlinesms.grails.test.EchoMessageSource
import grails.util.Environment

class FrontlinesmsGrailsTestGrailsPlugin {
	def version = '0.13-SNAPSHOT'
	def grailsVersion = '2.0 > *'
	def title = 'Frontlinesms Test Plugin'
	def author = 'Alex Anderson'
	def authorEmail = 'alex@frontlinesms.com'
	def description = 'Common dependencies for testing FrontlineSMS Grails projects.'
	def documentation = 'http://grails.org/plugin/frontlinesms-test'
	def license = 'APACHE'
	def organization = [name:'FrontlineSMS', url:'http://www.frontlinesms.com/']

	def doWithSpring = {
		if(!application.config['frontlinesms.test.i18n.echo.disable'] && (
				application.config['frontlinesms.test.i18n.echo.force'] ||
				Environment.current == Environment.TEST ||
				(Environment.current == Environment.CUSTOM && Environment.current.contains('test')))) {
			messageSource(EchoMessageSource)
		}
	}
}

