package parcheesi

import scala.collection.mutable.Stack

class TokenLibrary(z: List[Player]){
  val primary = new Stack[Token]
  for (playr <- z.reverse){
    primary.push(new Token(playr,-1))
    primary.push(new Token(playr,-1))
    primary.push(new Token(playr,-1))
    primary.push(new Token(playr,playr.start))
  }
  for (item <- (0 until primary.size)){
    primary(item).tokenID = item
  }
  val count = primary.size
  
  def display(): String = {
    var z = ""
    for (index <- (0 until primary.size)){
      z += ("Token " + (primary(index).tokenID+1) + ": ")
      if (primary(index).location == -1){z+="Jail"}
      else if (primary(index).location == -2){z+="Home"}
      else{z+=primary(index).location.toString}
      z += "\n"
    }
    z
  }
}