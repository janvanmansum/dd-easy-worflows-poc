/**
 * Copyright (C) 2020 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.dd

import java.text.DateFormat
import java.util.{ Calendar, TimeZone }

import org.json4s.DefaultFormats

import scala.concurrent.ExecutionContext

package object wf {

  implicit val formats = DefaultFormats

  implicit val context = ExecutionContext.global

  def currentTimestamp: String = {
    val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val f = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
    f.format(c.getTimeInMillis)
  }
}
