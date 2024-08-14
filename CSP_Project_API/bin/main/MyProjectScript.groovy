/* Copyright (c) 2024 Vector Informatik GmbH
 * SPDX-License-Identifier: MIT
 */

import com.vector.cfg.automation.model.ecuc.microsar.ecuc.EcuC
import com.vector.cfg.automation.model.ecuc.microsar.ecuc.ecucgeneral.EcucGeneral
import com.vector.cfg.automation.model.ecuc.microsar.ecuc.ecucgeneral.cputype.CPUType
import com.vector.cfg.automation.model.ecuc.microsar.ecuc.ecucgeneral.cputype.ECPUType
import com.vector.cfg.automation.model.ecuc.microsar.ecuc.ecuchardware.ecuccoredefinition.EcucCoreDefinition

import static com.vector.cfg.automation.api.ScriptApi.daVinci

//daVinci enables the IDE code completion support
daVinci {
    scriptDescription "Reads containter/parameter with the BswmdModel"

    /* 
     * Task: ReadTheActiveEcuC
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Reads containter/parameter with the BswmdModel
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("ReadTheActiveEcuCTask", DV_PROJECT) {
        taskDescription 'Reads container/parameter with the BswmdModel'

        taskHelp '''ReadTheActiveEcuC script task
    The task reads with the bswmdModel the parameter CPUType from the EcucGeneral container which is included 
	in the EcuC modul configuration (in the Basic Editor). 
    '''

        code {
            // Gets the ecuc module configuration
            EcuC ecuc = bswmdModel(EcuC.DefRef).single
            // Gets the EcucGeneral container
            EcucGeneral ecucGeneral = ecuc.ecucGeneral

            // Gets an enum parameter of this container
            CPUType cpuType = ecucGeneral.CPUType
            if (cpuType.value == ECPUType.CPU32Bit) {
                scriptLogger.info("CPUType (CPU32Bit) is correct. Now do something new")
            }
        }
    }

    /*
     * Task: WriteTheActiveEcuCTask
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Reads containter/parameter with the BswmdModel and creates a new EcuCore by two different methods
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("WriteTheActiveEcuCTask", DV_PROJECT) {
        taskDescription 'Creates container/parameter with the BswmdModel'

        taskHelp '''WriteTheActiveEcuC script task
        The task creates with the bswmdModel three new EcucCoreDefinition container in the EcuCHardware container which
        is included in the EcuC modul configuration (in the Basic Editor) and sets the parameter values of them.
        '''

        code {
            transaction {
                // Gets the first found ecuc module instance
                EcuC ecuc = bswmdModel(EcuC.DefRef).single

                // Gets the EcucGeneral container or create one if it is missing
                EcucGeneral ecucGeneral = ecuc.ecucGeneralOrCreate

                // Gets an boolean parameter of this container or create one if it is missing
                def ecusDummyFunction = ecucGeneral.dummyFunction

                // Sets the parameter value to true
                ecusDummyFunction.value = true

                // Gets the EcucCoreDefinition list (creates ecucHardware if it is missing)
                def ecucCoreDefinitions = ecuc.ecucHardwareOrCreate.ecucCoreDefinition

                // Adds two EcucCores
                EcucCoreDefinition core0 = ecucCoreDefinitions.createAndAdd("EcucCore0")
                EcucCoreDefinition core1 = ecucCoreDefinitions.createAndAdd("EcucCore1")

                if (ecucCoreDefinitions.exists("EcucCore0")) {
                    // Sets EcucCoreId to 0
                    ecucCoreDefinitions.byName("EcucCore0").ecucCoreId.value = 0
                    scriptLogger.info("ecucCoreId of EcucCore0 were set to 0")
                }

                // Creates a new EcucCore by method 'byNameOrCreate'
                EcucCoreDefinition core2 = ecucCoreDefinitions.byNameOrCreate("EcucCore2")
                if (ecucCoreDefinitions.exists("EcucCore2")) {
                    // Sets EcucCoreId to 3
                    core2.ecucCoreId.value = 3
                    scriptLogger.info("ecucCoreId of EcucCore2 were set to 3")
                }
            }
        }
    }
}