package fabricator.dsl


class TTT {
	public static void main(String[] args) {
		use(MachinistDsl) {
			Person.blueprint {
				firstName { "Peter" }
				lastName { "De Bruycker" }
				pets 2, { Pet.make() }
			}
			
			Pet.blueprint {
				name { "Generic pet ${sn()}" }				
			}

			Pet.blueprint("garfield") {
				name { "Garfield" }
			}

			println Person.make()
			println Pet.make("garfield")

			println Person.class.blueprint {
				
			}
						
			println Person.class.make(2)
		}
	}
}
