package parcheesi

class Player(num: Int, nm: String) {
  var AIType: Int = 0
  var col = ""
  var strt = (((num)*15)-1)
  var jail = 3
  var home = 0
  var activedice = 2
  var rolls = ( -1 , -1 )
  var diceflag = Array(false,false)
  val order = num
  val name = nm
  val token1 = new Token(this, start)
  val token2 = new Token(this, -1)
  val token3 = new Token(this, -1)
  val token4 = new Token(this, -1)
    
  def applyroll(t: TokenLibrary, brd: Board, roll: Int, tok: Token, dieselect: Int): Boolean ={
    var previousdestination = tok.location
    var newdestination = (previousdestination + roll)
    if ((tok.owner == this)&&(activedice != 0)){
      if (diceflag(dieselect)==false){
        if (tok.canmove(t,brd,roll)){
          tok.move(t,brd,roll)
          activedice -=1
          diceflag(dieselect) = true
          true
        }
        else{false}
      }
      else{false}
    }
    else{false}
  }
  
  def start():Int = {strt}
  
  def jailbreak(t: TokenLibrary, tok: Token){
    if ((jail != 0)&&(activedice != 0)){
      for(z <- t.primary){
        if ((z.tokenID == tok.tokenID)&&(z.location == -1)){
          tok.locationset(tok.owner.start)
          tok.owner.jail -=1
          tok.owner.activedice -=1
        }
      }
    }
  }
  
  def initialize(){
    if (num == 1) col = "Blue"
    else if (num == 2) col = "Red"
    else if (num == 3) col = "Green"
    else {col = "Yellow"}
    jail = 3
    home = 0
  }
  def turnset(){
    activedice = 2
    diceflag(0) = false
    diceflag(1) = false

    
  }
}