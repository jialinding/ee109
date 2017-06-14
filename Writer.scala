import spatial._
import org.virtualized._
import spatial.targets.DE1

object Writer6 extends SpatialApp {  // A slow version
  import IR._

  override val target = DE1

  val Kh = 3
  val Kw = 3
  val Rmax = 240
  val Cmax = 320

  type Int16 = FixPt[TRUE,_16,_0]
  type UInt8 = FixPt[FALSE,_8,_0]
  type UInt5 = FixPt[FALSE,_5,_0]
  type UInt6 = FixPt[FALSE,_6,_0]
  @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

  @virtualize
  def convolveVideoStream(rows: Int, cols: Int): Unit = {
    // val R = ArgIn[Int]
    // val C = ArgIn[Int]
    // setArg(R, rows)
    // setArg(C, cols)

    val imgIn  = StreamIn[Pixel16](target.VideoCamera)
    // val imgOut = BufferedOut[Pixel16](target.VGA)
    val imgOut = BufferedOut[Pixel16](target.VGA)


    // val cdwell = ArgIn[Int]
    // val rdwell = ArgIn[Int]
    // val cd = args(0).to[Int]
    // setArg(cdwell, cd)
    // val rd = args(1).to[Int]
    // setArg(rdwell, rd)

    Accel {
      val fifoIn = FIFO[Int16](320)
      val lb = LineBuffer[Int16](7, 320)
      val sr = RegFile[Int16](7, 7)

      Stream(*) { _ =>
        Foreach(0 until Rmax) { r =>
          Foreach(0 until Cmax) { c => 
            val pixel = imgIn.value()
            val grayPixel = (pixel.b.to[Int16] + pixel.g.to[Int16] + pixel.r.to[Int16]) / 3
            lb.enq(grayPixel)
            fifoIn.enq(grayPixel)
          }

          Foreach(0 until Cmax) { c =>
            val grayPixel = fifoIn.deq()

            Foreach(0 until sr_height){ i =>
              sr(i, *) <<= lb(i, c)
            }

            val thing = sr(0,0)

            imgOut(r,c) = mux[Pixel16](thing > 120, //r + c > 450,
              Pixel16(0.to[UInt5], 0.to[UInt6], 31.to[UInt5]),
              Pixel16(grayPixel(5::1).as[UInt5], grayPixel(5::0).as[UInt6], grayPixel(5::1).as[UInt5]))
            // Foreach(0 until cdwell) { _ => 
              // imgOut(r,c) = Pixel16(0.to[UInt5], 63.to[UInt6], 31.to[UInt5]) // Technically should be sqrt(horz**2 + vert**2)
            // }
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