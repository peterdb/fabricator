package fabricator

import fabricator.support.Blueprints

class Fabricator {

	final static Blueprints blueprints = new Blueprints()

	public static Blueprint blueprint(Class klass, String name = "master", Closure closure) {
		def parent = (name == "master" ? klass.superclass : klass)
		Blueprint blueprint = new Blueprint(klass, parent, closure)
		
		blueprints.forClass(klass)[name] = blueprint

		return blueprint
	}
	
	public static Blueprint getBlueprint(Class klass, String name = "master") {
		return blueprints.forClass(klass)[name]
	}
		
	public static <T> T make(Class<T> klass, int count, String name = "master", Map<String, Closure> overrides = [:]) {
		assert klass
		assert count > 0, "count must be > 0"
		
		return (1..count).collectAll { make(klass, name, overrides) }
	}
	
	public static <T> T make(Class<T> klass, String name = "master", Map<String, Closure> overrides = [:]) {
		Blueprint blueprint = blueprints.forClass(klass)[name]
		
		if(!blueprint) {
			throw new NoBlueprintException(klass, name)
		}
		
		return blueprint.make(overrides)
	}
	
	public static void clearBlueprints(Class self) {
		blueprints.forClass(self).clear()
	}
	
}
