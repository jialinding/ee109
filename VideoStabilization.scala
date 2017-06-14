// import spatial._
// import org.virtualized._
// import spatial.targets.DE1

// object VideoStabilization extends SpatialApp { 
//   import IR._

//   override val target = DE1

//   val R = 16  // FIXME
//   val C = 128  // FIXME
//   val t = 50.to[Int16]  // FIXME
//   val n = 12
//   val lb_height = 7
//   val lb_width = 640
//   val sr_height = 7
//   val sr_width = 7
//   val hamming_threshold = 1.0.to[Q16_16]

//   type Int16 = FixPt[TRUE,_16,_0]
//   type UInt10 = FixPt[FALSE,_10,_0]
//   type UInt8 = FixPt[FALSE,_8,_0]
//   type UInt5 = FixPt[FALSE,_5,_0]
//   type UInt6 = FixPt[FALSE,_6,_0]
//   type UInt3 = FixPt[FALSE,_3,_0]
//   type UInt1 = FixPt[FALSE,_1,_0]
//   type Q16_16 = FixPt[TRUE,_16,_16]
//   @struct case class Pixel24(b: UInt8, g: UInt8, r: UInt8)
//   @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

//   @struct case class Coordinate(x: Int16, y: Int16)
//   @struct case class Match(x1: UInt10, y1: UInt10, x2: UInt10, y2: UInt10)

//   @virtualize
//   def detect_FAST(): Unit = {
//     // val imgIn  = StreamIn[Pixel24](target.VideoCamera)  // TODO: change back to Pixel16
//     val imgIn  = StreamIn[Pixel16](target.VideoCamera)
//     // val imgIn2  = StreamIn[Pixel24](target.VideoCamera)  // TODO: change back to Pixel16
//     // val imgOut = StreamOut[Pixel24](target.VGA)
//     // val imgOut = BufferedOut[Pixel24](target.VGA)
//     val imgOut = BufferedOut[Pixel16](target.VGA)
//     // val imgOut = StreamOut[Pixel16](target.VGA)

//     Accel {
//       val sr_coord = RegFile[Coordinate](16)
//       Pipe{
//         sr_coord(0) = Coordinate(2.to[Int16], 0.to[Int16])
//         sr_coord(1) = Coordinate(3.to[Int16], 0.to[Int16])
//         sr_coord(2) = Coordinate(4.to[Int16], 0.to[Int16])
//         sr_coord(3) = Coordinate(5.to[Int16], 1.to[Int16])
//         sr_coord(4) = Coordinate(6.to[Int16], 2.to[Int16])
//         sr_coord(5) = Coordinate(6.to[Int16], 3.to[Int16])
//         sr_coord(6) = Coordinate(6.to[Int16], 4.to[Int16])
//         sr_coord(7) = Coordinate(5.to[Int16], 5.to[Int16])
//         sr_coord(8) = Coordinate(4.to[Int16], 6.to[Int16])
//         sr_coord(9) = Coordinate(3.to[Int16], 6.to[Int16])
//         sr_coord(10) = Coordinate(2.to[Int16], 6.to[Int16])
//         sr_coord(11) = Coordinate(1.to[Int16], 5.to[Int16])
//         sr_coord(12) = Coordinate(0.to[Int16], 4.to[Int16])
//         sr_coord(13) = Coordinate(0.to[Int16], 3.to[Int16])
//         sr_coord(14) = Coordinate(0.to[Int16], 2.to[Int16])
//         sr_coord(15) = Coordinate(1.to[Int16], 1.to[Int16])
//       }

//       val descriptor_coord_1 = RegFile[Coordinate](16)
//       Pipe{
//         descriptor_coord_1(0) = Coordinate(0.to[Int16], 0.to[Int16])
//         descriptor_coord_1(1) = Coordinate(1.to[Int16], 0.to[Int16])
//         descriptor_coord_1(2) = Coordinate(2.to[Int16], 0.to[Int16])
//         descriptor_coord_1(3) = Coordinate(3.to[Int16], 0.to[Int16])
//         descriptor_coord_1(4) = Coordinate(4.to[Int16], 0.to[Int16])
//         descriptor_coord_1(5) = Coordinate(5.to[Int16], 0.to[Int16])
//         descriptor_coord_1(6) = Coordinate(5.to[Int16], 0.to[Int16])

//         descriptor_coord_1(7) = Coordinate(0.to[Int16], 0.to[Int16])
//         descriptor_coord_1(8) = Coordinate(0.to[Int16], 1.to[Int16])
//         descriptor_coord_1(9) = Coordinate(0.to[Int16], 2.to[Int16])
//         descriptor_coord_1(10) = Coordinate(0.to[Int16], 3.to[Int16])
//         descriptor_coord_1(11) = Coordinate(0.to[Int16], 4.to[Int16])
//         descriptor_coord_1(12) = Coordinate(0.to[Int16], 5.to[Int16])
//         descriptor_coord_1(13) = Coordinate(0.to[Int16], 6.to[Int16])

//         descriptor_coord_1(14) = Coordinate(0.to[Int16], 0.to[Int16])
//         descriptor_coord_1(15) = Coordinate(0.to[Int16], 6.to[Int16])
//       }

//       val descriptor_coord_2 = RegFile[Coordinate](16)
//       Pipe{
//         descriptor_coord_2(0) = Coordinate(0.to[Int16], 6.to[Int16])
//         descriptor_coord_2(1) = Coordinate(1.to[Int16], 6.to[Int16])
//         descriptor_coord_2(2) = Coordinate(2.to[Int16], 6.to[Int16])
//         descriptor_coord_2(3) = Coordinate(3.to[Int16], 6.to[Int16])
//         descriptor_coord_2(4) = Coordinate(4.to[Int16], 6.to[Int16])
//         descriptor_coord_2(5) = Coordinate(5.to[Int16], 6.to[Int16])
//         descriptor_coord_2(6) = Coordinate(5.to[Int16], 6.to[Int16])

//         descriptor_coord_2(7) = Coordinate(6.to[Int16], 0.to[Int16])
//         descriptor_coord_2(8) = Coordinate(6.to[Int16], 1.to[Int16])
//         descriptor_coord_2(9) = Coordinate(6.to[Int16], 2.to[Int16])
//         descriptor_coord_2(10) = Coordinate(6.to[Int16], 3.to[Int16])
//         descriptor_coord_2(11) = Coordinate(6.to[Int16], 4.to[Int16])
//         descriptor_coord_2(12) = Coordinate(6.to[Int16], 5.to[Int16])
//         descriptor_coord_2(13) = Coordinate(6.to[Int16], 6.to[Int16])

//         descriptor_coord_2(14) = Coordinate(6.to[Int16], 0.to[Int16])
//         descriptor_coord_2(15) = Coordinate(6.to[Int16], 6.to[Int16])
//       }

//       val sr = RegFile[Int16](sr_height, sr_width)
//       val fifoIn = FIFO[Int16](C)
//       // val fifoOut = FIFO[Int16](C)
//       val lb = LineBuffer[Int16](lb_height, C)

//       // val fifoDescriptor1 = FIFO[UInt1](3200)
//       // val fifoDescriptor2 = FIFO[UInt1](3200)
//       val fifo_prev_descriptors = FIFO[UInt1](3200)
//       val fifo_curr_descriptors = FIFO[UInt1](3200)
//       val fifo_prev_coord = FIFO[Coordinate](200)
//       val fifo_curr_coord = FIFO[Coordinate](200)
//       // val matches = FIFO[Match](200)
//       val matches = SRAM[Match](300)
//       val num_matches = Reg[Int](0)

//       val numDescriptors = Reg[Int16](0)
//       val numDescriptorsPrev = Reg[Int16](0)

//       // val lastDescriptor = RegFile[UInt1](16)

//       // val row = 0
//       // val col = 0

//       val curr = Reg[Int16](0)
//       val is_feature = Reg[Int16](0)
//       val running_count = Reg[Int16](1)
//       val frame_counter = Reg[UInt1](0)
//       val is_first_frame = Reg[UInt1](1)
//       val hamming_distance = Reg[Int16](0)
//       val min_hamming_distance = Reg[Int16](16)
//       val second_min_hamming_distance = Reg[Int16](16)
//       val best_match_coord = Reg[Coordinate]

//       // for calculating affine transform
//       val prev_points = SRAM[Q16_16](3, 3)
//       // val curr_points = SRAM[UInt10](2, 3)
//       val curr_points = SRAM[Q16_16](3, 3)
//       // val prev_points_inv = SRAM[UInt10](3, 3)
//       val curr_points_inv = SRAM[Q16_16](3, 3)
//       val M = SRAM[Q16_16](2, 3)
//       val best_geo_distance = Reg[Q16_16](10000)
//       val cumulative_M = SRAM[Q16_16](2, 3)
//       Sequential {
//         cumulative_M(0, 0) = 1.to[Q16_16]
//         cumulative_M(0, 1) = 0.to[Q16_16]
//         cumulative_M(0, 2) = 0.to[Q16_16]
//         cumulative_M(1, 0) = 0.to[Q16_16]
//         cumulative_M(1, 1) = 1.to[Q16_16]
//         cumulative_M(1, 2) = 0.to[Q16_16]
//       }

//       // frame_counter := 0
//       frame_counter.reset
//       is_first_frame.reset // 1

//       // println("Start of stream")

//       Stream(*) { _ =>
//         // val pixel = imgIn.value()
//         // // TODO: fix this if using Pixel16 as input
//         // val grayscale_pixel = (pixel.b.to[Int16]*8 + pixel.g.to[Int16]*4 + pixel.r.to[Int16]*8) / 3
//         // // println(grayscale_pixel)
//         // fifoIn.enq(grayscale_pixel)

//         Foreach(0 until R){ r => 
//           Sequential.Foreach(0 until C) { _ => 
//             Sequential {
//               val pixel = imgIn.value()
//               val grayscale_pixel = (pixel.b.to[Int16]*8 + pixel.g.to[Int16]*4 + pixel.r.to[Int16]*8) / 3
//               fifoIn.enq(grayscale_pixel)
//               lb.enq(grayscale_pixel)
//             } 
//           }
//           Sequential.Foreach(0 until C) { c =>
//             Sequential {
//               // println("loop " + r + " " + c)
//               val grayscale_pixel = fifoIn.deq()
              
//               Pipe {
//                 Foreach(0 until sr_height){ i =>
//                   sr(i, *) <<= lb(i, c)
//                 }

//                 // println("row " + r + " col " + c + " grayscale " + grayscale_pixel)

//                 val ring_values = RegFile[Int16](32)

//                 Sequential.Foreach(0 until 16){ i =>
//                   val ring_pixel = sr_coord(i)
//                   val ring_pixel_val = sr(ring_pixel.x.to[Index], ring_pixel.y.to[Index])
//                   Sequential {
//                     ring_values(i) = 0
//                     ring_values(i+16) = 0
//                     if (ring_pixel_val < (grayscale_pixel - t)) {
//                         ring_values(i) = -1
//                         ring_values(i+16) = -1
//                     }
//                     if (ring_pixel_val > (grayscale_pixel + t)) {
//                         ring_values(i) = 1
//                         ring_values(i+16) = 1
//                     }
//                   }
//                 }

//                 // Figure out if 12 contiguous values below or above threshold
//                 running_count.reset  // 1
//                 curr := ring_values(0)
//                 is_feature.reset  // 0
//                 Sequential.Foreach(1 until 32){ i =>
//                   if (ring_values(i) == curr.value) {
//                       running_count := running_count.value + 1
//                   } else {
//                       running_count.reset  // 1
//                       curr := ring_values(i)
//                   }

//                   if (running_count.value == 12.to[Int16] && curr.value != 0.to[Int16]) {
//                       is_feature := 1
//                       // println(r + " " + c)
//                   }
//                 }

//                 if ((is_feature.value == 1.to[Int16]) && (r > 4.to[Index]) && numDescriptors.value < 200) {
//                   // println("add feature")
//                   val brief_descriptor = RegFile[UInt1](16)
//                   Pipe {
//                     Sequential.Foreach(0 until 16){ i =>
//                       Sequential {
//                         val pt1 = descriptor_coord_1(i)
//                         val pt2 = descriptor_coord_2(i)
//                         brief_descriptor(i) = 0.to[UInt1]
//                         if (sr(pt1.x.to[Index], pt1.y.to[Index]) > sr(pt2.x.to[Index], pt2.y.to[Index])) {
//                           brief_descriptor(i) = 1.to[UInt1]
//                         }
//                       }
//                     }

//                     if (is_first_frame.value.to[Int] == 0 && num_matches.value < 300) {
//                       // println(num_matches.value < 300)
//                       // match with descriptors from previous frame
//                       min_hamming_distance.reset  // 16
//                       second_min_hamming_distance.reset  // 16
//                       // println("finding match " + numDescriptorsPrev.value)
//                       Sequential.Foreach(0 until numDescriptorsPrev.value.to[Index]){ i =>
//                         hamming_distance.reset  // 0

//                         Sequential.Foreach(0 until 16){ j =>
//                           val bit = fifo_prev_descriptors.deq()
//                           if (bit != brief_descriptor(j)) {
//                             hamming_distance := hamming_distance.value + 1
//                           }
//                           fifo_prev_descriptors.enq(bit)
//                         }
//                         if (hamming_distance.value < min_hamming_distance.value) {
//                           second_min_hamming_distance := min_hamming_distance.value
//                           min_hamming_distance := hamming_distance.value
//                           best_match_coord := fifo_prev_coord.deq()
//                           fifo_prev_coord.enq(best_match_coord.value)
//                         } else if (hamming_distance.value < second_min_hamming_distance.value) {
//                           second_min_hamming_distance := hamming_distance.value
//                           fifo_prev_coord.enq(fifo_prev_coord.deq())
//                         } else {
//                           fifo_prev_coord.enq(fifo_prev_coord.deq())
//                         }
//                       }
//                       // TODO: add distance and orientation checks from IEEE paper
//                       if (min_hamming_distance.value.to[Q16_16] / second_min_hamming_distance.value.to[Q16_16] <= hamming_threshold && num_matches.value < 300) {
//                         // it's a match
//                         // println(min_hamming_distance.value.to[Q16_16] + " " + second_min_hamming_distance.value.to[Q16_16])
//                         matches(num_matches.value) = mux[Match](num_matches.value < 300,
//                           Match(x1=best_match_coord.value.x.to[UInt10], y1=best_match_coord.value.y.to[UInt10], x2=c.to[UInt10], y2=r.to[UInt10]),
//                           Match(0.to[UInt10], 0.to[UInt10], 0.to[UInt10], 0.to[UInt10]))
//                         num_matches := mux[Int](num_matches.value < 300, num_matches.value + 1, num_matches.value)
//                         // matches.enq(Match(x1=best_match_coord.value.x.to[UInt10], y1=best_match_coord.value.y.to[UInt10], x2=c.to[UInt10], y2=r.to[UInt10]))
//                       }
//                     }
//                     Sequential.Foreach(0 until 16){ i =>
//                       fifo_curr_descriptors.enq(brief_descriptor(i))
//                     }
//                   }
//                   fifo_curr_coord.enq(Coordinate(c.to[Int16], r.to[Int16]))
//                   numDescriptors := numDescriptors.value + 1
//                   // println("does this never happen?")
//                 }
//               }

//               // // For debug purposes
//               // fifoOut.enq(mux[Int16]((is_feature.value == 1.to[Int16]) && (r > 4.to[Index]), 255, grayscale_pixel))
              
//               // end of frame updates
//               if (r == R-1 && c == C-1) {
//                 // println("end of frame updates")
//                 frame_counter := mux[UInt1](frame_counter == 0.to[UInt1], 1, 0)
//                 numDescriptorsPrev := numDescriptors.value
//                 numDescriptors.reset  // 0

//                 Pipe {
//                   // println(fifo_prev_descriptors.numel())
//                   Sequential.Foreach(0 until fifo_prev_descriptors.numel()){ i =>
//                     fifo_prev_descriptors.deq()
//                   }
//                   // println(fifo_curr_descriptors.numel())
//                   Sequential.Foreach(0 until fifo_curr_descriptors.numel()){ i =>
//                     val bit = fifo_curr_descriptors.deq()
//                     fifo_prev_descriptors.enq(bit)
//                   }
//                   // println(fifo_prev_coord.numel())
//                   Sequential.Foreach(0 until fifo_prev_coord.numel()){ _ =>
//                     fifo_prev_coord.deq()
//                   }
//                   // println(fifo_curr_coord.numel())
//                   Sequential.Foreach(0 until fifo_curr_coord.numel()){ _ =>
//                     fifo_prev_coord.enq(fifo_curr_coord.deq())
//                   }
//                 }

//                 // println(matches.numel())

//                 // println("start matches")
//                 if (is_first_frame.value.to[Int] == 0) {
//                   // println(matches.numel() / 3)
//                   // println(num_matches.value / 3)
//                   val third = num_matches.value / 3
//                   best_geo_distance.reset  // 10000
//                   // Foreach(0 until matches.numel() / 3){ i =>
                  
//                   Pipe {
//                     Sequential.Foreach(0 until num_matches.value / 3){ i =>
//                       // println("3-match " + i)
//                       Sequential {
//                         // val match1 = matches.deq()
//                         // val match2 = matches.deq()
//                         // val match3 = matches.deq()
//                         val match1 = matches(i)
//                         val match2 = matches(i + third)
//                         val match3 = matches(i + 2*third)

//                         // Use Heron's formula to calculate the difference in areas between triangles
//                         val shoelace_prev = 0.5.to[Q16_16] * ((match1.x1 - match3.x1)*(match2.y1 - match1.y1) -
//                           (match1.x1 - match2.x1)*(match3.y1 - match1.y1)).to[Q16_16]
//                         val area_prev = mux[Q16_16](shoelace_prev >= 0, shoelace_prev, -1 * shoelace_prev)
//                         val shoelace_curr = 0.5.to[Q16_16] * ((match1.x2 - match3.x2)*(match2.y2 - match1.y2) -
//                           (match1.x2 - match2.x2)*(match3.y2 - match1.y2)).to[Q16_16]
//                         val area_curr = mux[Q16_16](shoelace_curr >= 0, shoelace_curr, -1 * shoelace_curr)
//                         val area_diff = area_curr - area_prev
//                         val geo_distance = mux[Q16_16](area_diff >= 0, area_diff, -1 * area_diff)
//                         // println("geo_distance: " + geo_distance)
                        
//                         if (geo_distance < best_geo_distance) {
//                           curr_points(0, 0) = match1.x2.to[Q16_16]
//                           curr_points(0, 1) = match2.x2.to[Q16_16]
//                           curr_points(0, 2) = match3.x2.to[Q16_16]
//                           curr_points(1, 0) = match1.y2.to[Q16_16]
//                           curr_points(1, 1) = match2.y2.to[Q16_16]
//                           curr_points(1, 2) = match3.y2.to[Q16_16]
//                           curr_points(2, 0) = 1.to[Q16_16]
//                           curr_points(2, 1) = 1.to[Q16_16]
//                           curr_points(2, 2) = 1.to[Q16_16]

//                           // val det = prev_points(0, 0) * (prev_points(1, 1) * prev_points(2, 2) - prev_points(2, 1) * prev_points(1, 2)) -
//                           //   prev_points(0, 1) * (prev_points(1, 0) * prev_points(2, 2) - prev_points(1, 2) * prev_points(2, 0)) +
//                           //   prev_points(0, 2) * (prev_points(1, 0) * prev_points(2, 1) - prev_points(1, 1) * prev_points(2, 0))
//                           val det = curr_points(0, 0) * (curr_points(1, 1) * curr_points(2, 2) - curr_points(2, 1) * curr_points(1, 2)) -
//                             curr_points(0, 1) * (curr_points(1, 0) * curr_points(2, 2) - curr_points(1, 2) * curr_points(2, 0)) +
//                             curr_points(0, 2) * (curr_points(1, 0) * curr_points(2, 1) - curr_points(1, 1) * curr_points(2, 0))
                          
//                           // println(det)
//                           // println("determinant is 0: " + (det.to[Int] == 0))
//                           // println("before if statement")
//                           if (det.to[Int] != 0) {
//                             best_geo_distance := geo_distance
//                             // println("det " + det)
//                             // println("det int " + det.to[Int])

//                             val invdet = 1.0.to[Q16_16]/det.to[Q16_16]
//                             // println("invdet " + invdet)
//                             curr_points_inv(0, 0) = (curr_points(1, 1) * curr_points(2, 2) - curr_points(2, 1) * curr_points(1, 2)).to[Q16_16] * invdet;
//                             curr_points_inv(0, 1) = (curr_points(0, 2) * curr_points(2, 1) - curr_points(0, 1) * curr_points(2, 2)).to[Q16_16] * invdet;
//                             curr_points_inv(0, 2) = (curr_points(0, 1) * curr_points(1, 2) - curr_points(0, 2) * curr_points(1, 1)).to[Q16_16] * invdet;
//                             curr_points_inv(1, 0) = (curr_points(1, 2) * curr_points(2, 0) - curr_points(1, 0) * curr_points(2, 2)).to[Q16_16] * invdet;
//                             curr_points_inv(1, 1) = (curr_points(0, 0) * curr_points(2, 2) - curr_points(0, 2) * curr_points(2, 0)).to[Q16_16] * invdet;
//                             curr_points_inv(1, 2) = (curr_points(1, 0) * curr_points(0, 2) - curr_points(0, 0) * curr_points(1, 2)).to[Q16_16] * invdet;
//                             curr_points_inv(2, 0) = (curr_points(1, 0) * curr_points(2, 1) - curr_points(2, 0) * curr_points(1, 1)).to[Q16_16] * invdet;
//                             curr_points_inv(2, 1) = (curr_points(2, 0) * curr_points(0, 1) - curr_points(0, 0) * curr_points(2, 1)).to[Q16_16] * invdet;
//                             curr_points_inv(2, 2) = (curr_points(0, 0) * curr_points(1, 1) - curr_points(1, 0) * curr_points(0, 1)).to[Q16_16] * invdet;

//                             prev_points(0, 0) = match1.x1.to[Q16_16]
//                             prev_points(0, 1) = match2.x1.to[Q16_16]
//                             prev_points(0, 2) = match3.x1.to[Q16_16]
//                             prev_points(1, 0) = match1.y1.to[Q16_16]
//                             prev_points(1, 1) = match2.y1.to[Q16_16]
//                             prev_points(1, 2) = match3.y1.to[Q16_16]
//                             prev_points(2, 0) = 1.to[Q16_16]
//                             prev_points(2, 1) = 1.to[Q16_16]
//                             prev_points(2, 2) = 1.to[Q16_16]

//                             // M is the affine transfrom from curr_points to prev_points
//                             M(0, 0) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,0) + prev_points(0,1)*curr_points_inv(1,0) + prev_points(0,2)*curr_points_inv(2,0), 1)
//                             M(0, 1) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,1) + prev_points(0,1)*curr_points_inv(1,1) + prev_points(0,2)*curr_points_inv(2,1), 0)
//                             M(0, 2) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,2) + prev_points(0,1)*curr_points_inv(1,2) + prev_points(0,2)*curr_points_inv(2,2), 0)
//                             M(1, 0) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,0) + prev_points(1,1)*curr_points_inv(1,0) + prev_points(1,2)*curr_points_inv(2,0), 0)
//                             M(1, 1) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,1) + prev_points(1,1)*curr_points_inv(1,1) + prev_points(1,2)*curr_points_inv(2,1), 1)
//                             M(1, 2) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,2) + prev_points(1,1)*curr_points_inv(1,2) + prev_points(1,2)*curr_points_inv(2,2), 0)
//                             // println(M(0,0))
//                             // println(M(0,1))
//                             // println(M(0,2))
//                             // println(M(1,0))
//                             // println(M(1,1))
//                             // println(M(1,2))
//                           }
//                         }
//                       }
//                     }

//                     // compute new cumulative transform: new_cum=M*cum
//                     val t00 = M(0,0)*cumulative_M(0,0) + M(0,1)*cumulative_M(1,0)
//                     val t01 = M(0,0)*cumulative_M(0,1) + M(0,1)*cumulative_M(1,1)
//                     val t02 = M(0,0)*cumulative_M(0,2) + M(0,1)*cumulative_M(1,2) + M(0,2)
//                     val t10 = M(1,0)*cumulative_M(0,0) + M(1,1)*cumulative_M(1,0)
//                     val t11 = M(1,0)*cumulative_M(0,1) + M(1,1)*cumulative_M(1,1)
//                     val t12 = M(1,0)*cumulative_M(0,2) + M(1,1)*cumulative_M(1,2) + M(1,2)

//                     // cumulative_M is the affine transform from the current frame to the first frame
//                     Sequential {
//                       cumulative_M(0,0) = t00
//                       cumulative_M(0,1) = t01
//                       cumulative_M(0,2) = t02
//                       cumulative_M(1,0) = t10
//                       cumulative_M(1,1) = t11
//                       cumulative_M(1,2) = t12
//                       // cumulative_M(0,0) = 1
//                       // cumulative_M(0,1) = 1
//                       // cumulative_M(0,2) = 1
//                       // cumulative_M(1,0) = 1
//                       // cumulative_M(1,1) = 1
//                       // cumulative_M(1,2) = 1
//                     }
//                   }

//                   // println(cumulative_M(0,0))
//                   // println(cumulative_M(0,1))
//                   // println(cumulative_M(0,2))
//                   // println(cumulative_M(1,0))
//                   // println(cumulative_M(1,1))
//                   // println(cumulative_M(1,2))
//                 }

//                 num_matches.reset
//                 if (is_first_frame.value.to[Int] == 1) {
//                   is_first_frame := 0
//                 }
//               }

//               // val pixel_value = fifoOut.deq()
//               val t_c = cumulative_M(0,0).to[Int]*c + cumulative_M(0,1).to[Int]*r + cumulative_M(0,2).to[Int]
//               val t_r = cumulative_M(1,0).to[Int]*c + cumulative_M(1,1).to[Int]*r + cumulative_M(1,2).to[Int]
//               if (t_c >= 0 && t_c < 320 && t_r >= 0 && t_r < 240) {
//                 imgOut(t_r, t_c) = Pixel16(grayscale_pixel(7::3).as[UInt5],
//                   grayscale_pixel(7::2).as[UInt6],
//                   grayscale_pixel(7::3).as[UInt5])
//               }

//               // imgOut(r, c) = Pixel16(pixel_value(7::3).as[UInt5],
//               //   pixel_value(7::2).as[UInt6],
//               //   pixel_value(7::3).as[UInt5])
//             }
//           }
//         }
//       }
//       ()
//     }
//   }

//   @virtualize
//   def main() {
//     detect_FAST()
//   }
// }

import spatial._
import org.virtualized._
import spatial.targets.DE1

object VideoStabilization extends SpatialApp { 
  import IR._

  override val target = DE1

  val R = 240  // FIXME
  val C = 320  // FIXME
  val t = 50.to[Int16]  // FIXME
  val n = 12
  val lb_height = 7
  val lb_width = 640
  val sr_height = 7
  val sr_width = 7
  val hamming_threshold = 1.0.to[Q16_16]

  type Int16 = FixPt[TRUE,_16,_0]
  type UInt10 = FixPt[FALSE,_10,_0]
  type UInt8 = FixPt[FALSE,_8,_0]
  type UInt5 = FixPt[FALSE,_5,_0]
  type UInt6 = FixPt[FALSE,_6,_0]
  type UInt3 = FixPt[FALSE,_3,_0]
  type UInt1 = FixPt[FALSE,_1,_0]
  type Q16_16 = FixPt[TRUE,_16,_16]
  @struct case class Pixel24(b: UInt8, g: UInt8, r: UInt8)
  @struct case class Pixel16(b: UInt5, g: UInt6, r: UInt5)

  @struct case class Coordinate(x: Int16, y: Int16)
  @struct case class Match(x1: UInt10, y1: UInt10, x2: UInt10, y2: UInt10)

  @virtualize
  def detect_FAST(): Unit = {
    // val imgIn  = StreamIn[Pixel24](target.VideoCamera)  // TODO: change back to Pixel16
    val imgIn  = StreamIn[Pixel16](target.VideoCamera)
    // val imgIn2  = StreamIn[Pixel24](target.VideoCamera)  // TODO: change back to Pixel16
    // val imgOut = StreamOut[Pixel24](target.VGA)
    // val imgOut = BufferedOut[Pixel24](target.VGA)
    val imgOut = BufferedOut[Pixel16](target.VGA)
    // val imgOut = StreamOut[Pixel16](target.VGA)

    Accel {
      val sr_coord = RegFile[Coordinate](16)
      Pipe{
        sr_coord(0) = Coordinate(2.to[Int16], 0.to[Int16])
        sr_coord(1) = Coordinate(3.to[Int16], 0.to[Int16])
        sr_coord(2) = Coordinate(4.to[Int16], 0.to[Int16])
        sr_coord(3) = Coordinate(5.to[Int16], 1.to[Int16])
        sr_coord(4) = Coordinate(6.to[Int16], 2.to[Int16])
        sr_coord(5) = Coordinate(6.to[Int16], 3.to[Int16])
        sr_coord(6) = Coordinate(6.to[Int16], 4.to[Int16])
        sr_coord(7) = Coordinate(5.to[Int16], 5.to[Int16])
        sr_coord(8) = Coordinate(4.to[Int16], 6.to[Int16])
        sr_coord(9) = Coordinate(3.to[Int16], 6.to[Int16])
        sr_coord(10) = Coordinate(2.to[Int16], 6.to[Int16])
        sr_coord(11) = Coordinate(1.to[Int16], 5.to[Int16])
        sr_coord(12) = Coordinate(0.to[Int16], 4.to[Int16])
        sr_coord(13) = Coordinate(0.to[Int16], 3.to[Int16])
        sr_coord(14) = Coordinate(0.to[Int16], 2.to[Int16])
        sr_coord(15) = Coordinate(1.to[Int16], 1.to[Int16])
      }

      val descriptor_coord_1 = RegFile[Coordinate](16)
      Pipe{
        descriptor_coord_1(0) = Coordinate(0.to[Int16], 0.to[Int16])
        descriptor_coord_1(1) = Coordinate(1.to[Int16], 0.to[Int16])
        descriptor_coord_1(2) = Coordinate(2.to[Int16], 0.to[Int16])
        descriptor_coord_1(3) = Coordinate(3.to[Int16], 0.to[Int16])
        descriptor_coord_1(4) = Coordinate(4.to[Int16], 0.to[Int16])
        descriptor_coord_1(5) = Coordinate(5.to[Int16], 0.to[Int16])
        descriptor_coord_1(6) = Coordinate(5.to[Int16], 0.to[Int16])

        descriptor_coord_1(7) = Coordinate(0.to[Int16], 0.to[Int16])
        descriptor_coord_1(8) = Coordinate(0.to[Int16], 1.to[Int16])
        descriptor_coord_1(9) = Coordinate(0.to[Int16], 2.to[Int16])
        descriptor_coord_1(10) = Coordinate(0.to[Int16], 3.to[Int16])
        descriptor_coord_1(11) = Coordinate(0.to[Int16], 4.to[Int16])
        descriptor_coord_1(12) = Coordinate(0.to[Int16], 5.to[Int16])
        descriptor_coord_1(13) = Coordinate(0.to[Int16], 6.to[Int16])

        descriptor_coord_1(14) = Coordinate(0.to[Int16], 0.to[Int16])
        descriptor_coord_1(15) = Coordinate(0.to[Int16], 6.to[Int16])
      }

      val descriptor_coord_2 = RegFile[Coordinate](16)
      Pipe{
        descriptor_coord_2(0) = Coordinate(0.to[Int16], 6.to[Int16])
        descriptor_coord_2(1) = Coordinate(1.to[Int16], 6.to[Int16])
        descriptor_coord_2(2) = Coordinate(2.to[Int16], 6.to[Int16])
        descriptor_coord_2(3) = Coordinate(3.to[Int16], 6.to[Int16])
        descriptor_coord_2(4) = Coordinate(4.to[Int16], 6.to[Int16])
        descriptor_coord_2(5) = Coordinate(5.to[Int16], 6.to[Int16])
        descriptor_coord_2(6) = Coordinate(5.to[Int16], 6.to[Int16])

        descriptor_coord_2(7) = Coordinate(6.to[Int16], 0.to[Int16])
        descriptor_coord_2(8) = Coordinate(6.to[Int16], 1.to[Int16])
        descriptor_coord_2(9) = Coordinate(6.to[Int16], 2.to[Int16])
        descriptor_coord_2(10) = Coordinate(6.to[Int16], 3.to[Int16])
        descriptor_coord_2(11) = Coordinate(6.to[Int16], 4.to[Int16])
        descriptor_coord_2(12) = Coordinate(6.to[Int16], 5.to[Int16])
        descriptor_coord_2(13) = Coordinate(6.to[Int16], 6.to[Int16])

        descriptor_coord_2(14) = Coordinate(6.to[Int16], 0.to[Int16])
        descriptor_coord_2(15) = Coordinate(6.to[Int16], 6.to[Int16])
      }

      val sr = RegFile[Int16](sr_height, sr_width)
      val fifoIn = FIFO[Int16](C)
      // val fifoOut = FIFO[Int16](C)
      val lb = LineBuffer[Int16](lb_height, C)

      // val fifoDescriptor1 = FIFO[UInt1](3200)
      // val fifoDescriptor2 = FIFO[UInt1](3200)
      val fifo_prev_descriptors = FIFO[UInt1](3200)
      val fifo_curr_descriptors = FIFO[UInt1](3200)
      val fifo_prev_coord = FIFO[Coordinate](200)
      val fifo_curr_coord = FIFO[Coordinate](200)
      // val matches = FIFO[Match](200)
      val matches = SRAM[Match](300)
      val num_matches = Reg[Int](0)

      val numDescriptors = Reg[Int16](0)
      val numDescriptorsPrev = Reg[Int16](0)

      // val lastDescriptor = RegFile[UInt1](16)

      // val row = 0
      // val col = 0

      val curr = Reg[Int16](0)
      val is_feature = Reg[Int16](0)
      val running_count = Reg[Int16](1)
      val frame_counter = Reg[UInt1](0)
      val is_first_frame = Reg[UInt1](1)
      val hamming_distance = Reg[Int16](0)
      val min_hamming_distance = Reg[Int16](16)
      val second_min_hamming_distance = Reg[Int16](16)
      val best_match_coord = Reg[Coordinate]

      // for calculating affine transform
      val prev_points = SRAM[Q16_16](3, 3)
      // val curr_points = SRAM[UInt10](2, 3)
      val curr_points = SRAM[Q16_16](3, 3)
      // val prev_points_inv = SRAM[UInt10](3, 3)
      val curr_points_inv = SRAM[Q16_16](3, 3)
      val M = SRAM[Q16_16](2, 3)
      val best_geo_distance = Reg[Q16_16](10000)
      val cumulative_M = SRAM[Q16_16](2, 3)
      Sequential {
        cumulative_M(0, 0) = 1.to[Q16_16]
        cumulative_M(0, 1) = 0.to[Q16_16]
        cumulative_M(0, 2) = 0.to[Q16_16]
        cumulative_M(1, 0) = 0.to[Q16_16]
        cumulative_M(1, 1) = 1.to[Q16_16]
        cumulative_M(1, 2) = 0.to[Q16_16]
      }

      // frame_counter := 0
      frame_counter.reset
      is_first_frame.reset // 1

      // println("Start of stream")

      Stream(*) { _ =>
        // val pixel = imgIn.value()
        // // TODO: fix this if using Pixel16 as input
        // val grayscale_pixel = (pixel.b.to[Int16]*8 + pixel.g.to[Int16]*4 + pixel.r.to[Int16]*8) / 3
        // // println(grayscale_pixel)
        // fifoIn.enq(grayscale_pixel)

        Foreach(0 until R){ r => 
          Sequential.Foreach(0 until C) { _ => 
            Sequential {
              val pixel = imgIn.value()
              val grayscale_pixel = (pixel.b.to[Int16]*8 + pixel.g.to[Int16]*4 + pixel.r.to[Int16]*8) / 3
              fifoIn.enq(grayscale_pixel)
              lb.enq(grayscale_pixel)
            } 
          }
          Sequential.Foreach(0 until C) { c =>
            Sequential {
              // println("loop " + r + " " + c)
              val grayscale_pixel = fifoIn.deq()
              
              Pipe {
                Foreach(0 until sr_height){ i =>
                  sr(i, *) <<= lb(i, c)
                }

                // println("row " + r + " col " + c + " grayscale " + grayscale_pixel)

                val ring_values = RegFile[Int16](32)

                Sequential.Foreach(0 until 16){ i =>
                  val ring_pixel = sr_coord(i)
                  val ring_pixel_val = sr(ring_pixel.x.to[Index], ring_pixel.y.to[Index])
                  Sequential {
                    Pipe{ring_values(i) = 0}
                    Pipe{ring_values(i+16) = 0}
                    if (ring_pixel_val < (grayscale_pixel - t)) {Pipe{
                        Pipe{ring_values(i) = -1}
                        Pipe{ring_values(i+16) = -1}
                    }}
                    if (ring_pixel_val > (grayscale_pixel + t)) {Pipe{
                        Pipe{ring_values(i) = 1}
                        Pipe{ring_values(i+16) = 1}
                    }}
                  }
                }

                // Figure out if 12 contiguous values below or above threshold
                running_count.reset  // 1
                curr := ring_values(0)
                is_feature.reset  // 0
                Sequential.Foreach(1 until 32){ i =>
                  if (ring_values(i) == curr.value) {
                      running_count := running_count.value + 1
                  } else {Pipe{
                      running_count.reset  // 1
                      curr := ring_values(i)
                  }}

                  if (running_count.value == 12.to[Int16] && curr.value != 0.to[Int16]) {
                      is_feature := 1
                      // println(r + " " + c)
                  }
                }

                if ((is_feature.value == 1.to[Int16]) && (r > 4.to[Index]) && numDescriptors.value < 200) {Pipe{
                                  // println("add feature")
                                  val brief_descriptor = RegFile[UInt1](16)
                                  Pipe {
                                    Sequential.Foreach(0 until 16){ i =>
                                      Sequential {
                                        val pt1 = descriptor_coord_1(i)
                                        val pt2 = descriptor_coord_2(i)
                                        brief_descriptor(i) = 0.to[UInt1]
                                        if (sr(pt1.x.to[Index], pt1.y.to[Index]) > sr(pt2.x.to[Index], pt2.y.to[Index])) {
                                          brief_descriptor(i) = 1.to[UInt1]
                                        }
                                      }
                                    }
                                    val boolreg = Reg[Bool](false)
                                    boolreg := is_first_frame.value.to[Int] == 0 && num_matches.value < 300
                                    if (boolreg) {Pipe{
                                        // println(num_matches.value < 300)
                                        // match with descriptors from previous frame
                                        min_hamming_distance.reset  // 16
                                        second_min_hamming_distance.reset  // 16
                                        // println("finding match " + numDescriptorsPrev.value)
                                        Sequential.Foreach(0 until numDescriptorsPrev.value.to[Index]){ i =>
                                          hamming_distance.reset  // 0
                  
                                          Sequential.Foreach(0 until 16){ j =>
                                            val bit = fifo_prev_descriptors.deq()
                                            if (bit != brief_descriptor(j)) {
                                              hamming_distance := hamming_distance.value + 1
                                            }
                                            fifo_prev_descriptors.enq(bit)
                                          }
                                          if (hamming_distance.value < min_hamming_distance.value) {Pipe{
                                            second_min_hamming_distance := min_hamming_distance.value
                                            min_hamming_distance := hamming_distance.value
                                            best_match_coord := fifo_prev_coord.deq()
                                            fifo_prev_coord.enq(best_match_coord.value)
                                          }} else if (hamming_distance.value < second_min_hamming_distance.value) {Pipe{
                                            second_min_hamming_distance := hamming_distance.value
                                            fifo_prev_coord.enq(fifo_prev_coord.deq())
                                          }} else {
                                            fifo_prev_coord.enq(fifo_prev_coord.deq())
                                          }
                                        }
                                        // TODO: add distance and orientation checks from IEEE paper
                                        if (min_hamming_distance.value.to[Q16_16] / second_min_hamming_distance.value.to[Q16_16] <= hamming_threshold && num_matches.value < 300) {Pipe{
                                          // it's a match
                                          // println(min_hamming_distance.value.to[Q16_16] + " " + second_min_hamming_distance.value.to[Q16_16])
                                          matches(num_matches.value) = mux[Match](num_matches.value < 300,
                                            Match(x1=best_match_coord.value.x.to[UInt10], y1=best_match_coord.value.y.to[UInt10], x2=c.to[UInt10], y2=r.to[UInt10]),
                                            Match(0.to[UInt10], 0.to[UInt10], 0.to[UInt10], 0.to[UInt10]))
                                          num_matches := mux[Int](num_matches.value < 300, num_matches.value + 1, num_matches.value)
                                          // matches.enq(Match(x1=best_match_coord.value.x.to[UInt10], y1=best_match_coord.value.y.to[UInt10], x2=c.to[UInt10], y2=r.to[UInt10]))
                                        }}
                                      }}
                                    Sequential.Foreach(0 until 16){ i =>
                                      fifo_curr_descriptors.enq(brief_descriptor(i))
                                    }
                                  }
                                  fifo_curr_coord.enq(Coordinate(c.to[Int16], r.to[Int16]))
                                  numDescriptors := numDescriptors.value + 1
                                  // println("does this never happen?")
                                }}
              }

              // // For debug purposes
              // fifoOut.enq(mux[Int16]((is_feature.value == 1.to[Int16]) && (r > 4.to[Index]), 255, grayscale_pixel))
              
              // end of frame updates
              if (r == R-1 && c == C-1) {Pipe{
                    // println("end of frame updates")
                    frame_counter := mux[UInt1](frame_counter == 0.to[UInt1], 1, 0)
                    numDescriptorsPrev := numDescriptors.value
                    numDescriptors.reset  // 0
    
                    Pipe {
                      // println(fifo_prev_descriptors.numel())
                      Sequential.Foreach(0 until fifo_prev_descriptors.numel()){ i =>
                        fifo_prev_descriptors.deq()
                      }
                      // println(fifo_curr_descriptors.numel())
                      Sequential.Foreach(0 until fifo_curr_descriptors.numel()){ i =>
                        val bit = fifo_curr_descriptors.deq()
                        fifo_prev_descriptors.enq(bit)
                      }
                      // println(fifo_prev_coord.numel())
                      Sequential.Foreach(0 until fifo_prev_coord.numel()){ _ =>
                        fifo_prev_coord.deq()
                      }
                      // println(fifo_curr_coord.numel())
                      Sequential.Foreach(0 until fifo_curr_coord.numel()){ _ =>
                        fifo_prev_coord.enq(fifo_curr_coord.deq())
                      }
                    }
    
                    // println(matches.numel())
    
                    // println("start matches")
                    if (is_first_frame.value.to[Int] == 0) {Pipe{
                      // println(matches.numel() / 3)
                      // println(num_matches.value / 3)
                      val third = num_matches.value / 3
                      best_geo_distance.reset  // 10000
                      // Foreach(0 until matches.numel() / 3){ i =>
                      
                      Pipe {
                        Sequential.Foreach(0 until num_matches.value / 3){ i =>
                          // println("3-match " + i)
                          Sequential {
                            // val match1 = matches.deq()
                            // val match2 = matches.deq()
                            // val match3 = matches.deq()
                            val match1 = matches(i)
                            val match2 = matches(i + third)
                            val match3 = matches(i + 2*third)
    
                            // Use Heron's formula to calculate the difference in areas between triangles
                            val shoelace_prev = 0.5.to[Q16_16] * ((match1.x1 - match3.x1)*(match2.y1 - match1.y1) -
                              (match1.x1 - match2.x1)*(match3.y1 - match1.y1)).to[Q16_16]
                            val area_prev = mux[Q16_16](shoelace_prev >= 0, shoelace_prev, -1 * shoelace_prev)
                            val shoelace_curr = 0.5.to[Q16_16] * ((match1.x2 - match3.x2)*(match2.y2 - match1.y2) -
                              (match1.x2 - match2.x2)*(match3.y2 - match1.y2)).to[Q16_16]
                            val area_curr = mux[Q16_16](shoelace_curr >= 0, shoelace_curr, -1 * shoelace_curr)
                            val area_diff = area_curr - area_prev
                            val geo_distance = mux[Q16_16](area_diff >= 0, area_diff, -1 * area_diff)
                            // println("geo_distance: " + geo_distance)
                            
                            if (geo_distance < best_geo_distance) {Pipe{
                              Pipe{curr_points(0, 0) = match1.x2.to[Q16_16]}
                              Pipe{curr_points(0, 1) = match2.x2.to[Q16_16]}
                              Pipe{curr_points(0, 2) = match3.x2.to[Q16_16]}
                              Pipe{curr_points(1, 0) = match1.y2.to[Q16_16]}
                              Pipe{curr_points(1, 1) = match2.y2.to[Q16_16]}
                              Pipe{curr_points(1, 2) = match3.y2.to[Q16_16]}
                              Pipe{curr_points(2, 0) = 1.to[Q16_16]}
                              Pipe{curr_points(2, 1) = 1.to[Q16_16]}
                              Pipe{curr_points(2, 2) = 1.to[Q16_16]}
    
                              // val det = prev_points(0, 0) * (prev_points(1, 1) * prev_points(2, 2) - prev_points(2, 1) * prev_points(1, 2)) -
                              //   prev_points(0, 1) * (prev_points(1, 0) * prev_points(2, 2) - prev_points(1, 2) * prev_points(2, 0)) +
                              //   prev_points(0, 2) * (prev_points(1, 0) * prev_points(2, 1) - prev_points(1, 1) * prev_points(2, 0))
                              val det = curr_points(0, 0) * (curr_points(1, 1) * curr_points(2, 2) - curr_points(2, 1) * curr_points(1, 2)) -
                                curr_points(0, 1) * (curr_points(1, 0) * curr_points(2, 2) - curr_points(1, 2) * curr_points(2, 0)) +
                                curr_points(0, 2) * (curr_points(1, 0) * curr_points(2, 1) - curr_points(1, 1) * curr_points(2, 0))
                              
                              // println(det)
                              // println("determinant is 0: " + (det.to[Int] == 0))
                              // println("before if statement")
                              if (det.to[Int] != 0) {Pipe{
                                best_geo_distance := geo_distance
                                // println("det " + det)
                                // println("det int " + det.to[Int])
    
                                val invdet = 1.0.to[Q16_16]/det.to[Q16_16]
                                // println("invdet " + invdet)
                                curr_points_inv(0, 0) = (curr_points(1, 1) * curr_points(2, 2) - curr_points(2, 1) * curr_points(1, 2)).to[Q16_16] * invdet;
                                curr_points_inv(0, 1) = (curr_points(0, 2) * curr_points(2, 1) - curr_points(0, 1) * curr_points(2, 2)).to[Q16_16] * invdet;
                                curr_points_inv(0, 2) = (curr_points(0, 1) * curr_points(1, 2) - curr_points(0, 2) * curr_points(1, 1)).to[Q16_16] * invdet;
                                curr_points_inv(1, 0) = (curr_points(1, 2) * curr_points(2, 0) - curr_points(1, 0) * curr_points(2, 2)).to[Q16_16] * invdet;
                                curr_points_inv(1, 1) = (curr_points(0, 0) * curr_points(2, 2) - curr_points(0, 2) * curr_points(2, 0)).to[Q16_16] * invdet;
                                curr_points_inv(1, 2) = (curr_points(1, 0) * curr_points(0, 2) - curr_points(0, 0) * curr_points(1, 2)).to[Q16_16] * invdet;
                                curr_points_inv(2, 0) = (curr_points(1, 0) * curr_points(2, 1) - curr_points(2, 0) * curr_points(1, 1)).to[Q16_16] * invdet;
                                curr_points_inv(2, 1) = (curr_points(2, 0) * curr_points(0, 1) - curr_points(0, 0) * curr_points(2, 1)).to[Q16_16] * invdet;
                                curr_points_inv(2, 2) = (curr_points(0, 0) * curr_points(1, 1) - curr_points(1, 0) * curr_points(0, 1)).to[Q16_16] * invdet;
    
                                prev_points(0, 0) = match1.x1.to[Q16_16]
                                prev_points(0, 1) = match2.x1.to[Q16_16]
                                prev_points(0, 2) = match3.x1.to[Q16_16]
                                prev_points(1, 0) = match1.y1.to[Q16_16]
                                prev_points(1, 1) = match2.y1.to[Q16_16]
                                prev_points(1, 2) = match3.y1.to[Q16_16]
                                prev_points(2, 0) = 1.to[Q16_16]
                                prev_points(2, 1) = 1.to[Q16_16]
                                prev_points(2, 2) = 1.to[Q16_16]
    
                                // M is the affine transfrom from curr_points to prev_points
                                M(0, 0) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,0) + prev_points(0,1)*curr_points_inv(1,0) + prev_points(0,2)*curr_points_inv(2,0), 1)
                                M(0, 1) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,1) + prev_points(0,1)*curr_points_inv(1,1) + prev_points(0,2)*curr_points_inv(2,1), 0)
                                M(0, 2) = mux[Q16_16](r == R-1 && c == C-1, prev_points(0,0)*curr_points_inv(0,2) + prev_points(0,1)*curr_points_inv(1,2) + prev_points(0,2)*curr_points_inv(2,2), 0)
                                M(1, 0) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,0) + prev_points(1,1)*curr_points_inv(1,0) + prev_points(1,2)*curr_points_inv(2,0), 0)
                                M(1, 1) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,1) + prev_points(1,1)*curr_points_inv(1,1) + prev_points(1,2)*curr_points_inv(2,1), 1)
                                M(1, 2) = mux[Q16_16](r == R-1 && c == C-1, prev_points(1,0)*curr_points_inv(0,2) + prev_points(1,1)*curr_points_inv(1,2) + prev_points(1,2)*curr_points_inv(2,2), 0)
                                // println(M(0,0))
                                // println(M(0,1))
                                // println(M(0,2))
                                // println(M(1,0))
                                // println(M(1,1))
                                // println(M(1,2))
                              }}
                            }}
                          }
                        }
    
                        // compute new cumulative transform: new_cum=M*cum
                        val t00 = M(0,0)*cumulative_M(0,0) + M(0,1)*cumulative_M(1,0)
                        val t01 = M(0,0)*cumulative_M(0,1) + M(0,1)*cumulative_M(1,1)
                        val t02 = M(0,0)*cumulative_M(0,2) + M(0,1)*cumulative_M(1,2) + M(0,2)
                        val t10 = M(1,0)*cumulative_M(0,0) + M(1,1)*cumulative_M(1,0)
                        val t11 = M(1,0)*cumulative_M(0,1) + M(1,1)*cumulative_M(1,1)
                        val t12 = M(1,0)*cumulative_M(0,2) + M(1,1)*cumulative_M(1,2) + M(1,2)
    
                        // cumulative_M is the affine transform from the current frame to the first frame
                        Sequential {
                          cumulative_M(0,0) = t00
                          cumulative_M(0,1) = t01
                          cumulative_M(0,2) = t02
                          cumulative_M(1,0) = t10
                          cumulative_M(1,1) = t11
                          cumulative_M(1,2) = t12
                          // cumulative_M(0,0) = 1
                          // cumulative_M(0,1) = 1
                          // cumulative_M(0,2) = 1
                          // cumulative_M(1,0) = 1
                          // cumulative_M(1,1) = 1
                          // cumulative_M(1,2) = 1
                        }
                      }
    
                      // println(cumulative_M(0,0))
                      // println(cumulative_M(0,1))
                      // println(cumulative_M(0,2))
                      // println(cumulative_M(1,0))
                      // println(cumulative_M(1,1))
                      // println(cumulative_M(1,2))
                    }}
    
                    num_matches.reset
                    if (is_first_frame.value.to[Int] == 1) {
                      is_first_frame := 0
                    }
                  }}

              // val pixel_value = fifoOut.deq()
              val t_c = cumulative_M(0,0).to[Int]*c + cumulative_M(0,1).to[Int]*r + cumulative_M(0,2).to[Int]
              val t_r = cumulative_M(1,0).to[Int]*c + cumulative_M(1,1).to[Int]*r + cumulative_M(1,2).to[Int]
              if (t_c >= 0 && t_c < 320 && t_r >= 0 && t_r < 240) {
                imgOut(t_r, t_c) = Pixel16(grayscale_pixel(7::3).as[UInt5],
                  grayscale_pixel(7::2).as[UInt6],
                  grayscale_pixel(7::3).as[UInt5])
              }

              // imgOut(r, c) = Pixel16(pixel_value(7::3).as[UInt5],
              //   pixel_value(7::2).as[UInt6],
              //   pixel_value(7::3).as[UInt5])
            }
          }
        }
      }
      ()
    }
  }

  @virtualize
  def main() {
    detect_FAST()
  }
}
