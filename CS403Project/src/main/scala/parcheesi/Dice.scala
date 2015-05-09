package parcheesi

import scala.util.Random

class Dice {
  def roll(): Int = {Random.nextInt(6)+1}
}