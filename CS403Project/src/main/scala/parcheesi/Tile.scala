package parcheesi

import scala.collection.mutable.Stack
import java.awt.Color

class Tile(num: Int, sf: Boolean) {
  val loc = num
  val safe = sf
  
  def check(b: Board, t: TokenLibrary): Stack[Token] = {
    var Tokenlist = new Stack[Token]
    for (x <- t.primary){ if (x.locationread == loc){Tokenlist.push(x)}}
    Tokenlist
  }
  
  def returnstring(b: Board, t:TokenLibrary): String = {
    var x = " "
    var y = check(b,t)
    for (item <- y){
      x+= (item.tokenID+1) + " "
    }
    x
  }
  
  def findColor(b: Board, t: TokenLibrary): Color = {
    var y = check(b,t)
    if(y.isEmpty){Color.gray}
    else{
      var x = y(0)
      if (x.owner.order == 1){ Color.blue.brighter }
      else if (x.owner.order == 2){ Color.red }
      else if (x.owner.order == 3){ Color.green }
      else if (x.owner.order == 4){ Color.yellow }
      else { Color.orange}
    }
      
  }
}