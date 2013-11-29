package fabricator.specs

import spock.lang.Specification
import fabricator.Blueprint

class ExplicitInheritanceSpecs extends Specification {
	def "inherits attributes from the parent blueprint"() {
		setup:
		def parent_blueprint = new Blueprint(Expando, {
			name { "Fred" }
			age  { 97 }
		})
		def child_blueprint = new Blueprint(Expando, parent_blueprint, {
			name { "Bill" }
		})

		when:
		def child = child_blueprint.make()
		
		then:
		child.name == "Bill"
		child.age == 97
	}

	def "takes the serial number from the parent"() {
		setup:
		def parent_blueprint = new Blueprint(Expando, {
			serial { sn() }
		})

		def child_blueprint = new Blueprint(Expando, parent_blueprint, {
			serial { sn() }
		})

		expect:
		parent_blueprint.make().serial == "0001"
		child_blueprint.make().serial == "0002"
		parent_blueprint.make().serial == "0003"
	}
}
