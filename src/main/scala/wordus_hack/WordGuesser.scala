package wordus_hack

import scala.collection.mutable.HashSet

object WordGuesser {

  //logic is referenced from XXX's implementation
  def generate_clue(guess: String, target: String): String = {
    assert(guess.length == 5)
    assert(target.length == 5)

    val elusive = new HashSet[Char]()

    target.zip(guess).foreach{(t, g) => 
      if(t!=g) {
        elusive.add(t)
      }
    }

    val result = target.zip(guess).map{ (t,g) =>
      case t == g => "G"
      case t != g if elusive.contains(g) => 
        elusive.remove(g)
        "Y"
      case _ => "B"
    }

    result.mkString
  }
}
