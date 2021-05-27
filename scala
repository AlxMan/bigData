第一题：百元喝酒
package scalapart01

object demo01 {

  def main(args: Array[String]): Unit = {

    var one :Int = 100/2;
    println(s"总喝瓶数：${one+process(0,one,one)}")
  }

  def process(sum:Int,bottle:Int,cap:Int):Int = {
    if(bottle<3 && cap<5) return sum
    var a1 = bottle/3
    var a2 = bottle%3
    var b1 = cap/5
    var b2 = cap%5
    println(f"总数=${a1+b1+sum} 上次剩余瓶子=${a1+b1+a2}%02d 上次剩余瓶盖=${a1+b1+b2}%02d")
    return process(a1+b1+sum,a1+b1+a2,a1+b1+b2)
  }

}


第二题：人机猜拳
package scalapart01

import scala.util.control.Breaks.{break, breakable}

object demo02 {

  def main(args: Array[String]): Unit = {
    println("欢迎参加游戏")
    //1. 选取对战角色
    println("开始选取角色 孙悟空输入[1]  唐僧输入[2]")
    var role=scala.io.StdIn.readInt()
    while(role !=1 && role !=2){
      println("没有这个选项，请重新输入:")
      role=scala.io.StdIn.readInt()
    }
    println(s"您选择的是[${if(role==1)"孙悟空" else "唐僧"}]")
    println()
    //2. 开始对战，用户出拳，与对手进行比较，提示胜负信息
    println("游戏开始")
    println("游戏介绍:1.石头输入[1] 2.剪刀输入[2] 3.布输入[3] 4.退出输入[n]")
    var result = ""
    var score:Int = 0
    breakable{
      while(true){
        println("游请出拳")
        result = scala.io.StdIn.readLine()
        if(result != "1" && result != "2" && result != "3"){
          //4 . 循环对战，当输入“n”时，终止对战，并显示对战结果
          if(result == "n") break
          println("您输入错误！请重新输入")
        }else{
          //3. 猜拳结束算分，平局都加一分，获胜加二分，失败不加分
          var computer = scala.util.Random.nextInt(3)+1+""
          var sco = if(computer == result) 1 else {if((result.toInt-computer.toInt)==1||(result.toInt-computer.toInt)== -2 ) 0 else 2}
          println(s"你出的是[${if(result == "1")"石头"else if(result == "2")"剪刀" else "布"}]," +
            s"机器出的是[${if(computer == "1")"石头"else if(computer == "2")"剪刀" else "布"}]," +
            s"结果您是[${if(sco==2)"胜利" else if(sco == 1) "平局" else "失败"}]")
          score += sco
        }
      }
    }
    //5. 游戏结束后显示得分
    println(s"您的分数是[$score]")
    println("bye bye")
  }

}


第三题：用户位置时长统计
package scalapart01

  case class UserInfo(userName: String, location: String, startTime: Int, duration: Int)

  object demo03 {
    def main(args: Array[String]): Unit = {
      val userInfoList: List[UserInfo] = List(
        UserInfo("UserA", "LocationA", 8, 60),
        UserInfo("UserA", "LocationA", 9, 60),
        UserInfo("UserA", "LocationB", 10, 60),
        UserInfo("UserA", "LocationB", 11, 80)
      )


      val userMap = userInfoList.groupBy(t => t.userName + "," + t.location)
      val orderByUserMap = userMap.mapValues(t => t.sortBy(x => x.startTime))

      var firstTime = 0

      val totalMap = orderByUserMap.mapValues(t => {
        firstTime = t.head.startTime
        var sum = t.map(x => x.duration).sum
        sum
      })
      totalMap.foreach {
        case (datas, sumTime) => println(s"$datas,$firstTime,$sumTime")
      }
    }

}
