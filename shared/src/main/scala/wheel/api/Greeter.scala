package wheel.api

import wvlet.airframe.http.*

sealed trait Node

case class Comment ( val text : String) extends Node

case class Item ( val text : String, val children : Option[NodeList] ) extends Node:
    override def toString: String = children match
        case Some( c ) => 
            text +  
            (for
                char <- ("\n" + c)
            yield
                if char == '\n' then
                    "\n "
                else
                    ""+char).flatten.mkString
        case None => text

case class Head ( val children : NodeList ) extends Node:
    override def toString: String =
        
        (for
            char <- ("" + children)
         yield
            if char == '\n' then
                "\n "
            else
                ""+char).flatten.mkString

sealed trait NodeList:
    val list : List[Node]

case class UnorderedList ( val list : List[Node]) extends NodeList:
    override def toString: String = "*" + list.mkString( "\n*" )
case class OrderedList ( val list : List[Node]) extends NodeList:
    override def toString: String = ">" + list.mkString( "\n>" )

@RPC
trait Greeter {
  def greet(name: String): String //= s"Hello, $name!"
  def getTime: String = java.time.Instant.now.toString
  def parseNodeList(text: String): Option[NodeList]
}

object Greeter extends RxRouterProvider {
  override def router: RxRouter = RxRouter.of[Greeter]
}
