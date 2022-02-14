package wordle_hack

import scala.util.matching._

object WordSieve{
  def get_candidates(clues: WordGuessClues, unfiltered: Seq[String]) : Seq[String] = {
    val new_regex = generate_regex(clues)
    println(s"regex is ${new_regex}")
    unfiltered.filter(new_regex.matches(_))
  }

  def generate_regex(clues: WordGuessClues): Regex = {
    val lookahead_yes = clues.must_exist_letters.toSeq.map{c =>
      s"(?=\\w*${c}\\w*)"}.mkString
    val lookahead_no = if(clues.must_not_exist_letters.size>0){
      "(?=[^" + clues.must_not_exist_letters.toSeq.mkString + "]{5})"
    } else ""
      
    val lookahead_ban = clues.banned_words.toSeq.map{w =>
      s"(?!$w)"}.mkString
    val regex_body = clues.letter_status_array.map{_ match {
      case a: DeterminedLetter => s"${a.letter}"
      case a: UndeterminedLetter => 
        if(a.prohimited_letter_table.size>0){
        val letter_string = a.prohimited_letter_table.toSeq.map{c => s"$c"}.mkString
        "[^" + letter_string + "]"}
        else{"\\w"}
    }}.mkString
    (lookahead_yes + lookahead_no + lookahead_ban + regex_body).r
  }
}