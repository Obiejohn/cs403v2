package parcheesi

import scala.util.Random
import scala.collection.mutable.Queue
import scala.collection.mutable.Stack
import scala.math

class Model() {      //instantiate game with "val game = new Main" followed by "game.doGame"
  val mainBoard = new Board
  val D1 = new Dice
  val D2 = new Dice
  val P1 = new Player(1,"Computer_1")
  P1.initialize
  val P2 = new Player(2,"Computer_2")
  P2.initialize
  val P3 = new Player(3,"Computer_3")
  P3.initialize
  val P4 = new Player(4,"Computer_4")
  P4.initialize
  val P5 = new Player(5,"Dummy_Slot")
  P5.initialize
  var winner: Player = null
  val dummytoken = new Token(P5,-5)
  
  val playerList = List(P1,P2,P3,P4)
  var y = 0
  val TLib = new TokenLibrary(playerList)
  val mainboard = new Board
  val HB = new Homeboard(playerList)
  val JB = new Jailboard(playerList)
  var gameover: Boolean = false
  var t: Boolean = false
  
  def showPlayingOrder():List[Player]={playerList}
  
  def checkforwinner(){
    for (ply <- showPlayingOrder){
      if (ply.home == 4){
        gameover = true
        winner = ply
      }
    }
  }
  
  def current():Player = {
    (playerList(y))
  }
  def advanceOrder(){
    if(y!=3){y+=1}
    else{y=0}
  }
  
  def checkStack(tried: Stack[Token]): Int = {
    var tried2 = new Stack[Token]
    for(tokn <- tried){
      if((tokn.owner == current)&&(tokn.tokenID != 17)){tried2.push(tokn)}
    }
    tried2.size
  }
  
  def doMove(){
    current.rolls = (D1.roll,D2.roll)
    current.turnset
    var done: Boolean = false
    var toke: Token = null
    var reversalflag = false
    var AIo = current.AIType
    var AI = AIo
    if (AIo == 3){AI = Random.nextInt(3)}
    var triedtokens = new Stack[Token]
    while (!done){
      if (AI == 0){  //AI movetype
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
    
        
        var targettoken: Token = new Token(current,current.start)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        
        
        
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if(!t){
              triedtokens.push(targettoken)
            }
          }
          
          
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if(!t){
              triedtokens.push(targettoken)

            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          
          if(!t){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          
          if(!t){
            triedtokens.push(targettoken)
            }
          }
         
          
        
          
        else{done = true}  //out of dice
      
      }
      
      else if (AI == 1){
        var targettoken: Token = new Token(current,current.start)
        if (current.diceflag(0)&&current.diceflag(1)){done = true}
        targettoken.tokenID = 17
        var offset = (current.order-1)*4
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if (((tok.location == -1)||(tok.location == -2)||(tok.owner != current))&&(!triedtokens.contains(tok))){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        
        
        if (checkStack(triedtokens) != (4)){  //if all tokens have not been tried yet
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if((!t)&&(!triedtokens.contains(targettoken))){
              triedtokens.push(targettoken)
            }
          
          }
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if((!t)&&(!triedtokens.contains(targettoken))){
              triedtokens.push(targettoken)
                
            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          if((!t)&&(!triedtokens.contains(targettoken))){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          if((!t)&&(!triedtokens.contains(targettoken))){
            triedtokens.push(targettoken)
            }
          
          }
        }
        else if (checkStack(triedtokens) == (4)){  //all tokens have been tried
        if((current.diceflag(0)==false)&&(current.rolls._1 !=5)){current.diceflag(0)=true}
        if((current.diceflag(1)==false)&&(current.rolls._2 !=5)){current.diceflag(0)=true}
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 != 5)&&(current.rolls._2 != 5)){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
        }
        else{done = true}  //out of dice
      
      }
      else if (AI == 2){    //Passive AI
        var targettoken: Token = new Token(current,current.start)
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
    
        if (!reversalflag){
        targettoken = new Token(P5,99999)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))<(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses least ahead piece.
        
        if(targettoken.tokenID == 17){
          reversalflag = true
        }

        
        if (checkStack(triedtokens) == (4)){reversalflag = true} //if impossible to send least forward, alternate back to moderate system temporarily.
        
        }
        if (reversalflag){
        targettoken = new Token(current,current.start)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        }
        
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if((!t)&&(targettoken.tokenID != 17)){
              triedtokens.push(targettoken)
            }
          }
          
          
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if((!t)&&(targettoken.tokenID != 17)){
              triedtokens.push(targettoken)

            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          
          if((!t)&&(targettoken.tokenID != 17)){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          
          if((!t)&&(targettoken.tokenID != 17)){
            triedtokens.push(targettoken)
            }
          }
         
          
        
    
        else{done = true}  //out of dice
      
      }
        
    }
  }
  
  def doTurn(){
    for(i <- (0 until showPlayingOrder.size)){
      doMove()
      advanceOrder
    }
  }

  def doGame(){
    while(gameover == false){
      checkforwinner
      doTurn
    }
  }
  
  def showPlayingArea():String = {
    mainBoard.showPlayingArea(TLib)
  }
  
  def doMoveV2(roll1: Int, roll2: Int){
    current.rolls = (roll1,roll2)
    current.turnset
    var done: Boolean = false
    var toke: Token = null
    var reversalflag = false
    var AIo = current.AIType
    var AI = AIo
    if (AIo == 3){AI = Random.nextInt(3)}
    var triedtokens = new Stack[Token]
    while (!done){
      if (AI == 0){  //AI movetype
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
    
        
        var targettoken: Token = new Token(current,current.start)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        
        
        
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if(!t){
              triedtokens.push(targettoken)
            }
          }
          
          
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if(!t){
              triedtokens.push(targettoken)

            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          
          if(!t){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          
          if(!t){
            triedtokens.push(targettoken)
            }
          }
         
          
        
          
        else{done = true}  //out of dice
      
      }
      
      else if (AI == 1){
        var targettoken: Token = new Token(current,current.start)
        if (current.diceflag(0)&&current.diceflag(1)){done = true}
        targettoken.tokenID = 17
        var offset = (current.order-1)*4
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if (((tok.location == -1)||(tok.location == -2)||(tok.owner != current))&&(!triedtokens.contains(tok))){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        
        
        if (checkStack(triedtokens) != (4)){  //if all tokens have not been tried yet
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if((!t)&&(!triedtokens.contains(targettoken))){
              triedtokens.push(targettoken)
            }
          
          }
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if((!t)&&(!triedtokens.contains(targettoken))){
              triedtokens.push(targettoken)
                
            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          if((!t)&&(!triedtokens.contains(targettoken))){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          if((!t)&&(!triedtokens.contains(targettoken))){
            triedtokens.push(targettoken)
            }
          
          }
        }
        else if (checkStack(triedtokens) == (4)){  //all tokens have been tried
        if((current.diceflag(0)==false)&&(current.rolls._1 !=5)){current.diceflag(0)=true}
        if((current.diceflag(1)==false)&&(current.rolls._2 !=5)){current.diceflag(0)=true}
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 != 5)&&(current.rolls._2 != 5)){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
        }
        else{done = true}  //out of dice
      
      }
      else if (AI == 2){    //Passive AI
        var targettoken: Token = new Token(current,current.start)
        if((current.diceflag(0))&&(current.diceflag(1))){done = true}
        if((current.rolls._1 == 5)&&(current.jail != 0)&&(current.diceflag(0)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(0)=true   
        }
        if((current.rolls._2 == 5)&&(current.jail != 0)&&(current.diceflag(1)==false)){
          for(tokens <- TLib.primary){
              if ((tokens.owner == current)&&(tokens.location == -1)){toke = tokens}
            }
          if(toke!=null){
          current.jailbreak(TLib,toke)}
          current.diceflag(1)=true   
        }
    
        if (!reversalflag){
        targettoken = new Token(P5,99999)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))<(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses least ahead piece.
        
        if(targettoken.tokenID == 17){
          reversalflag = true
        }

        
        if (checkStack(triedtokens) == (4)){reversalflag = true} //if impossible to send least forward, alternate back to moderate system temporarily.
        
        }
        if (reversalflag){
        targettoken = new Token(current,current.start)
        targettoken.tokenID = 17
        for(tok <- TLib.primary){
          var a = tok.location - current.start
          var b = targettoken.location - current.start
          if (a < 0){a += 79}
          if (b < 0){b += 79}
          if ((tok.location == -1)||(tok.location == -2)){triedtokens.push(tok)}
          else if ((tok.owner == current)&&((math.abs(a))>=(math.abs(b)))&&(!triedtokens.contains(tok))){targettoken = tok}
          else if ((a == 79)&&(targettoken.tokenID == 17)&&(checkStack(triedtokens)==4))(targettoken = tok)
        }//chooses furthest ahead piece.
        if(targettoken.tokenID == 17){
          done = true
        }
        }
        
        if ((!current.diceflag(0))&&(!current.diceflag(1))){    //both dice unused
          if (current.rolls._1 > current.rolls._2){  //first die is larger
            t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
            
            if((!t)&&(targettoken.tokenID != 17)){
              triedtokens.push(targettoken)
            }
          }
          
          
          else{//second die is larger, or the rolls are equal.
            t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
            
            if((!t)&&(targettoken.tokenID != 17)){
              triedtokens.push(targettoken)

            }
          }  
        }            
        
        else if(!current.diceflag(0)){   //first die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._1,targettoken,0)
          
          if((!t)&&(targettoken.tokenID != 17)){
            triedtokens.push(targettoken)
          }
        }
        
        else if(!current.diceflag(1)){   //second die unused
          t = current.applyroll(TLib,mainBoard,current.rolls._2,targettoken,1)
          
          if((!t)&&(targettoken.tokenID != 17)){
            triedtokens.push(targettoken)
            }
          }
         
          
        
    
        else{done = true}  //out of dice
      
      }
        
    }
  }
}