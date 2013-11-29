package fabricator.specs

import spock.lang.Specification
import fabricator.NoBlueprintException

class ExceptionsSpecs extends Specification {

	def "NoBlueprintException presents the right message"() {
		when:
		def exception = new NoBlueprintException(String, "master")
		
		then:
		exception.message == "No master blueprint defined for class String"
	}
}