import spatial._
import org.virtualized._
import spatial.targets.DE1


// object Writer14 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 0  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](1, 320)
//       val sr = RegFile[Int16](1, 7)
//       // val ring_values = RegFile[Int16](32)

//       // val curr = Reg[Int16](2)
//       // val is_feature = Reg[Int16](0)
//       // val running_count = Reg[Int16](0)
//       // val thing = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             Pipe {
//               val grayPixel = fifoIn.deq()
            
//               sr(0, *) <<= lb(0, c)

//               val is_feature = ((sr(0, 0) > grayPixel) && (sr(0, 6) > grayPixel)) || ((sr(0, 0) < grayPixel) && (sr(0, 6) < grayPixel))

//               imgOut(r,c) = mux[Pixel16](is_feature, //r + c > 450,
//                 Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//                 Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//             }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }










object Writer13 extends SpatialApp {  // A slow version
  import IR._

  override val target = DE1

  val Kh = 3
  val Kw = 3
  val Rmax = 240
  val Cmax = 320
  val t = 0  // FIXME
  val n = 12

  type Int16 = FixPt[TRUE,_16,_0]
  type UInt8 = FixPt[FALSE,_8,_0]
  type UInt5 = FixPt[FALSE,_5,_0]
  type UInt6 = FixPt[FALSE,_6,_0]
  @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
  // @struct case class Coordinate(x: Int16, y: Int16)

  @virtualize
  def convolveVideoStream(rows: Int, cols: Int): Unit = {

    val imgIn  = StreamIn[Pixel16](target.VideoCamera)
    val imgOut = BufferedOut[Pixel16](target.VGA)

    Accel {
      val fifoIn = FIFO[Int16](320)
      val lb = LineBuffer[Int16](1, 320)
      val sr = RegFile[Int16](1, 7)
      // val ring_values = RegFile[Int16](32)

      // val curr = Reg[Int16](2)
      // val is_feature = Reg[Int16](0)
      // val running_count = Reg[Int16](0)
      // val thing = Reg[Int16](0)

      Stream(*) { _ =>
        Foreach(0 until Rmax) { r =>
          Foreach(0 until Cmax) { c => 
            val pixel = imgIn.value()
            val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
            lb.enq(grayPixel)
            fifoIn.enq(grayPixel)
          }

          Foreach(0 until Cmax) { c =>
            Pipe {
              val grayPixel = fifoIn.deq()
            
              sr(0, *) <<= lb(0, c)

              val is_feature = ((sr(0, 0) < grayPixel) && (sr(0, 6) > grayPixel)) || ((sr(0, 0) > grayPixel) && (sr(0, 6) < grayPixel))

              imgOut(r,c) = mux[Pixel16](is_feature, //r + c > 450,
                Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
                Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
            }
          }
        }
      }
      ()
    }
  }

  @virtualize
  def main() {
    val R = Rmax
    val C = Cmax
    convolveVideoStream(R, C)
  }
}








// object Writer12 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 0  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](1, 320)
//       val sr = RegFile[Int16](1, 7)
//       // val ring_values = RegFile[Int16](32)

//       // val curr = Reg[Int16](2)
//       // val is_feature = Reg[Int16](0)
//       // val running_count = Reg[Int16](0)
//       // val thing = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Pipe {
//               sr(0, *) <<= lb(0, c)
//               // thing := sr(0,3)
//             }
//             // println("thing: " + thing.value)

//             // Sequential.Foreach(0 until 16){ i =>
//             //   val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//             //   Sequential {
//             //     if (ring_pixel_val < grayPixel - t) {
//             //         ring_values(i) = -1
//             //         ring_values(i+16) = -1
//             //     } else if (ring_pixel_val > grayPixel + t) {
//             //         ring_values(i) = 1
//             //         ring_values(i+16) = 1
//             //     } else {
//             //         ring_values(i) = 0
//             //         ring_values(i+16) = 0
//             //     }
//             //   }
//             // }

//             // // Figure out if 12 continguous values below or above threshold
//             // running_count.reset
//             // curr.reset
//             // is_feature.reset
//             // Sequential.Foreach(0 until 32){ i =>
//             //   running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//             //   curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//             //   is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             // }

//             val is_feature = ((sr(0, 0) < grayPixel - t) && (sr(0, 6) > grayPixel + t)) || ((sr(0, 0) > grayPixel + t) && (sr(0, 6) < grayPixel - t))

//             imgOut(r,c) = mux[Pixel16](is_feature, //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }










// object Writer11 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 50  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       // val coord_x = RegFile[Int16](16)
//       // Pipe{
//       //   coord_x(0) = 2.to[Int16]
//       //   coord_x(1) = 3.to[Int16]
//       //   coord_x(2) = 4.to[Int16]
//       //   coord_x(3) = 5.to[Int16]
//       //   coord_x(4) = 6.to[Int16]
//       //   coord_x(5) = 6.to[Int16]
//       //   coord_x(6) = 6.to[Int16]
//       //   coord_x(7) = 5.to[Int16]
//       //   coord_x(8) = 4.to[Int16]
//       //   coord_x(9) = 3.to[Int16]
//       //   coord_x(10) = 2.to[Int16]
//       //   coord_x(11) = 1.to[Int16]
//       //   coord_x(12) = 0.to[Int16]
//       //   coord_x(13) = 0.to[Int16]
//       //   coord_x(14) = 0.to[Int16]
//       //   coord_x(15) = 1.to[Int16]
//       // }

//       // val coord_y = RegFile[Int16](16)
//       // Pipe{
//       //   coord_y(0) = 0.to[Int16]
//       //   coord_y(1) = 0.to[Int16]
//       //   coord_y(2) = 0.to[Int16]
//       //   coord_y(3) = 1.to[Int16]
//       //   coord_y(4) = 2.to[Int16]
//       //   coord_y(5) = 3.to[Int16]
//       //   coord_y(6) = 4.to[Int16]
//       //   coord_y(7) = 5.to[Int16]
//       //   coord_y(8) = 6.to[Int16]
//       //   coord_y(9) = 6.to[Int16]
//       //   coord_y(10) = 6.to[Int16]
//       //   coord_y(11) = 5.to[Int16]
//       //   coord_y(12) = 4.to[Int16]
//       //   coord_y(13) = 3.to[Int16]
//       //   coord_y(14) = 2.to[Int16]
//       //   coord_y(15) = 1.to[Int16]
//       // }

//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](1, 320)
//       val sr = RegFile[Int16](1, 7)
//       // val ring_values = RegFile[Int16](32)

//       // val curr = Reg[Int16](2)
//       // val is_feature = Reg[Int16](0)
//       // val running_count = Reg[Int16](0)
//       val thing = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Pipe {
//               sr(0, *) <<= lb(0, c)
//               thing := sr(0,3)
//             }
//             // println("thing: " + thing.value)

//             // Sequential.Foreach(0 until 16){ i =>
//             //   val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//             //   Sequential {
//             //     if (ring_pixel_val < grayPixel - t) {
//             //         ring_values(i) = -1
//             //         ring_values(i+16) = -1
//             //     } else if (ring_pixel_val > grayPixel + t) {
//             //         ring_values(i) = 1
//             //         ring_values(i+16) = 1
//             //     } else {
//             //         ring_values(i) = 0
//             //         ring_values(i+16) = 0
//             //     }
//             //   }
//             // }

//             // // Figure out if 12 continguous values below or above threshold
//             // running_count.reset
//             // curr.reset
//             // is_feature.reset
//             // Sequential.Foreach(0 until 32){ i =>
//             //   running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//             //   curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//             //   is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             // }

//             imgOut(r,c) = mux[Pixel16](thing.value < 128.to[Int16], //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }












// object Writer10 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 50  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       // val coord_x = RegFile[Int16](16)
//       // Pipe{
//       //   coord_x(0) = 2.to[Int16]
//       //   coord_x(1) = 3.to[Int16]
//       //   coord_x(2) = 4.to[Int16]
//       //   coord_x(3) = 5.to[Int16]
//       //   coord_x(4) = 6.to[Int16]
//       //   coord_x(5) = 6.to[Int16]
//       //   coord_x(6) = 6.to[Int16]
//       //   coord_x(7) = 5.to[Int16]
//       //   coord_x(8) = 4.to[Int16]
//       //   coord_x(9) = 3.to[Int16]
//       //   coord_x(10) = 2.to[Int16]
//       //   coord_x(11) = 1.to[Int16]
//       //   coord_x(12) = 0.to[Int16]
//       //   coord_x(13) = 0.to[Int16]
//       //   coord_x(14) = 0.to[Int16]
//       //   coord_x(15) = 1.to[Int16]
//       // }

//       // val coord_y = RegFile[Int16](16)
//       // Pipe{
//       //   coord_y(0) = 0.to[Int16]
//       //   coord_y(1) = 0.to[Int16]
//       //   coord_y(2) = 0.to[Int16]
//       //   coord_y(3) = 1.to[Int16]
//       //   coord_y(4) = 2.to[Int16]
//       //   coord_y(5) = 3.to[Int16]
//       //   coord_y(6) = 4.to[Int16]
//       //   coord_y(7) = 5.to[Int16]
//       //   coord_y(8) = 6.to[Int16]
//       //   coord_y(9) = 6.to[Int16]
//       //   coord_y(10) = 6.to[Int16]
//       //   coord_y(11) = 5.to[Int16]
//       //   coord_y(12) = 4.to[Int16]
//       //   coord_y(13) = 3.to[Int16]
//       //   coord_y(14) = 2.to[Int16]
//       //   coord_y(15) = 1.to[Int16]
//       // }

//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](7, 320)
//       val sr = RegFile[Int16](7, 7)
//       // val ring_values = RegFile[Int16](32)

//       // val curr = Reg[Int16](2)
//       // val is_feature = Reg[Int16](0)
//       // val running_count = Reg[Int16](0)
//       val thing = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Foreach(0 until 7){ i =>
//               // println(i + ": " + lb(i, c))
//               Pipe {
//                 sr(i, *) <<= lb(i, c)
//               }
//             }

//             Pipe {
//               thing := sr(3,3)
//             }
//             // println("thing: " + thing.value)

//             // Sequential.Foreach(0 until 16){ i =>
//             //   val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//             //   Sequential {
//             //     if (ring_pixel_val < grayPixel - t) {
//             //         ring_values(i) = -1
//             //         ring_values(i+16) = -1
//             //     } else if (ring_pixel_val > grayPixel + t) {
//             //         ring_values(i) = 1
//             //         ring_values(i+16) = 1
//             //     } else {
//             //         ring_values(i) = 0
//             //         ring_values(i+16) = 0
//             //     }
//             //   }
//             // }

//             // // Figure out if 12 continguous values below or above threshold
//             // running_count.reset
//             // curr.reset
//             // is_feature.reset
//             // Sequential.Foreach(0 until 32){ i =>
//             //   running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//             //   curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//             //   is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             // }

//             imgOut(r,c) = mux[Pixel16](thing.value < 0.to[Int16], //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }












// object Writer9 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 50  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       // val coord_x = RegFile[Int16](16)
//       // Pipe{
//       //   coord_x(0) = 2.to[Int16]
//       //   coord_x(1) = 3.to[Int16]
//       //   coord_x(2) = 4.to[Int16]
//       //   coord_x(3) = 5.to[Int16]
//       //   coord_x(4) = 6.to[Int16]
//       //   coord_x(5) = 6.to[Int16]
//       //   coord_x(6) = 6.to[Int16]
//       //   coord_x(7) = 5.to[Int16]
//       //   coord_x(8) = 4.to[Int16]
//       //   coord_x(9) = 3.to[Int16]
//       //   coord_x(10) = 2.to[Int16]
//       //   coord_x(11) = 1.to[Int16]
//       //   coord_x(12) = 0.to[Int16]
//       //   coord_x(13) = 0.to[Int16]
//       //   coord_x(14) = 0.to[Int16]
//       //   coord_x(15) = 1.to[Int16]
//       // }

//       // val coord_y = RegFile[Int16](16)
//       // Pipe{
//       //   coord_y(0) = 0.to[Int16]
//       //   coord_y(1) = 0.to[Int16]
//       //   coord_y(2) = 0.to[Int16]
//       //   coord_y(3) = 1.to[Int16]
//       //   coord_y(4) = 2.to[Int16]
//       //   coord_y(5) = 3.to[Int16]
//       //   coord_y(6) = 4.to[Int16]
//       //   coord_y(7) = 5.to[Int16]
//       //   coord_y(8) = 6.to[Int16]
//       //   coord_y(9) = 6.to[Int16]
//       //   coord_y(10) = 6.to[Int16]
//       //   coord_y(11) = 5.to[Int16]
//       //   coord_y(12) = 4.to[Int16]
//       //   coord_y(13) = 3.to[Int16]
//       //   coord_y(14) = 2.to[Int16]
//       //   coord_y(15) = 1.to[Int16]
//       // }

//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](7, 320)
//       val sr = RegFile[Int16](7, 7)
//       // val ring_values = RegFile[Int16](32)

//       // val curr = Reg[Int16](2)
//       // val is_feature = Reg[Int16](0)
//       // val running_count = Reg[Int16](0)
//       val thing = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Foreach(0 until 7){ i =>
//               // println(i + ": " + lb(i, c))
//               Pipe {
//                 sr(i, *) <<= lb(i, c)
//               }
//             }

//             Pipe {
//               thing := sr(3,3)
//             }
//             // println("thing: " + thing.value)

//             // Sequential.Foreach(0 until 16){ i =>
//             //   val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//             //   Sequential {
//             //     if (ring_pixel_val < grayPixel - t) {
//             //         ring_values(i) = -1
//             //         ring_values(i+16) = -1
//             //     } else if (ring_pixel_val > grayPixel + t) {
//             //         ring_values(i) = 1
//             //         ring_values(i+16) = 1
//             //     } else {
//             //         ring_values(i) = 0
//             //         ring_values(i+16) = 0
//             //     }
//             //   }
//             // }

//             // // Figure out if 12 continguous values below or above threshold
//             // running_count.reset
//             // curr.reset
//             // is_feature.reset
//             // Sequential.Foreach(0 until 32){ i =>
//             //   running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//             //   curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//             //   is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             // }

//             imgOut(r,c) = mux[Pixel16](thing.value > 100.to[Int16], //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }









// object Writer8 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 50  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       val coord_x = RegFile[Int16](16)
//       Pipe{
//         coord_x(0) = 2.to[Int16]
//         coord_x(1) = 3.to[Int16]
//         coord_x(2) = 4.to[Int16]
//         coord_x(3) = 5.to[Int16]
//         coord_x(4) = 6.to[Int16]
//         coord_x(5) = 6.to[Int16]
//         coord_x(6) = 6.to[Int16]
//         coord_x(7) = 5.to[Int16]
//         coord_x(8) = 4.to[Int16]
//         coord_x(9) = 3.to[Int16]
//         coord_x(10) = 2.to[Int16]
//         coord_x(11) = 1.to[Int16]
//         coord_x(12) = 0.to[Int16]
//         coord_x(13) = 0.to[Int16]
//         coord_x(14) = 0.to[Int16]
//         coord_x(15) = 1.to[Int16]
//       }

//       val coord_y = RegFile[Int16](16)
//       Pipe{
//         coord_y(0) = 0.to[Int16]
//         coord_y(1) = 0.to[Int16]
//         coord_y(2) = 0.to[Int16]
//         coord_y(3) = 1.to[Int16]
//         coord_y(4) = 2.to[Int16]
//         coord_y(5) = 3.to[Int16]
//         coord_y(6) = 4.to[Int16]
//         coord_y(7) = 5.to[Int16]
//         coord_y(8) = 6.to[Int16]
//         coord_y(9) = 6.to[Int16]
//         coord_y(10) = 6.to[Int16]
//         coord_y(11) = 5.to[Int16]
//         coord_y(12) = 4.to[Int16]
//         coord_y(13) = 3.to[Int16]
//         coord_y(14) = 2.to[Int16]
//         coord_y(15) = 1.to[Int16]
//       }

//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](7, 320)
//       val sr = RegFile[Int16](7, 7)
//       val ring_values = RegFile[Int16](32)

//       val curr = Reg[Int16](2)
//       val is_feature = Reg[Int16](0)
//       val running_count = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Foreach(0 until 7){ i =>
//               sr(i, *) <<= lb(i, c)
//             }

//             // val thing = sr(0,0)

//             Sequential.Foreach(0 until 16){ i =>
//               val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//               Sequential {
//                 if (ring_pixel_val < grayPixel - t) {
//                     ring_values(i) = -1
//                     ring_values(i+16) = -1
//                 } else if (ring_pixel_val > grayPixel + t) {
//                     ring_values(i) = 1
//                     ring_values(i+16) = 1
//                 } else {
//                     ring_values(i) = 0
//                     ring_values(i+16) = 0
//                 }
//               }
//             }

//             // Figure out if 12 continguous values below or above threshold
//             running_count.reset
//             curr.reset
//             is_feature.reset
//             Sequential.Foreach(0 until 32){ i =>
//               running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//               curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//               is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             }

//             imgOut(r,c) = mux[Pixel16](is_feature.value == 1.to[Int16], //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }










// object Writer7 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320
//   val t = 50  // FIXME
//   val n = 12

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)
//   // @struct case class Coordinate(x: Int16, y: Int16)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     Accel {
//       val coord_x = RegFile[Int16](16)
//       Pipe{
//         coord_x(0) = 2.to[Int16]
//         coord_x(1) = 3.to[Int16]
//         coord_x(2) = 4.to[Int16]
//         coord_x(3) = 5.to[Int16]
//         coord_x(4) = 6.to[Int16]
//         coord_x(5) = 6.to[Int16]
//         coord_x(6) = 6.to[Int16]
//         coord_x(7) = 5.to[Int16]
//         coord_x(8) = 4.to[Int16]
//         coord_x(9) = 3.to[Int16]
//         coord_x(10) = 2.to[Int16]
//         coord_x(11) = 1.to[Int16]
//         coord_x(12) = 0.to[Int16]
//         coord_x(13) = 0.to[Int16]
//         coord_x(14) = 0.to[Int16]
//         coord_x(15) = 1.to[Int16]
//       }

//       val coord_y = RegFile[Int16](16)
//       Pipe{
//         coord_y(0) = 0.to[Int16]
//         coord_y(1) = 0.to[Int16]
//         coord_y(2) = 0.to[Int16]
//         coord_y(3) = 1.to[Int16]
//         coord_y(4) = 2.to[Int16]
//         coord_y(5) = 3.to[Int16]
//         coord_y(6) = 4.to[Int16]
//         coord_y(7) = 5.to[Int16]
//         coord_y(8) = 6.to[Int16]
//         coord_y(9) = 6.to[Int16]
//         coord_y(10) = 6.to[Int16]
//         coord_y(11) = 5.to[Int16]
//         coord_y(12) = 4.to[Int16]
//         coord_y(13) = 3.to[Int16]
//         coord_y(14) = 2.to[Int16]
//         coord_y(15) = 1.to[Int16]
//       }

//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](7, 320)
//       val sr = RegFile[Int16](7, 7)
//       val ring_values = RegFile[Int16](32)

//       val curr = Reg[Int16](2)
//       val is_feature = Reg[Int16](0)
//       val running_count = Reg[Int16](0)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Foreach(0 until 7){ i =>
//               sr(i, *) <<= lb(i, c)
//             }

//             val thing = sr(0,0)

//             Sequential.Foreach(0 until 16){ i =>
//               val ring_pixel_val = sr(coord_x(i).to[Index], coord_y(i).to[Index])
//               Sequential {
//                 if (ring_pixel_val < grayPixel - t) {
//                     ring_values(i) = -1
//                     ring_values(i+16) = -1
//                 } else if (ring_pixel_val > grayPixel + t) {
//                     ring_values(i) = 1
//                     ring_values(i+16) = 1
//                 } else {
//                     ring_values(i) = 0
//                     ring_values(i+16) = 0
//                 }
//               }
//             }

//             // Figure out if 12 continguous values below or above threshold
//             running_count.reset
//             curr.reset
//             is_feature.reset
//             Sequential.Foreach(0 until 32){ i =>
//               running_count := mux[Int16](ring_values(i) == curr.value, running_count.value + 1, 1)
//               curr := mux[Int16](ring_values(i) == curr.value, curr.value, ring_values(i))
//               is_feature := mux[Int16](running_count.value == 12.to[Int16] && curr.value != 0.to[Int16], 1, is_feature.value)
//             }

//             imgOut(r,c) = mux[Pixel16](thing > 120, //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }












// object Writer6 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {
//     // val R = ArgIn[Int]
//     // val C = ArgIn[Int]
//     // setArg(R, rows)
//     // setArg(C, cols)

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgOut = BufferedOut[Pixel16](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)


//     // val cdwell = ArgIn[Int]
//     // val rdwell = ArgIn[Int]
//     // val cd = args(0).to[Int]
//     // setArg(cdwell, cd)
//     // val rd = args(1).to[Int]
//     // setArg(rdwell, rd)

//     Accel {
//       val fifoIn = FIFO[Int16](320)
//       val lb = LineBuffer[Int16](7, 320)
//       val sr = RegFile[Int16](7, 7)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             lb.enq(grayPixel)
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()

//             Foreach(0 until 7){ i =>
//               sr(i, *) <<= lb(i, c)
//             }

//             val thing = sr(0,0)

//             imgOut(r,c) = mux[Pixel16](thing > 120, //r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//             // Foreach(0 until cdwell) { _ => 
//               // imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
//             // }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }









// object Writer5 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {
//     // val R = ArgIn[Int]
//     // val C = ArgIn[Int]
//     // setArg(R, rows)
//     // setArg(C, cols)

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgOut = BufferedOut[Pixel16](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)


//     // val cdwell = ArgIn[Int]
//     // val rdwell = ArgIn[Int]
//     // val cd = args(0).to[Int]
//     // setArg(cdwell, cd)
//     // val rd = args(1).to[Int]
//     // setArg(rdwell, rd)

//     Accel {
//       val fifoIn = FIFO[Int16](320)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()
//             imgOut(r,c) = mux[Pixel16](r + c > 450,
//               Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
//               Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
//             // Foreach(0 until cdwell) { _ => 
//               // imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
//             // }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }








// object Writer4 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {
//     // val R = ArgIn[Int]
//     // val C = ArgIn[Int]
//     // setArg(R, rows)
//     // setArg(C, cols)

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgOut = BufferedOut[Pixel16](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)


//     // val cdwell = ArgIn[Int]
//     // val rdwell = ArgIn[Int]
//     // val cd = args(0).to[Int]
//     // setArg(cdwell, cd)
//     // val rd = args(1).to[Int]
//     // setArg(rdwell, rd)

//     Accel {
//       val fifoIn = FIFO[Int16](320)

//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()
//             val out = mux[Int16](r + c > 450, 255, grayPixel)
//             imgOut(r,c) = Pixel16(out(5::1).as[UInt5], out(5::0).as[UInt6], out(5::1).as[UInt5])
//             // Foreach(0 until cdwell) { _ => 
//               // imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
//             // }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }








// object Writer3 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {
//     // val R = ArgIn[Int]
//     // val C = ArgIn[Int]
//     // setArg(R, rows)
//     // setArg(C, cols)

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgOut = BufferedOut[Pixel16](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)


//     // val cdwell = ArgIn[Int]
//     // val rdwell = ArgIn[Int]
//     // val cd = args(0).to[Int]
//     // setArg(cdwell, cd)
//     // val rd = args(1).to[Int]
//     // setArg(rdwell, rd)

//     Accel {
//       val fifoIn = FIFO[Int16](320)
      
//       Stream(*) { _ =>
//         Foreach(0 until Rmax) { r =>
//           Foreach(0 until Cmax) { c => 
//             val pixel = imgIn.value()
//             val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//             fifoIn.enq(grayPixel)
//           }

//           Foreach(0 until Cmax) { c =>
//             val grayPixel = fifoIn.deq()
//             imgOut(r,c) = Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5])
//             // Foreach(0 until cdwell) { _ => 
//               // imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
//             // }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }









// object Writer2 extends SpatialApp {  // A slow version
//   import IR._

//   override val target = DE1

//   val Kh = 3
//   val Kw = 3
//   val Rmax = 240
//   val Cmax = 320

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @virtualize
//   def convolveVideoStream(rows: Int, cols: Int): Unit = {
//     // val R = ArgIn[Int]
//     // val C = ArgIn[Int]
//     // setArg(R, rows)
//     // setArg(C, cols)

//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgOut = BufferedOut[Pixel16](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)

//     // val cdwell = ArgIn[Int]
//     // val rdwell = ArgIn[Int]
//     // val cd = args(0).to[Int]
//     // setArg(cdwell, cd)
//     // val rd = args(1).to[Int]
//     // setArg(rdwell, rd)

//     Accel(*) {
//       Foreach(0 until Rmax) { r =>
//         Foreach(0 until Cmax) { c => 
//           val pixel = imgIn.value()
//           val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
//           imgOut(r,c) = Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5])
//         }

//         // Foreach(0 until Cmax) { c =>
//         //   // Foreach(0 until cdwell) { _ => 
//         //     imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
//         //   // }
//         // }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     val R = Rmax
//     val C = Cmax
//     convolveVideoStream(R, C)
//   }
// }