package fabricator.specs

import spock.lang.Specification
import fabricator.Blueprint

class BlueprintSpecs extends Specification {

	def "makes an object of the given class"() {
		setup:
		def blueprint= new Blueprint(Expando)

		when:
		def object = blueprint.make()

		then:
		object instanceof Expando
	}

	def "constructs an attribute from the blueprint"() {
		setup:
		def blueprint= new Blueprint(Expando, {
			name { "Fred" }
		})

		when:
		def object = blueprint.make()

		then:
		object.name == "Fred"
	}

	def "constructs a list for an attribute in the blueprint"() {
		setup:
		def blueprint = new Blueprint(Expando, {
			things(3) { new Object() }
		})

		when:
		def things = blueprint.make().things

		then:
		things instanceof List
		things.size() == 3
		things.each {thing -> assert thing instanceof Object }
		things.unique() == things
	}

	def "allows passing in attributes to override the blueprint"() {
		setup:
		def closureCalled = false
		def blueprint = new Blueprint(Expando, {
			name { closureCalled = true; "Fred" }
		})

		when:
		def object = blueprint.make([name: "Bill"])

		then:
		closureCalled == false
		object.name == "Bill"
	}
	
	def "provides a serial number within the blueprint"() {
		setup:
		def blueprint = new Blueprint(Expando, {
			name { "Fred ${sn()}" }
		})
		
		when:
		def fred1 = blueprint.make()
		def fred2 = blueprint.make()
		
		then:
		fred1.name == "Fred 0001"
		fred2.name == "Fred 0002"
	}
	
	def "provides access to the object being constructed within the blueprint"() {
		setup:
			def blueprint = new Blueprint(Expando, {
			  title { "Test" }
			  body  { object.title }
			})
			
		when:
		def object = blueprint.make()
		
		then:
		object.body == "Test"
	}
}