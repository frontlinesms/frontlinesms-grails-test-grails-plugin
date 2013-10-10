package frontlinesms.grails.test

import java.text.SimpleDateFormat
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable

class EchoMessageSource implements MessageSource {
	static final String DATE_FORMAT = 'yyyy-dd-MM_hh:mm'
	private static final DATE_FORMAT_KEYS = ['date.format', 'default.date.format']

	String getMessage(MessageSourceResolvable resolvable, Locale locale) {
		getMessage(resolvable.codes[0], resolvable.arguments)
	}

	String getMessage(String code, Object[] args) {
		getMessage(code, args, null)
	}

	String getMessage(String code, Object[] args, Locale locale) {
		if(code in DATE_FORMAT_KEYS) {
			// This is the date format key used in grails by default.
			// All dates should be formatted in a standard way in tests.
			return DATE_FORMAT
		} else if(args) {
			return "${code}[${args.join(',')}]"
		} else {
			return code
		}
	}

	String 	getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		getMessage(code, args, locale)
	}

	static String formatDate(Date d) {
		new SimpleDateFormat(DATE_FORMAT).format(d)
	}

	static Date parseDate(String dateString) {
		new SimpleDateFormat(DATE_FORMAT).parse(dateString)
	}
}

