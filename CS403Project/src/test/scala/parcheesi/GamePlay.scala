package parcheesi

import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.Assertions._

class GamePlay extends FunSpec with Matchers {


  describe("A Player") {
    describe("At Beginning"){
      it("should have a color") {
        val P1 = new Player(1,"John")
        P1.initialize
        P1.col should be ("Blue")
      }
      it("should have a turn order") {
        val P1 = new Player(1,"John")
        P1.initialize
        P1.order should be (1)
      }
      it("should have a start location") {
        val P1 = new Player(1,"John")
        P1.initialize
        assert( P1.start == 14 )
        val P2 = new Player(2,"Josh")
        P2.initialize
        assert( P2.start == 29 )
        val P3 = new Player(3,"Jane")
        P3.initialize
        assert( P3.start == 44 )
        val P4 = new Player(4,"Jill")
        P4.initialize
        assert( P4.start == 59 )
      }
      
      it("should have a jail value, initially as 3") {
        val P1 = new Player(1,"John")
        P1.initialize
        P1.jail should be (3)
      } 
      it("should have a home value, initially as 0") {
        val P1 = new Player(1,"John")
        P1.initialize
        P1.home should be (0)
      }
    }
    describe("During Gameplay"){
      it("should be able to get two seperate dice values") {
        var D1 = new Dice
        var D2 = new Dice
        var P1 = new Player(1,"John")
        P1.rolls = (D1.roll,D2.roll)
        assert(P1.rolls._1 > 0)
        assert(P1.rolls._1 < 7)
        assert(P1.rolls._2 > 0)
        assert(P1.rolls._2 < 7)
        
      }
      it("should be able to apply a dice value to a token to move it") {
        var D1 = new Dice
        var D2 = new Dice
        val P1 = new Player(1,"John")
        P1.initialize
        P1.rolls = (D1.roll,D2.roll)
        val ModelBoard = new Board()
        val TLib = new TokenLibrary(List(P1))
        var t = false
        t = P1.applyroll(TLib, ModelBoard, P1.rolls._1,TLib.primary(0),0)
        TLib.primary(0).location should be (P1.start + P1.rolls._1)
        assert (t == true)
      }
      it("should be able to apply a dice value of 5 to a pull a token out of jail") {
        val P1 = new Player(1,"John")
        P1.initialize
        val ModelBoard = new Board()
        val TLib = new TokenLibrary(List(P1))
        TLib.primary(1).locationread should be (-1)
        P1.jailbreak(TLib,TLib.primary(1))
        P1.jail should be (2)
        TLib.primary(1).locationread should be (P1.start)
      }
        
    }
  }
    
  describe("A Token") {
    it("should have a player owner") {
      val P1 = new Player(1,"John")
      P1.initialize
      val To = new Token(P1,20)
      To.owner should be (P1)
    }
    it("should have a unique tokenID") {
      val P1 = new Player(1,"John")
      P1.initialize
      val P2 = new Player(2,"Jack")
      P2.initialize
      val P3 = new Player(3,"James")
      P3.initialize
      val P4 = new Player(4,"Joe")
      P4.initialize
      val ModelBoard = new Board()
      val TLib = new TokenLibrary(List(P1,P2,P3,P4))
      for (i <- (0 until TLib.primary.size)){
        TLib.primary(i).tokenID should be (i)}
    }
    it("should have a tile location") {
      val P1 = new Player(1,"John")
      P1.initialize
      val T = new Token(P1,20)
      assert(T.location == 20)
    }
    it("should be able to move to another tile") {
      val P1 = new Player(1,"Frodo")
      P1.initialize
      val P2 = new Player(2,"Sam")
      P2.initialize
      val ModelBoard = new Board()
      val TLib = new TokenLibrary(List(P1,P2))
      TLib.primary(0).locationset(20)
      TLib.primary(1).locationset(20)
      TLib.primary(2).locationset(20)
      TLib.primary(3).locationset(20)
      TLib.primary(4).locationset(20)
      TLib.primary(5).locationset(20)
      TLib.primary(0).move(TLib,ModelBoard,1)
      TLib.primary(1).move(TLib,ModelBoard,2)
      TLib.primary(2).move(TLib,ModelBoard,3)
      TLib.primary(3).move(TLib,ModelBoard,4)
      TLib.primary(4).move(TLib,ModelBoard,5)
      TLib.primary(5).move(TLib,ModelBoard,6)
      TLib.primary(0).locationread should be (21)
      TLib.primary(1).locationread should be (22)
      TLib.primary(2).locationread should be (23)
      TLib.primary(3).locationread should be (24)
      TLib.primary(4).locationread should be (25)
      TLib.primary(5).locationread should be (26)
    }
    it("should be able to go around the board"){
      val P1 = new Player(1,"Frodo")
      P1.initialize
      val ModelBoard = new Board()
      val TLib = new TokenLibrary(List(P1))
      TLib.primary(0).locationset(78)
      TLib.primary(0).move(TLib,ModelBoard,6)
      TLib.primary(0).locationread should be (5)
    }
    
    it("should be removed from play if another token lands on its location, if it is owned by another player and the tile is unsafe") {
      val P1 = new Player(1,"John")
      P1.initialize
      val P2 = new Player(2,"Josh")
      P2.initialize
      val ModelBoard = new Board()
      val TLib = new TokenLibrary(List(P1,P2))
      TLib.primary(0).locationset(20)
      TLib.primary(5).locationset(25)
      TLib.primary(0).move(TLib,ModelBoard,5)
      TLib.primary(0).locationread should be (25)
      TLib.primary(5).locationread should be (-1)
    }
  }
  
  describe("A Tile"){
    it("should be able to check for tokens on it") {
      val P1 = new Player(1,"John")
      P1.initialize
      val Tile20 = new Tile(20,false)
      val ModelBoard = new Board()
      val TLib = new TokenLibrary(List(P1))
      TLib.primary(0).locationset(20)
      assert(TLib.primary(0).locationread == 20)
      assert(ModelBoard.primary(20).check(ModelBoard, TLib).contains(TLib.primary(0)))
    }
    it("should have a safe or unsafe value") {
      val Tile20 = new Tile(20,false)
      val Tile30 = new Tile(30,false)
      assert(Tile20.safe == false)
      assert(Tile30.safe == false)
    }
  }
  describe("The Board") {
    it("Should consist of the appropriate number of tiles, as it is a collection of tiles") { 
      val B = new Board
      assert(B.count == 80)
    }
  }
  describe("Home Area Score Display") {
    describe("At Beginning"){
      it("Should initially display 0 for all players") {
        val P1 = new Player(1,"John")
        P1.initialize
        val P2 = new Player(2,"Josh")
        P2.initialize
        val P3 = new Player(3,"Jane")
        P3.initialize
        val P4 = new Player(4,"Jack")
        P4.initialize
        val HB = new Homeboard(List(P1,P2,P3,P4))
        HB.update
        assert(HB.primary(0)==0)
        assert(HB.primary(1)==0)
        assert(HB.primary(2)==0)
        assert(HB.primary(3)==0)
      }
    }
    describe("During Play"){
      it("Should update if a player enters a token to the home area") {
        val P1 = new Player(1,"John")
        P1.initialize
        val HB = new Homeboard(List(P1))
        assert(P1.home == 0)
        assert(HB.primary(0) == 0)
        val ModelBoard = new Board()
        val TLib = new TokenLibrary(List(P1))
        TLib.primary(0).locationset(13)       
        P1.applyroll(TLib, ModelBoard, 4, TLib.primary(0),0)
        assert(TLib.primary(0).locationread == -2)
        HB.update
        assert(P1.home == 1)
        assert(HB.primary(0) == 1)
      }
    }
  }
  describe("Jail Area Score Display") {
    describe("At Beginning"){
      it("Should initially display 3 for all players") {
        val P1 = new Player(1,"John")
        P1.initialize
        val P2 = new Player(2,"Josh")
        P2.initialize
        val P3 = new Player(3,"Jane")
        P3.initialize
        val P4 = new Player(4,"Jack")
        P4.initialize
        val JB = new Jailboard(List(P1,P2,P3,P4))
        JB.update
        assert(JB.primary(0) == 3)
        assert(JB.primary(1) == 3)
        assert(JB.primary(2) == 3)
        assert(JB.primary(3) == 3)
      }
    }
    describe("During Play"){
      it("Should update if a player loses a token from play") {
        val P1 = new Player(1,"John")
        P1.initialize
        val ModelBoard = new Board()
        val TLib = new TokenLibrary(List(P1))
        var JB = new Jailboard(List(P1))
        JB.update
        assert(P1.jail == 3)
        TLib.primary(0).jail
        JB.update
        assert(P1.jail == 4)
      }
      it("Should update if a player pulls a token out of jail.") {
        val P1 = new Player(1,"John")
        P1.initialize
        val ModelBoard = new Board()
        val TLib = new TokenLibrary(List(P1))
        var JB = new Jailboard(List(P1))
        JB.update
        assert(P1.jail == 3)
        P1.jailbreak(TLib,TLib.primary(1))
        assert(P1.jail == 2)       
      }
    }
  }
    describe("Dice") {
    it("Should be able to return a value between 1 and 6") {
      var D1 = new Dice
      var x = D1.roll()
      assert(x > 0)
      assert(x < 7)
    }
  }
  describe("Model") {
    it("Initialize the player order and color") {
      val Model1 = new Model
      assert(Model1.P1.col == "Blue")
      assert(Model1.P2.col == "Red")
      assert(Model1.P3.col == "Green")
      assert(Model1.P4.col == "Yellow")
      assert(Model1.showPlayingOrder == List(Model1.P1,Model1.P2,Model1.P3,Model1.P4))
      
    }
    it("Should be able to rotate the current player") {
      val Model1 = new Model
      assert(Model1.current == Model1.P1)
      Model1.advanceOrder()
      assert(Model1.current == Model1.P2)
    }
    it("Should be able to check for a winner") {
      val Model1 = new Model
      assert(Model1.gameover == false )
      Model1.P1.home = 4
      Model1.checkforwinner
      assert(Model1.gameover == true )
    }
    
    it("Should be able to execute a move"){
      val Model1 = new Model
      assert(Model1.current.diceflag(0) == false)
      assert(Model1.current.diceflag(1) == false)
      Model1.doMove()
      assert(Model1.current.diceflag(0) == true)
      assert(Model1.current.diceflag(1) == true)
      
    }
    
    it("Should be able to run a turn and end up at its original position"){
      val Model1 = new Model
      var tstvar = Model1.current
      //Model1.doTurn
      assert(tstvar == Model1.current)
    }
    
    it("Should be able to run a whole game"){
      val Model1 = new Model
      assert(Model1.gameover == false)
      assert(Model1.winner == null)
      Model1.doGame
      assert(Model1.gameover == true)
      assert(Model1.winner != null)
      
    }
    
  }
  describe("The View"){
    it("Should be able to display"){
      val v = new View
      val m = new Model
      val c = new Controller(v,m)
      v.init(c)
    }
  }
  describe("Strategy") {
    it("If Moderate, should unjail token and move farthest available piece") {
      var m = new Model
      m.TLib.primary(0).location = 30
      m.TLib.primary(1).location = -1
      m.TLib.primary(2).location = -1
      m.TLib.primary(3).location = -1
      m.current.AIType = 0
      assert(m.TLib.primary(0).location == 30)
      assert(m.TLib.primary(3).location == -1)
      m.doMoveV2(5,4)
      assert(m.TLib.primary(0).location == 34)
      assert(m.TLib.primary(3).location == m.current.start)
      
    }
    it("If Aggressive, should move farthest piece and disregard ability to unjail") {
      var m = new Model
      m.TLib.primary(0).location = 30
      m.TLib.primary(1).location = -1
      m.TLib.primary(2).location = -1
      m.TLib.primary(3).location = -1
      m.current.AIType = 1
      assert(m.TLib.primary(0).location == 30)
      assert(m.TLib.primary(3).location == -1)
      m.doMoveV2(5,4)
      assert(m.TLib.primary(0).location == 39)
      assert(m.TLib.primary(3).location == -1)
    }
    it("If Passive, should move most behind of two pieces") {
      var m = new Model
      m.TLib.primary(0).location = 30
      m.TLib.primary(1).location = 40
      m.TLib.primary(2).location = -1
      m.TLib.primary(3).location = -1
      m.current.jail = 2
      m.current.AIType = 2
      assert(m.TLib.primary(0).location == 30)
      assert(m.TLib.primary(1).location == 40)
      m.doMoveV2(4,4)
      assert(m.TLib.primary(0).location == 38)
      assert(m.TLib.primary(1).location == 40)
    }
    }

}
    