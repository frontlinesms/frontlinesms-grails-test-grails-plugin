package frontlinesms.grails.test

import spock.lang.*
import grails.plugin.spock.*

class TodoISpec extends IntegrationSpec {
	def 'TODO alone should work'() {
		when:
			TODO
		then:
			RuntimeException ex = thrown()
			ex.message == 'TODO: Not yet implemented.'
	}

	def 'TODO as method should work'() {
		when:
			TODO()
		then:
			RuntimeException ex = thrown()
			ex.message == 'TODO: Not yet implemented.'
	}

	def 'TODO with arg should work'() {
		when:
			TODO('custom message')
		then:
			RuntimeException ex = thrown()
			ex.message == 'TODO: custom message'
	}
}

