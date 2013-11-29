package fabricator.support

import fabricator.Blueprint

class Blueprints {

	// TODO store the blueprints
	final static Map<Class<?>, Map<String, Blueprint>> blueprints = [:]
	
	public Map<String, Blueprint> forClass(Class<?> klass) {
		if(!blueprints[klass]) {
			blueprints[klass] = [:]
		}
		
		return blueprints[klass]
	}
	
	public void clear() {
		blueprints.clear()
	}
}
