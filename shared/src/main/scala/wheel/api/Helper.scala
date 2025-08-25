
package wheel.api

object Helper:

  def digest_leading_whitespace ( text : String ) : Array[(Int, String)] =
      for
          line <-
            for
              line <-
                text.split("\n") match
                  case array : Array[String | Null] => array
                  case _ => Array.empty[String | Null]
            yield
              line match
                case s : String => s
                case _ => ""
          if line != ""
      yield
          var i = 0
          while line(i) == ' ' do i += 1
          ( i , line.splitAt(i)._2)            
  def expand_at ( in : Array[(Int, String)] ) : Option[Int] =
      var out : Option[Int] = None
      for
          ((_,line),i) <- in.zipWithIndex
          if line.length() >= 2
      do
          val test = line.splitAt(2)._1
          if test == "*>" then
              out = Some(i)
          if test == ">*" then
              out = Some(i)
      out
  def expand_once ( in : Array[(Int, String)] ) : Option[Array[(Int, String)]] =
      for
          index <- expand_at( in )
      yield
          val ( left, right ) = in.splitAt( index + 1 )
          val ( num_spaces, line ) = left( left.length - 1 )
          val ( bullet, rest ) = line.splitAt(1)
          left.splitAt( left.length - 1 )._1 ++ Array( ( num_spaces, bullet ), ( num_spaces + 1, rest ) ) ++ right
  def expand ( in : Array[(Int, String)] ) : Array[(Int, String)] =
      expand_once( in ) match
          case Some( out ) => expand( out )
          case None => in
  def next_map ( expanded : Array[(Int, String)] ) : Array[Option[Int]] =
      def get_next ( i : Int ) : Option[Int] =
          var out : Option[Int] = Some(expanded.length)
          val level = expanded(i)._1
          var j = expanded.length - 1
          while j > i do
              if expanded(j)._1 == level then
                  out = Some(j)
              j -= 1
          if out == Some(i + 1) then
              out = None
          out
      for 
          i <- (0 to expanded.length - 1).toArray
      yield
          get_next(i)

  def make_list ( expanded : Array[(Int, String)] , at : Int = 0)(implicit n_map : Array[Option[Int]] = next_map( expanded ) ) : Option[NodeList] =
      val level = expanded(at)._1
      var childrenO : Option[List[Node]] = 
          for
              next <- n_map( at )
          yield for
              i <- ((at + 1) to (next - 1)).toList
              if expanded(i)._1 == level + 1
              list <- make_list( expanded, i )
          yield
              if expanded(i)._2.length == 1 then
                  Head( list )
              else
                  Item( expanded(i)._2.splitAt(1)._2, Some(list) )
      if childrenO == Some(Nil) then
          childrenO = None
      val aaa = 
          for
              children <- childrenO
          yield
              if expanded( at + 1 )._2(0) == '*' then 
                  UnorderedList( children )
              else
                  OrderedList( children )
      aaa
  def like_a_boss ( expanded : Array[(Int, String)] ) : Option[NodeList] =
      make_list( ((0,"dummy") :: ( for (i,s) <- expanded.toList yield (i+1,s) )).toArray )
  def parse ( text : String ) : Option[NodeList] =
      like_a_boss ( expand ( digest_leading_whitespace(text) ) )
