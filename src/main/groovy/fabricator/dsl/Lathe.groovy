package fabricator.dsl

import fabricator.Blueprint

/**
 * @author peter
 */
class Lathe {

	final object
	final Closure serialNumber
	final assignedProperties = [:]

	public Lathe(Class klass, Closure serialNumber, Map properties = [:]) {
		this.serialNumber = serialNumber

		this.object = klass.newInstance()
		properties.each { property, value ->
			assignProperty(property, value)
		}
	}

	def methodMissing(String property, args) {
		if(!propertyAssigned(property)) {
			assignProperty(property, makeProperty(property, new ArrayList(Arrays.asList(args))))
		}
	}

	def sn() {
		serialNumber()
	}

	private assignProperty(property, value){
		assignedProperties[property] = value

		object[property] = value
	}

	private makeProperty(property, args) {
		def count = (args.get(0) instanceof Integer) ? args.remove(0) : 0

		if(count) {
			return (1..count).collectAll { makeOneValue(property, args) }
		}
		else {
			return makeOneValue(property, args)
		}
	}

	private boolean propertyAssigned(property) {
		return assignedProperties.containsKey(property)
	}

	private makeOneValue(property, args) {
		assert args.size() == 1
		assert args[0] instanceof Closure

		Closure value = args[0]
		this.with(value)
	}
}
