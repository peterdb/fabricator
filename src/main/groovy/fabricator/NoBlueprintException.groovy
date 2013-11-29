package fabricator

public class NoBlueprintException extends RuntimeException {

	public NoBlueprintException(Class klass, String name) {
		super("No $name blueprint defined for class ${klass.simpleName}")
	}
	
}
