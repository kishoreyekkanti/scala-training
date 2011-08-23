/*
 * Copyright 2011 Typesafe Inc.
 * 
 * This work is based on the original contribution of WeigleWilczek.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalatrain

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(info != null, "info must not be null!")
  require(schedule != null, "schedule must not be null!")
  require(schedule.size >= 2, "schedule must have at least two stations!")
  require(Time.isIncreasing(schedule map { _._1 }))

  val stations: Seq[Station] = schedule map { _._2 }

  val backToBackStations: Seq[(Station, Station)] = stations.init zip stations.tail

  val departureTimes: Map[Station, Time] = schedule map { _.swap } toMap
}

case class Station(name: String) {
  require(name != null, "name must not be null!")
}

sealed abstract class TrainInfo {
  def number: String
}

case class Ice(number: String, hasWifi: Boolean = false) extends TrainInfo

case class Re(number: String) extends TrainInfo

case class Brb(number: String) extends TrainInfo
