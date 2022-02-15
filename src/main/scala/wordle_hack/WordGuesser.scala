package wordle_hack

import scala.collection.mutable
import scala.math._
import scala.collection.parallel.CollectionConverters._

sealed case class CandidateWord (
  val word: String,
  val score: Double
) extends Ordered [CandidateWord] {
  override def compare(that: CandidateWord) : Int = {
    if(this.score > that.score) 1
    else if (this.score == that.score) 0
    else -1
  }
}

object WordGuesser {

  def pick_best_candidate(candidate_list: Seq[String]): String = {
    //for each candidate calculate a score
    val score_list = candidate_list.par.map{guess => 
      val entropy = calculate_entropy_score(guess, candidate_list)
      CandidateWord(guess, entropy)
    }

    //pick the highest-scoring candidate
    val best = score_list.max
    best.word

  }

  def calculate_entropy_score(guess: String, candidate_list: Seq[String]): Double = {

    /**
      * the occurence map counts how many target words in the candidate list can cause a specific clue to be generated. 
      * e.g. if guess is "abcde", and candidate_list is ["abcde", "abcdx" and "xxxxx"], then the occurence map will look like: 
        "GGGGG" -> 1,
        "GGGGB" -> 1,
        "BBBBB" -> 1
      */
    val occurence_map  = new mutable.HashMap[String, Int]()

    candidate_list.foreach{c => 
      val clue = generate_clue(guess, c)
      val count = occurence_map.getOrElse(clue, 0)
      occurence_map.put(clue, count + 1)
    }

    //now that we know how many time each clue occurs,
    //calculate the un-normalized entropy of this guess
    val entropy: Double = occurence_map.toSeq.map{x => 
      val count = x._2.toDouble
      -count * log(count)
    }.sum

    entropy
  }

  //reference to Lynn's implementation (https://github.com/lynn/hello-wordl)
  def generate_clue(guess: String, target: String): String = {
    //assert(guess.length == 5)
    //assert(target.length == 5)

    val elusive = new mutable.HashSet[Char]()

    val zipped_pair = target zip guess

    zipped_pair.foreach{
      case (t,g) if (t!=g) => 
        elusive.add(t)
      case _ =>
    }

    val result = zipped_pair.map{ 
        case (t,g) if (t == g) => "G"
        case (t,g) if (t != g && elusive.contains(g)) => 
          elusive.remove(g)
          "Y"
        case _ => "B"
      
    }

    result.mkString
  }
}
