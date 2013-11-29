package fabricator

import spock.util.mop.Use;
import fabricator.dsl.Lathe
import fabricator.dsl.MachinistDsl;

/**
 * A {@link Blueprint}
 * @author peter
 */
class Blueprint {

	final parent
	final Class klass
	final Closure closure
	
	private int serialNumber = 0

	public Blueprint(Class klass, Closure closure) {
		assert klass
		
		this.klass = klass
		this.closure = closure
	}

	public Blueprint(Class klass, def parent, Closure closure) {
		assert klass
		
		this.klass = klass
		this.parent = parent
		this.closure = closure
	}

	private static int count = 0
	
	public Object make(Map overrides = [:]) {
		println "making! $klass"
		def lathe = new Lathe(klass, this.&newSerialNumber, overrides)

		count++		
		if(count == 3) {
			Thread.dumpStack()
		}
		
		lathe.with(closure)
		ancestors().each { Blueprint ancestor -> lathe.with(ancestor.closure) }
		
		return lathe.object
	}
	
	def ancestors() {
		def ancestors = []
		
		Blueprint ancestor = parent()
		while(ancestor) {
		  ancestors << ancestor
		  ancestor = ancestor.parent()
		}
		
		return ancestors
	}
	
	def Blueprint parent() {
		switch (parent) {
		case null:
			return null;
		case Blueprint:
		  	//parent references the parent blueprint directly.
			return parent;
		default:
			// parent is a class in which we should look for a blueprint.
			find_blueprint_in_superclass_chain(parent)
		}
	}
	
	protected String newSerialNumber() {
		def parent = parent()
		if(parent) {
			return parent.newSerialNumber()
		} else {
			serialNumber++
			return String.format('%04d', serialNumber)
		}
	}
	
	private Blueprint find_blueprint_in_superclass_chain(Class klass) {
		use(MachinistDsl) {
			while(klass != null && !hasBlueprint(klass)) {
				klass = klass.superclass
			}
			return klass?.blueprint
		}
	}

	private boolean hasBlueprint(Class klass) {
		use(MachinistDsl) {
			return klass.blueprint != null
		}
	}
}
