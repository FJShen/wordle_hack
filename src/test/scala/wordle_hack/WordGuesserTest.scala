package wordle_hack

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class WordGuesserTest extends AnyFlatSpec with Matchers{
  "The WordGuesser" should "give accurate feedback" in {
    WordGuesser.generate_clue("truck", "ambey") shouldEqual "BBBBB"
    WordGuesser.generate_clue("amber", "ambre") shouldEqual "GGGYY"
    WordGuesser.generate_clue("mills", "lying") shouldEqual "BYYBB"
    WordGuesser.generate_clue("lying", "mills") shouldEqual "YBYBB"
    WordGuesser.generate_clue("lllxx", "lopll") shouldEqual "GYBBB"
    WordGuesser.generate_clue("abcde", "abcde") shouldEqual "GGGGG"
  }
}
