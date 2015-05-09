package parcheesi

import scala.collection.mutable.Stack

class Homeboard(in: List[Player]) {
  val playerstorage = in
  val primary = new Stack[Int]
  for (index <- (0 until in.size).reverse){
    {primary.push(in(index).home)}

  }

  def update(){
    for (index <- (0 until primary.size)){
      primary(index) = playerstorage(index).home
    }
  }
}