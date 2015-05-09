package parcheesi

import scala.collection.mutable.Stack

class Jailboard(in: List[Player]){
  val playerstorage = in
  val primary = new Stack[Int]
  for (index <- (0 until in.size).reverse){
    {primary.push(in(index).jail)}

  }

  def update(){
    for (index <- (0 until primary.size)){
      primary(index) = playerstorage(index).jail
    }
  }
}