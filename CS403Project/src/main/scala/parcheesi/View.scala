package parcheesi

import scala.swing._
import java.awt.Color
import java.awt.geom._

class View {
     var c: Controller = null
     var initialized = false
     val btnlen = new Dimension(120,40) 
     val ButtonMove = new Button(Action("Play Move") (c.doMove))
     ButtonMove.preferredSize = btnlen
     ButtonMove.minimumSize = btnlen
     ButtonMove.maximumSize = btnlen
     val ButtonTurn = new Button(Action("Play Turn") (c.doTurn))
     ButtonTurn.preferredSize = btnlen
     ButtonTurn.minimumSize = btnlen
     ButtonTurn.maximumSize = btnlen
     val ButtonGame = new Button(Action("Play Game") (c.doGame))
     ButtonGame.preferredSize = btnlen
     ButtonGame.minimumSize = btnlen
     ButtonGame.maximumSize = btnlen
     val ButtonReset = new Button(Action("Reset Game") (c.reset))
     ButtonReset.preferredSize = btnlen
     ButtonReset.minimumSize = btnlen
     ButtonReset.maximumSize = btnlen
     val P1AI0 = new Button(Action("Moderate")(c.AImod(0,0)))
     val P1AI1 = new Button(Action("Aggressive")(c.AImod(0,1)))
     val P1AI2 = new Button(Action("Passive")(c.AImod(0,2)))
     val P1AI3 = new Button(Action("Random")(c.AImod(0,3)))
     val P2AI0 = new Button(Action("Moderate")(c.AImod(1,0)))
     val P2AI1 = new Button(Action("Aggressive")(c.AImod(1,1)))
     val P2AI2 = new Button(Action("Passive")(c.AImod(1,2)))
     val P2AI3 = new Button(Action("Random")(c.AImod(1,3)))
     val P3AI0 = new Button(Action("Moderate")(c.AImod(2,0)))
     val P3AI1 = new Button(Action("Aggressive")(c.AImod(2,1)))
     val P3AI2 = new Button(Action("Passive")(c.AImod(2,2)))
     val P3AI3 = new Button(Action("Random")(c.AImod(2,3)))
     val P4AI0 = new Button(Action("Moderate")(c.AImod(3,0)))
     val P4AI1 = new Button(Action("Aggressive")(c.AImod(3,1)))
     val P4AI2 = new Button(Action("Passive")(c.AImod(3,2)))
     val P4AI3 = new Button(Action("Random")(c.AImod(3,3)))
     P1AI0.minimumSize = btnlen
     P1AI1.minimumSize = btnlen
     P1AI2.minimumSize = btnlen
     P1AI3.minimumSize = btnlen
     P2AI0.minimumSize = btnlen
     P2AI1.minimumSize = btnlen
     P2AI2.minimumSize = btnlen
     P2AI3.minimumSize = btnlen
     P3AI0.minimumSize = btnlen
     P3AI1.minimumSize = btnlen
     P3AI2.minimumSize = btnlen
     P3AI3.minimumSize = btnlen
     P4AI0.minimumSize = btnlen
     P4AI1.minimumSize = btnlen
     P4AI2.minimumSize = btnlen
     P4AI3.minimumSize = btnlen
     P1AI0.maximumSize = btnlen
     P1AI1.maximumSize = btnlen
     P1AI2.maximumSize = btnlen
     P1AI3.maximumSize = btnlen
     P2AI0.maximumSize = btnlen
     P2AI1.maximumSize = btnlen
     P2AI2.maximumSize = btnlen
     P2AI3.maximumSize = btnlen
     P3AI0.maximumSize = btnlen
     P3AI1.maximumSize = btnlen
     P3AI2.maximumSize = btnlen
     P3AI3.maximumSize = btnlen
     P4AI0.maximumSize = btnlen
     P4AI1.maximumSize = btnlen
     P4AI2.maximumSize = btnlen
     P4AI3.maximumSize = btnlen
     var P1AIB = new ButtonGroup(P1AI0,P1AI1,P1AI2,P1AI3)
     var P2AIB = new ButtonGroup(P2AI0,P2AI1,P2AI2,P2AI3)
     var P3AIB = new ButtonGroup(P3AI0,P3AI1,P3AI2,P3AI3)
     var P4AIB = new ButtonGroup(P4AI0,P4AI1,P4AI2,P4AI3)
     var P1AI = new Menu("Player 1 AI"){
       //contents += new RadioMenuItem(Action("AI"){})
       contents += P1AI1
       contents += P1AI2
       contents += P1AI3
     }
  
     var home1 = new TextArea("Player 1 Home: 0 \n")
     var home2 = new TextArea("Player 2 Home: 0 \n")
     var home3 = new TextArea("Player 3 Home: 0 \n")
     var home4 = new TextArea("Player 4 Home: 0 \n")
     
     var jail1 = new TextArea("Player 1 Jail: 3 \n")
     var jail2 = new TextArea("Player 2 Jail: 3 \n")
     var jail3 = new TextArea("Player 3 Jail: 3 \n")
     var jail4 = new TextArea("Player 4 Jail: 3 \n")
     
     var AI1 = new TextArea("Player 1 AI: Moderate \n")
     var AI2 = new TextArea("Player 2 AI: Moderate \n")
     var AI3 = new TextArea("Player 3 AI: Moderate \n")
     var AI4 = new TextArea("Player 4 AI: Moderate \n")
     
     var Win1 = new TextArea("Player 1 Wins: 0 \n")
     var Win2 = new TextArea("Player 2 Wins: 0 \n")
     var Win3 = new TextArea("Player 3 Wins: 0 \n")
     var Win4 = new TextArea("Player 4 Wins: 0 \n")
  
     var bodyt = new TextArea("")
     val body = new BoxPanel(Orientation.Vertical){contents += bodyt}
  
  
     val statboard = new BoxPanel(Orientation.Vertical) {
     contents += home1
     contents += home2
     contents += home3
     contents += home4
     }
     statboard.preferredSize = new Dimension(300,300)
     val statboard2 = new BoxPanel(Orientation.Vertical){
     contents += jail1
     contents += jail2
     contents += jail3
     contents += jail4
     }
     statboard2.preferredSize = new Dimension(300,300)
     val statboard3 = new BoxPanel(Orientation.Vertical){
     contents += AI1
     contents += AI2
     contents += AI3
     contents += AI4
     }
     statboard3.preferredSize = new Dimension(300,300)
     val statboard4 = new BoxPanel(Orientation.Vertical){
     contents += Win1
     contents += Win2
     contents += Win3
     contents += Win4
     }
     statboard4.preferredSize = new Dimension(300,300)
     def homeupdate(){
       c.m.HB.update
       if (c.m.HB.primary(0) < 4){
         home1.text = (" Player 1 Home: " + c.m.HB.primary(0) + "\n")
       }
       else if (c.m.HB.primary(0) == 4){
         home1.text = (" Player 1 Home: " + c.m.HB.primary(0) + "\n" + "Player 1 has won the game!")
       }
       if (c.m.HB.primary(1) < 4){
         home2.text = (" Player 2 Home: " + c.m.HB.primary(1) + "\n")
       }
       else if (c.m.HB.primary(1) == 4){
         home2.text = (" Player 2 Home: " + c.m.HB.primary(1) + "\n" + "Player 2 has won the game!")
       }
       if (c.m.HB.primary(2) < 4){
         home3.text = (" Player 3 Home: " + c.m.HB.primary(2) + "\n")
       }
       else if (c.m.HB.primary(2) == 4){
         home3.text = (" Player 3 Home: " + c.m.HB.primary(2) + "\n" + "Player 3 has won the game!")
       }
       if (c.m.HB.primary(3) < 4){
         home4.text = (" Player 4 Home: " + c.m.HB.primary(3) + "\n")
       }
       else if (c.m.HB.primary(3) == 4){
         home4.text = (" Player 4 Home: " + c.m.HB.primary(3) + "\n" + "Player 4 has won the game!")
       }
     }
     
     def jailupdate(){
       c.m.JB.update
       jail1.text = (" Player 1 Jail: " + c.m.JB.primary(0) + "\n")
       jail2.text = (" Player 2 Jail: " + c.m.JB.primary(1) + "\n")
       jail3.text = (" Player 3 Jail: " + c.m.JB.primary(2) + "\n")
       jail4.text = (" Player 4 Jail: " + c.m.JB.primary(3) + "\n")
     }
     
     def winupdate(){
       Win1.text = (" Player 1 Wins: " + c.P1Win + "\n")
       Win2.text = (" Player 2 Wins: " + c.P2Win + "\n")
       Win3.text = (" Player 3 Wins: " + c.P3Win + "\n")
       Win4.text = (" Player 4 Wins: " + c.P4Win + "\n")
     }
     
     def AIupdate(){
       if (c.m.playerList(0).AIType == 0){AI1.text = ("Player 1 AI: Moderate \n")}
       else if (c.m.playerList(0).AIType == 1){AI1.text = ("Player 1 AI: Aggressive \n")}
       else if (c.m.playerList(0).AIType == 2){AI1.text = ("Player 1 AI: Passive \n")}
       else if (c.m.playerList(0).AIType == 3){AI1.text = ("Player 1 AI: Random \n")}
       if (c.m.playerList(1).AIType == 0){AI2.text = ("Player 2 AI: Moderate \n")}
       else if (c.m.playerList(1).AIType == 1){AI2.text = ("Player 2 AI: Aggressive \n")}
       else if (c.m.playerList(1).AIType == 2){AI2.text = ("Player 2 AI: Passive \n")}
       else if (c.m.playerList(1).AIType == 3){AI2.text = ("Player 2 AI: Random \n")}
       if (c.m.playerList(2).AIType == 0){AI3.text = ("Player 3 AI: Moderate \n")}
       else if (c.m.playerList(2).AIType == 1){AI3.text = ("Player 3 AI: Aggressive \n")}
       else if (c.m.playerList(2).AIType == 2){AI3.text = ("Player 3 AI: Passive \n")}
       else if (c.m.playerList(2).AIType == 3){AI3.text = ("Player 3 AI: Random \n")}
       if (c.m.playerList(3).AIType == 0){AI4.text = ("Player 4 AI: Moderate \n")}
       else if (c.m.playerList(3).AIType == 1){AI4.text = ("Player 4 AI: Aggressive \n")}
       else if (c.m.playerList(3).AIType == 2){AI4.text = ("Player 4 AI: Passive \n")}
       else if (c.m.playerList(3).AIType == 3){AI4.text = ("Player 4 AI: Random \n")}
     }
     
     var tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10, tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20, tile21, tile22, tile23, tile24, tile25, tile26, tile27, tile28, tile29 = new Button(" ")
     var tile30, tile31, tile32, tile33, tile34, tile35, tile36, tile37, tile38, tile39 = new Button(" ")
     var tile40, tile41, tile42, tile43, tile44, tile45, tile46, tile47, tile48, tile49 = new Button(" ")
     var tile50, tile51, tile52, tile53, tile54, tile55, tile56, tile57, tile58, tile59 = new Button(" ")
     var tile60, tile61, tile62, tile63, tile64, tile65, tile66, tile67, tile68, tile69 = new Button(" ")
     var tile70, tile71, tile72, tile73, tile74, tile75, tile76, tile77, tile78, tile79 = new Button(" ")
     
     
     val BoardArray = Array(tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10, tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20, tile21, tile22, tile23, tile24, tile25, tile26, tile27, tile28, tile29, tile30, tile31, tile32, tile33, tile34, tile35, tile36, tile37, tile38, tile39, tile40, tile41, tile42, tile43, tile44, tile45, tile46, tile47, tile48, tile49, tile50, tile51, tile52, tile53, tile54, tile55, tile56, tile57, tile58, tile59, tile60, tile61, tile62, tile63, tile64, tile65, tile66, tile67, tile68, tile69, tile70, tile71, tile72, tile73, tile74, tile75, tile76, tile77, tile78, tile79)
     
     val textdisp = new TextArea
     
     val BoardN = new BoxPanel(Orientation.Horizontal) {
       for(item <- (0 to 19)){
         contents += BoardArray(item)
       }
     }
     val BoardE = new BoxPanel(Orientation.Vertical) {
       for(item <- (20 to 39)){
         contents += BoardArray(item)
       }
     }
     val BoardS = new BoxPanel(Orientation.Horizontal) {
       for(item <- (40 to 59).reverse){
         contents += BoardArray(item)
       }
     }
     val BoardW = new BoxPanel(Orientation.Vertical) {
       for(item <- (60 to 79).reverse){
         contents += BoardArray(item)
       }
     }
     
     
     def boardupdate(){
       for (ind <- ( 0 until c.m.mainBoard.primary.length)){
         BoardArray(ind).text = c.m.mainBoard.primary(ind).returnstring(c.m.mainBoard,c.m.TLib)
         BoardArray(ind).background = c.m.mainBoard.primary(ind).findColor(c.m.mainBoard,c.m.TLib)
       }
     }
     
     val frame = new MainFrame {
     title = "Parcheesi"
     background = Color.black
     contents = new BoxPanel(Orientation.Horizontal) {
       val mdm = new Dimension(120,800)
       val AIset = new Dimension(120,140)
       var menuorienter = new BorderPanel(){
       var menupane =  new BoxPanel(Orientation.Vertical) {
         contents += ButtonMove
         contents += ButtonTurn
         contents += ButtonGame
         contents += ButtonReset
         contents += new BoxPanel(Orientation.Vertical) {
           this.preferredSize = AIset
           this.minimumSize = AIset
           this.maximumSize = AIset
           var P1AIL = new Label("Player 1 AI")
           contents += P1AIL
           P1AIL.background = Color.blue.brighter
           contents ++= P1AIB.buttons
           background = Color.blue.brighter
         }
         contents += new BoxPanel(Orientation.Vertical) {
           this.preferredSize = AIset
           this.minimumSize = AIset
           this.maximumSize = AIset
           var P2AIL = new Label("Player 2 AI")
           contents += P2AIL
           P2AIL.background = Color.red
           contents ++= P2AIB.buttons
           background = Color.red
         }
         contents += new BoxPanel(Orientation.Vertical) {
           this.preferredSize = AIset
           this.minimumSize = AIset
           this.maximumSize = AIset
           var P3AIL = new Label("Player 3 AI")
           contents += P3AIL
           P3AIL.background = Color.green
           contents ++= P3AIB.buttons
           background = Color.green
         }
         contents += new BoxPanel(Orientation.Vertical) {
           this.preferredSize = AIset
           this.minimumSize = AIset
           this.maximumSize = AIset
           var P4AIL = new Label("Player 4 AI")
           contents += P4AIL
           P4AIL.background = Color.yellow
           contents ++= P4AIB.buttons
           background = Color.yellow
         }
         background = Color.gray
       }
       layout += menupane -> BorderPanel.Position.North
       menupane.preferredSize = mdm
       menupane.minimumSize = mdm
       menupane.maximumSize = mdm
       }
       contents += menuorienter
       menuorienter.preferredSize = mdm
       menuorienter.minimumSize = mdm
       menuorienter.maximumSize = mdm
       
       var mainarea = new BoxPanel(Orientation.Vertical) {  
         var statpanel = new BoxPanel(Orientation.Horizontal){
         background = Color.white
         contents += statboard
         contents += statboard2
         contents += statboard3
         contents += statboard4
         }
         var playArea = new BorderPanel() {
           layout += BoardN -> BorderPanel.Position.North
           layout += BoardE -> BorderPanel.Position.East
           layout += BoardS -> BorderPanel.Position.South
           layout += BoardW -> BorderPanel.Position.West
           layout += bodyt -> BorderPanel.Position.Center
         
         }
         contents += playArea
         contents += statpanel
         contents += new BoxPanel(Orientation.Horizontal){ background = Color.black }
       }
       contents += mainarea
       val maindim = new Dimension(800,700)
       mainarea.preferredSize = maindim
       mainarea.minimumSize = maindim
       mainarea.maximumSize = maindim
       
     }
   }
     
   def init(con:Controller){
    c = con
    initialized = true
    var update = false
    //frame.menuBar = createMenu
    var fsize = new Dimension(1050,800)
    frame.size = fsize
    frame.preferredSize = fsize
    frame.minimumSize = fsize
    frame.maximumSize = fsize
    frame.visible = true
    boardupdate
    homeupdate
    jailupdate
    
  }  
     
     
}