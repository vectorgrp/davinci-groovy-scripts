import com.vector.cfg.model.mdf.model.autosar.base.MIARPackage

import static com.vector.cfg.automation.api.ScriptApi.daVinci

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
        taskDescription 'Task just prints "Update successful" in the console'

        taskHelp '''Task will be executed after a successful project update and then just prints text in the console
        '''
        code {
            println "Update successful"
        }
    }

    /*
     * Task: TestUpdateSettings
     * Type: DV_APPLICATION
     * -------------------------------------------------------------------------------------------------------
     * Task executes a python script during update workflow
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("TestUpdateSettings", DV_APPLICATION) {
        taskDescription 'Task executes a python script during update workflow'

        taskHelp '''Task enables the support for python then executes a script to modify SystemExtract and disables
		python support after
        '''
        code {
            def dpaProject = "D:/PES_8/DaVinci/CBD2201090/D01/external/Demo/Demo.dpa"
            workflow.updateProject(dpaProject) {
                scriptLogger.info "the dpa project (" + dpaProject + ") will be updated"

                updateSettings {

                    updateMode = ECUC_ONLY

                    def sysDescEnabled = true
                    def sysDescStdEnabled = true

                    // Executes the given scripts
                    // If no API call gets specified, the .DPA file content will be	used.
                    def pythonScript = "D:/PES_8/FOSS/foss_scripts/CSP_Workflow_API/input/helloWorld.py"
                    executeSysDescModificationScript(pythonScript)
                    scriptLogger.info "the python script (" + pythonScript + ") were executed"

                    // Disables all System Description Modification scripts
                    disableSysDescModificationScript()
                }
            }
        }
    }

    /*
     * Task: ArPackageNames
     * Type: DV_ON_FILE_PREPROCESSING_RESULT
     * -------------------------------------------------------------------------------------------------------
     * Task prints the asrPath of the arPackages after file_preprocessing is finished
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("ArPackageNames", DV_ON_FILE_PREPROCESSING_RESULT) {
        taskDescription 'Task prints the asrPath of the arPackages after file_preprocessing is finished'

        taskHelp '''Task prints some text and the asrPath of the arPackages from the mdfmodel after file_preprocessing is finished
        '''

        // The type DV_ON_FILE_PREPROCESSING_INPUT_FILE allows the
        // script to modify every Autosar input ﬁle (.arxml) which is used in the update workﬂow except
        // of ProjectStandardConﬁguration ﬁles.

        taskHelp '''Simple Application task to show all the existent ArPackages in the communication extract 
        to console. Logs the message "Logging message from the script" to the scriptLogger.
        '''

        code {

            println "HelloApplication"
            scriptLogger.info "Logging message from the script"

            def allArpackages = mdfModel(MIARPackage)

            for (arpackage in allArpackages) {
                println arpackage.asrPath
                scriptLogger.info(arpackage.asrPath)
            }

            // The property OutputFolder is the folder of the generated artifacts
            scriptLogger.info "the script was executed"
        }
    }
}