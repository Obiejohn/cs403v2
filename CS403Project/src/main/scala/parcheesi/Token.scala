package parcheesi

class Token(play: Player, startloc: Int) {
  var tokenID = 0
  var location = startloc
  val ownernum = play.order
  val owner = play
  
  def locationread():Int={location}
  def locationset(a:Int){location=a}
  
  def jail(){
    locationset(-1)
    play.jail += 1
  }
  
  def move(t: TokenLibrary, brd: Board, dist: Int) {
    var currloc = locationread
    var dest: Int = (currloc + dist)
    if (dest > 79){dest -= 79}
    var target: Token = null
    if(brd.primary(dest).check(brd,t).size == 0){locationset(dest)}
    else{
      var target: Token = brd.primary(dest).check(brd,t)(0)  //Wont need to check other slots if canmove is run first
      if(target.owner == this.owner){this.locationset(dest)}
      else{
        locationset(dest)
        target.jail
      }
    }
    if ((currloc < owner.start)&&(dest >= owner.start)){
      home
    }
  }
  
  def canmove(t: TokenLibrary, brd: Board, dist: Int):Boolean = {
    var dest: Int = (locationread + dist)
    var wraparound: Boolean = false
    if(dest > 79){
      dest -= 79
      wraparound = true
    }
    if (wraparound == false){
      var flag = 0
      for (index <- (locationread+1 to dest) ){
        if (brd.primary(index).check(brd,t).size == 2){flag=1}
      }
      if (flag == 0){ true } else { false }
    }
    else{
      var flag = 0
      if (locationread != 79){
      for (index <- (locationread+1 to 79) ){
        if (brd.primary(index).check(brd,t).size == 2){flag=1}
      }
      }
      for (index
          <- (0 to dest) ){
        if (brd.primary(index).check(brd,t).size == 2){flag=1}
      }
      if (flag == 0){ true } else { false }
    }
    
  }
  
  def home(){
    locationset(-2)
    owner.home +=1
  }

}