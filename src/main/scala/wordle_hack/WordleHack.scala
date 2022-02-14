package wordle_hack

import scala.io.{Source, StdIn}
import scala.util.Random
import javax.print.attribute.standard.PrinterURI

object WordleHack {

  //read lines from file
  val raw_dict_path = "dictionary/enable1.txt"
  val words : Seq[String] = load_5_letter_words(raw_dict_path)

  val rnd = Random


  def run() = {
    var candidate_list : Seq[String] = words
    val clues = new WordGuessClues

    var exit = false
    var restart = true
    var num_of_trial = 1
    var regex = null

    while(!exit){
      //filter out acceptable candidates
      candidate_list = WordSieve.get_candidates(clues, candidate_list)
      println(s"candidate list size: ${candidate_list.size}")

      //generate a guess
      val guess = if(num_of_trial ==1) "tares" else WordGuesser.pick_best_candidate(candidate_list)
      //val guess = generate_guess(candidate_list)
      println(s"guess: $guess")

      //read feedback
      val feedback = read_feedback()
      if(feedback.contains('0')) {
        exit = true
      }
      else{
        //update clues
        clues.update_clues(guess, feedback)

        num_of_trial += 1
      }
    }
    
  }



  def load_5_letter_words(raw_dict_path: String) : Seq[String] = {
    val lines_of_file_it = Source.fromFile(raw_dict_path).getLines()

    //filter out lines that matches regex
    val word_regex = """[\w]{1,}""".r
    println(s"Using this regex to filter out words:  $word_regex")
    val word_lines_it = lines_of_file_it.filter{ x =>
      x.length==5 && word_regex.matches(x)
    }

    word_lines_it.toSeq
  }

  def generate_guess(candidates: Seq[String]): String = {
    candidates(rnd.nextInt(candidates.length))
  }

  def read_feedback() : String = {
    val feedback = StdIn.readLine(s"""Input the feedback in the format of FFFFF,                    
                              |where F can be one of: 
                              |G=Green
                              |Y=Yello
                              |B=Black
                              |X=not a word
                              |Or type '0' to exit program / type '1' to restart
                              |> """.stripMargin)
    feedback.toUpperCase
  }


}
