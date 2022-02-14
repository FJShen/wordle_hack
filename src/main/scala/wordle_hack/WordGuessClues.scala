package wordle_hack

import scala.collection.mutable

sealed trait LetterStatus

case class DeterminedLetter (
  letter : Char
) extends LetterStatus {
  override def toString: String = {
    letter.toString
  }
}

case class UndeterminedLetter(
  prohimited_letter_table : mutable.HashSet[Char]
) extends LetterStatus

class WordGuessClues (
  val letter_status_array : mutable.Seq[LetterStatus] = mutable.Seq.fill(5)(
    new UndeterminedLetter(new mutable.HashSet[Char])),
  val must_exist_letters : mutable.HashSet[Char] = mutable.HashSet[Char](),
  val must_not_exist_letters : mutable.HashSet[Char] = mutable.HashSet[Char](),
  val banned_words : mutable.HashSet[String] = mutable.HashSet[String]()
  ){


  def update_clues(guess: String, feedback: String) = {
    assert(feedback.length == 5)
    assert(guess.length == 5)

    if(feedback == "XXXXX"){
      banned_words.add(guess)
    }

    for(idx <- 0 until 5){
      val c = feedback.charAt(idx)
      val letter = guess.charAt(idx)

      c match {
        case 'G' => 
          letter_status_array(idx) = new DeterminedLetter(letter)
          must_exist_letters.add(letter)

        case 'Y' =>
          assert(letter_status_array(idx).isInstanceOf[UndeterminedLetter])
          letter_status_array(idx).asInstanceOf[UndeterminedLetter].prohimited_letter_table.add(letter)
          must_exist_letters.add(letter)

        case 'B' =>
          must_not_exist_letters.add(letter)
          letter_status_array(idx).asInstanceOf[UndeterminedLetter].prohimited_letter_table.add(letter)

        case _ =>
      }
    }

    //if one of the must_exist_letters are marked black (B) at another location, 
    //remove it from the must_not_exist_letters set
    must_exist_letters.foreach{x =>
      if(must_not_exist_letters.contains(x)){
        must_not_exist_letters.remove(x)
      }  
    }
    
  }
}
