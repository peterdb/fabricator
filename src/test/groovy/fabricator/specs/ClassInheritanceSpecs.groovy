package fabricator.specs

import fabricator.NoBlueprintException;
import fabricator.dsl.MachinistDsl;
import spock.lang.Specification;
import spock.util.mop.Use;

@Use(MachinistDsl)
class ClassInheritanceSpecs extends Specification {
	static class Grandpa {
		def name
		def age
	}

	static class Dad extends Grandpa {
	}

	static class Son extends Dad {
	}

	def setup() {
		[Grandpa, Dad, Son].each { it.clearBlueprints() }
	}

	def "inherits blueprinted attributes from the parent class"() {
		setup:
		Dad.blueprint { 
			name { "Fred" }
		}
		Son.blueprint {}

		when:
		def son = Son.make()

		then:
		son.name == "Fred"
	}

	def "overrides blueprinted attributes in the child class"() {
		setup:
		Dad.blueprint { 
			name { "Fred" }
		}
		Son.blueprint {
			name { "George" }
		}

		when:
		def dad = Dad.make()
		def son = Son.make()

		then:
		dad.name == "Fred"
		son.name == "George"
	}
	
	def "inherits from blueprinted attributes in ancestor class"() {
		setup:
		Grandpa.blueprint {
			name { "Fred" }
		}
		Son.blueprint { }

		when:
		def grandpa = Grandpa.make()
		def son = Son.make()
		Dad.make()

		then:
		grandpa.name == "Fred"
		thrown(NoBlueprintException)
		son.name == "Fred"
	}
	
	def "follows inheritance for named blueprints correctly"() {
		setup:
		Dad.blueprint {
		  name { "John" }
		  age  { 56 }
		}
		Dad.blueprint("special") {
		  name { "Paul" }
		}
		Son.blueprint("special") {
		  age { 37 }
		}
		
		when:
		def son = Son.make("special")
		
		then:
		son.name == "John"
		son.age == 37
	}
}
