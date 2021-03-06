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
package nl.knaw.dans.dd.prepub

import java.util.UUID

import nl.knaw.dans.dd.prepub.dataverse.json._

import scala.collection.mutable.ListBuffer

class DansDataVaultMetadataBlockMapper {

  case class EditField(typeName: String, value: String)
  case class EditFields(fields: List[EditField])

  val vaultFields = new ListBuffer[PrimitiveFieldSingleValue]

  // TODO: Base on info from workflow
  def createDataVaultFields(workFlowVariables: WorkFlowVariables,
                            b: Option[String],
                            n: Option[String],
                            o: Option[String],
                            ov: Option[String],
                            st: Option[String]): EditFields = {
    val fields = ListBuffer[EditField]()
    fields.append(EditField("dansDataversePid", workFlowVariables.pid))
    fields.append(EditField("dansDataversePidVersion", s"${ workFlowVariables.majorVersion }.${ workFlowVariables.minorVersion }"))

    // TODO: How find out if a bagId that is found here is carried over from the previous version or a bagId minted by the SWORD service?
    //       Compare with bagId of previous version if exists
    //       if no previous version: filled in bagId means SWORD filled it in
    //       if previous version: new bagId means SWORD filled it in, the same bagId as in previous version means UI created new draft.
    fields.append(EditField("dansBagId", b.getOrElse(mintBagId())))

    fields.append(EditField("dansNbn", n.getOrElse(mintUrnNbn())))
    o.foreach(b => fields.append(EditField("dansOtherId", b)))
    ov.foreach(b => fields.append(EditField("dansOtherIdVersion", b)))
    st.foreach(b => fields.append(EditField("dansSwordToken", b)))
    EditFields(fields.toList)
  }

  def mintUrnNbn(): String = {
    "testURN:NBN"
  }

  def mintBagId(): String = {
    s"urn:uuid${ UUID.randomUUID().toString }"
  }
}
