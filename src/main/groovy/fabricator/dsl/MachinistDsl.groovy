package fabricator.dsl

import fabricator.Blueprint
import fabricator.Fabricator

@Category(Class)
class MachinistDsl {

	public static Blueprint blueprint(Class self, name = "master", Closure closure) {
		return Fabricator.blueprint(self, name, closure)
	}
	
	public static Blueprint getBlueprint(Class self, name = "master") {
		return Fabricator.getBlueprint(self, name)
	}

	public static <T> List<T> make(Class<T> self, int count, String name = "master") {
		return Fabricator.make(self, count, name)
	}

	public static <T> T make(Class<T> self, String name = "master", Map<String, Object> overrides = [:]) {
		return Fabricator.make(self, name, overrides)
	}
	
	public static void clearBlueprints(Class self) {
		Fabricator.clearBlueprints(self)
	}

}
