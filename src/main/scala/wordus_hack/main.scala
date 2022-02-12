package wordus_hack

import scala.io.{Source, StdIn}
import scala.util.matching._
import scala.collection.immutable.ArraySeq
import scala.util.Random
import scala.collection.mutable.{HashSet, Seq}


object main extends App {

  WordusHack.run

  /*val letter_status_array: Seq[LetterStatus] = Seq.fill(5)(new UndeterminedLetter(new HashSet[Char]))
  val must_exist_letters = HashSet[Char]()
  val must_not_exist_letters = HashSet[Char]()
  val rnd = Random
  
  //read lines from file
  val raw_dict_path = "resources/dict.txt"
  val lines_of_file_it = Source.fromFile(raw_dict_path).getLines()

  //filter out lines that matches regex
  val word_regex = """[A-Z]{1,}""".r
  println(s"Using this regex to filter out words:  $word_regex")
  val word_lines_it = lines_of_file_it.filter{ x =>
    x.length==5 &&  word_regex.matches(x)
  }
  
  val words = word_lines_it.toIndexedSeq
  //print_iterable(words)

  var num_of_trial=1
  var guess: String = ""

  make_tries()

  def get_random_word(seq_of_words : IndexedSeq[String]): String = {
    seq_of_words(rnd.nextInt(seq_of_words.length))
  }

  def print_iterable(it : IterableOnce[_]) = {
    it.iterator.foreach(println(_))
  }

  def make_tries()={
    guess = get_random_word(words)
    println(s"first guess is $guess")
    while(true){
      read_feedback_and_reguess()
      num_of_trial += 1
    }
  }

  def read_feedback_and_reguess(): Unit = {
    val feedback = StdIn.readLine(s"""Input the feedback in the format of FFFFF,                    
                              |where F can be one of G, Y, or B. 
                              |G=Green
                              |Y=Yello
                              |B=Black
                              |X=not a word
                              |${num_of_trial}> """.stripMargin)
    process_feedback(feedback)
    println(letter_status_array)
    val new_regex = generate_regex
    println(s"regex is $new_regex")
    guess = get_random_word(words.filter{w => new_regex.matches(w)})
    println(s"new guess: $guess")
  }

  def generate_regex(): Regex = {
    val lookahead_yes = must_exist_letters.toSeq.map{c =>
      s"(?=\\w*${c}\\w*)"}.mkString
    val lookahead_no = must_not_exist_letters.toSeq.map{c =>
      s"(?=[^${c}]{5})"}.mkString
    val regex_body = letter_status_array.map{_ match {
      case a: DeterminedLetter => s"${a.letter}"
      case a: UndeterminedLetter => 
        if(a.prohimited_letter_table.size>0){
        val letter_string = a.prohimited_letter_table.toSeq.map{c => s"$c"}.mkString
        "[^" + letter_string + "]"}
        else{"[A-Z]"}
    }}.mkString
    (lookahead_yes + lookahead_no + regex_body).r
  }

  def process_feedback(feedback: String) = {
    if(feedback.length != 5){
      println("must be 5 chars, do it again!")
      read_feedback_and_reguess()
    }
    else if(feedback.contains("X")){
      
    }
    else{
      for(idx <- 0 until 5){
        val c = feedback.charAt(idx)
        c match {
          case 'G' => 
            letter_status_array(idx) = new DeterminedLetter(guess.charAt(idx))
          case 'Y' => 
            letter_status_array(idx).asInstanceOf[UndeterminedLetter].prohimited_letter_table.add(guess.charAt(idx))
            must_exist_letters.add(guess.charAt(idx))
          case 'B' => must_not_exist_letters.add(guess.charAt(idx))
        }
      }
    }

  }*/
  

}
