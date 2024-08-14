/* Copyright (c) 2024 Vector Informatik GmbH
 * SPDX-License-Identifier: MIT
 */

import com.vector.cfg.automation.model.ecuc.microsar.dem.Dem
import com.vector.cfg.automation.model.ecuc.microsar.dem.demconfigset.DemConfigSet
import com.vector.cfg.automation.model.ecuc.microsar.dem.demconfigset.demeventparameter.DemEventParameter

import static com.vector.cfg.automation.api.ScriptApi.*

//daVinci enables the IDE code completion support
daVinci {
    scriptDescription "scripttasks for update workflow topics"

    /*
     * Task: UpdateExistingProject
     * Type: DV_ON_SUCCESSFUL_UPDATE_WORKFLOW
     * -------------------------------------------------------------------------------------------------------
     * Task will be executed after a successful project update
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("UpdateExistingProject", DV_ON_SUCCESSFUL_UPDATE_WORKFLOW) {
        taskDescription 'Set the value of the parameter DemEventCreateInfoPort to false'

        taskHelp '''Task will be executed after a successful project update and will set the value of the parameter DemEventCreateInfoPort'''
        code {

            transaction{
                Dem demModel = bswmdModel(Dem.DefRef).single

                DemConfigSet demConigSet = demModel.demConfigSetOrCreate
                List<DemEventParameter> demEventParam = demConigSet.demEventParameter

                for (def param in demEventParam)
                {
                    param.demEventCreateInfoPortOrCreate.setValue(false)
                }

                println(demEventParam)
            }
            saveProject()
        }
    }
}