package parcheesi

import scala.collection.mutable.Stack

class Board {
  val primary = new Stack[Tile]
  for (index <- (0 until 80).reverse){
    if ((index-5) % 10 != 0){primary.push(new Tile(index,true))}
    else{primary.push(new Tile(index,false))}
  }

  val count = primary.size
  
  def showPlayingArea(TLib: TokenLibrary): String = {
    var z = ""
    for (index <- (1 until primary.size)){
      z += "At location: " + index.toString + ". "
      for (index2 <- primary(index).check(this,TLib)){
        z += index2.tokenID+1
        z += " "}
      z += "\n"
    }
    z
  }
}