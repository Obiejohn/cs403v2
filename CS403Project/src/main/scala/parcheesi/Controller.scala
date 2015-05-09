package parcheesi

class Controller(a:View,b:Model) {
  var m = b
  val v = a
  var movecount = 0
  var P1Win = 0
  var P2Win = 0
  var P3Win = 0
  var P4Win = 0
  
  def getCurrent():String = {m.current.name}
  def getRolls():(Int,Int) = {m.current.rolls}
  
  def AImod(playint: Int, AIint: Int){
    m.playerList(playint).AIType = AIint
    v.AIupdate
  }
  var t = ""
  def doMove(){
    movecount += 1
    t = ""
    t += ("Player " + m.current.name + "'s turn:" + "\n")

    var a = positionsGet()
    m.doMove
    t += ("Rolls: " + m.current.rolls._1 + " and " + m.current.rolls._2 + "\n")
    var b = positionsGet()
    t += showdifference(a,b)
    v.bodyt.text = t
    m.advanceOrder
    v.boardupdate
    v.homeupdate
    v.jailupdate
    v.winupdate
    v.AIupdate
    }
  
  def doMove2(){
    movecount += 1
    t += ("Player " + m.current.name + "'s turn:" + "\n")
    var a = positionsGet()
    m.doMove
    t += ("Rolls: " + m.current.rolls._1 + " and " + m.current.rolls._2 + "\n")
    var b = positionsGet()
    t += showdifference(a,b)
    v.bodyt.text = t
    m.advanceOrder
    v.AIupdate
    v.boardupdate
    v.homeupdate
    v.jailupdate
    v.winupdate
    t += ("\n")
  }
  
  def doTurn(){
    t = ""
    doMove2
    doMove2
    doMove2
    doMove2
    }
  
  def doTurn2(){
    doMove2
    doMove2
    doMove2
    doMove2
    }
  
  def doGame(){
    t = ""
    while(m.gameover == false){
      m.checkforwinner
      doTurn
    }
    if(m.gameover == true){
      
      if (m.HB.primary(0) == 4){ t+= ("Player 1 has won the Game!")
        P1Win += 1
        }
      else if (m.HB.primary(1) == 4){ t+= ("Player 2 has won the Game!")
        P2Win += 1
        }
      else if (m.HB.primary(2) == 4){ t+= ("Player 3 has won the Game!")
        P3Win += 1
        }
      else if (m.HB.primary(3) == 4){ t+= ("Player 4 has won the Game!")
        P4Win += 1
        }
    }
    v.winupdate
  }
  
  def reset(){
    m = new Model
    v.boardupdate
    v.homeupdate
    v.jailupdate
    v.AIupdate
    v.winupdate
    v.bodyt.text = ""
    }
  
  def advanceOrder(){ m.advanceOrder}
  
  def display(): String = {m.TLib.display}
  
  def positionsGet(): Array[Int] = {
    var x = Array[Int](0,0,0,0)
    var offset = (m.current.order-1) * 4
    x(0) = m.TLib.primary(offset).location
    x(1) = m.TLib.primary(offset+1).location
    x(2) = m.TLib.primary(offset+2).location
    x(3) = m.TLib.primary(offset+3).location
    x
  }
  def showdifference(a: Array[Int], b: Array[Int]): String = {
    var maintext = ""
    var offset = (m.current.order-1) * 4
    if((a(0) != -1)&&(b(0) != -2)&&(b(0) != -1)){
      var c = b(0)-a(0)
      if (c < 0){ c += 80 }
      maintext +=("Token " + (offset+1) + " moved " + c + " tiles.")
      maintext +=("\n")
    }
    else if((a(0) == -1)&&(b(0)== -1)){
      maintext +=("Token " + (offset+1) + " is still in jail.")
      maintext +=("\n")
    }
    else if(b(0) == -2){
      maintext +=("Token " + (offset+1) + " is home!")
      maintext +=("\n")
    }
    else if(a(0) == -1){
      maintext +=("Token " + (offset+1) + " moved " + (b(0)-m.current.start) + " tiles after getting out of jail.")
      maintext +=("\n")
    }
    
    if((a(1) != -1)&&(b(1) != -2)&&(b(1) != -1)){
      var c = b(1)-a(1)
      if (c < 0){ c += 80 }
      maintext +=("Token " + (offset+2) + " moved " + c + " tiles.")
      maintext +=("\n")
    }
    else if((a(1) == -1)&&(b(1)== -1)){
      maintext +=("Token " + (offset+2) + " is still in jail.")
      maintext +=("\n")
    }
    else if(b(1) == -2){
      maintext +=("Token " + (offset+2) + " is home!")
      maintext +=("\n")
    }
    else if(a(1) == -1){
      maintext +=("Token " + (offset+2) + " moved " + (b(1)-m.current.start) + " tiles after getting out of jail.")
      maintext +=("\n")
    }
    
    if((a(2) != -1)&&(b(2) != -2)&&(b(2) != -1)){
      var c = b(2)-a(2)
      if (c < 0){ c += 80 }
      maintext +=("Token " + (offset+3) + " moved " + c + " tiles.")
      maintext +=("\n")
    }
    else if((a(2) == -1)&&(b(2)== -1)){
      maintext +=("Token " + (offset+3) + " is still in jail.")
      maintext +=("\n")
    }
    else if(b(2) == -2){
      maintext +=("Token " + (offset+3) + " is home!")
      maintext +=("\n")
    }
    else if(a(2) == -1){
      maintext +=("Token " + (offset+3) + " moved " + (b(2)-m.current.start) + " tiles after getting out of jail.")
      maintext +=("\n")
    }
    
    if((a(3) != -1)&&(b(3) != -2)&&(b(3) != -1)){
      var c = b(3)-a(3)
      if (c < 0){ c += 80 }
      maintext +=("Token " + (offset+4) + " moved " + c + " tiles.")
      maintext +=("\n")
    }
    else if((a(3) == -1)&&(b(3)== -1)){
      maintext +=("Token " + (offset+4) + " is still in jail.")
      maintext +=("\n")
    }
    else if(b(3) == -2){
      maintext +=("Token " + (offset+4) + " is home!")
      maintext +=("\n")
    }
    else if(a(3) == -1){
      maintext +=("Token " + (offset+4) + " moved " + (b(3)-m.current.start) + " tiles after getting out of jail.")
      maintext +=("\n")
    }
    maintext
  }
  
}