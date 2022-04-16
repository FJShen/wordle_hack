package wordle_hack

import scala.io.{Source, StdIn}
import scala.util.Random


object main extends App {

  WordleHack.run()

}


object WordleHack {

  //read lines from file
  val raw_dict_path = "resources/nyt_allowed.txt"
  val words : Seq[String] = load_5_letter_words(raw_dict_path)

  val rnd = Random

  var candidate_list : Seq[String] = words
  var clues = new WordGuessClues

  def run() = {

    var exit = false

    while(!exit){
      exit = false
      val loop_status = loop_body()
      loop_status match {
        case "exit" => exit = true
        case "restart" =>
          candidate_list = words
          clues = new WordGuessClues
        case _ => //do nothing
      }
    }
    
  }

  def loop_body():String  = {
      //filter out acceptable candidates
      candidate_list = WordSieve.get_candidates(clues, candidate_list)
      println(s"candidate list size: ${candidate_list.size}")

      //generate a guess
      println("generating an optimal guess...")
      val guess = WordGuesser.pick_best_candidate(candidate_list)
      println(s"guess:  ${guess.toUpperCase()}")

      //read feedback and return loop_status
      val feedback = read_feedback()
      if(feedback.contains('0')) {
        "exit"
      }
      else if(feedback.contains('1')) {
        "restart"
      }
      else{
        //update clues
        clues.update_clues(guess, feedback)
        "continue"
      }
  }


  def load_5_letter_words(raw_dict_path: String) : Seq[String] = {
    val lines_of_file_it = Source.fromFile(raw_dict_path).getLines()

    //filter out lines that matches regex
    val word_regex = """[\w]{5}""".r
    println(s"Filtering out words with: $word_regex")
    val word_lines_it = lines_of_file_it.filter{ x =>
      word_regex.matches(x)
    }

    word_lines_it.toSeq
  }

  def read_feedback() : String = {
    val feedback = StdIn.readLine(s"""Input the feedback in the format of FFFFF,                    
                              |where F can be one of: 
                              |G (Green) / Y (Yello) / B (Black)
                              |Or type 'X' to indicate word not accepted 
                              |type '0' to exit program 
                              |type '1' to restart
                              |> """.stripMargin)
    feedback.toUpperCase
  }

}
