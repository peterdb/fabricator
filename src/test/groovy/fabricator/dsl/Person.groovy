package fabricator.dsl

import groovy.transform.ToString;

@ToString
class Person {
	def firstName
	def lastName
	List pets
}
