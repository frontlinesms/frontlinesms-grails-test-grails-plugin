package frontlinesms.grails.test

import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable

class EchoMessageSource implements MessageSource {
	String getMessage(MessageSourceResolvable resolvable, Locale locale) {
		getMessage(resolvable.codes[0], resolvable.arguments)
	}

	String getMessage(String code, Object[] args, Locale locale) {
		if(args) {
			return "${code}[${args.join(',')}]"
		} else {
			return code
		}
	}

	String 	getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		getMessage(code, args, locale)
	}
}

