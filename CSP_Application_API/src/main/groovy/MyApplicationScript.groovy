import groovy.io.FileType

import static com.vector.cfg.automation.api.ScriptApi.daVinci

//daVinci enables the IDE code completion support
daVinci {
    scriptDescription "Script collection for different scripts with tasktype DV_APPLICATION"

    /* 
     * Task: CreateNewProject
     * Type: DV_APPLICATION
     * -------------------------------------------------------------------------------------------------------
     * Creates a new project and activates Det, Dio and EcuC modules
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("CreateNewProject", DV_APPLICATION) {
        taskDescription 'Creates a new Dpa Project in the given directory and activates Det, Dio and EcuC modules'

        taskHelp '''CreateNewProject script task
        The task creates a project under "/CreateProject" in the given project folder with the name "newProject".
        Activates the modules /MICROSAR/Det, /MICROSAR/Dio and /MICROSAR/EcuC
        And saves the project to disk
        '''

        code {
            def workDir = "D:/PES_8/FOSS/foss_scripts/Projects/by_CSP_Application_API"

            deleteProjectFolder("$workDir/CreateProject")

            def projPath = paths.resolvePath("$workDir/CreateProject")

            // Create a new Project
            def theProject = projects.createProject {
                projectName 'newProject'
                projectFolder projPath

                general {
                    description 'The Project Description'
                    createStartMenuEntries false
                }
                daVinciDeveloper {
                    createDaVinciDeveloperWorkspace false
                }
            }
            //Now load the new project
            theProject.openProject {
                //Do something the opened project
                transaction {
                    operations.activateModuleConfiguration(sipDefRef.Det)
                    operations.activateModuleConfiguration(sipDefRef.Dio)
                    operations.activateModuleConfiguration(sipDefRef.EcuC)
                }


                saveProject()
            }
            scriptLogger.info("Project created and modules added and saved to: $theProject")
        }
    }

    /*
    * Task: UpdateProjectTask
    * Type: DV_APPLICATION
    * -------------------------------------------------------------------------------------------------------
    * Creates a new project and updates it with a communication extract
    * -------------------------------------------------------------------------------------------------------
    */
    scriptTask("CreateAndUpdateProjectTask", DV_APPLICATION) {
        taskDescription 'Creates a new Dpa Project and updates it with a communication extract'

        taskHelp '''CreateNewProject script task
        The task creates a project under "/CreateAndUpdateProject" in the given project folder with the name "updatedProject".
        Adds the communication extract 'e_Rx_simple_AR4.arxml'.
        executes an update.
        '''

        code {
            def workDir = "D:/PES_8/FOSS/foss_scripts/Projects/by_CSP_Application_API"

            deleteProjectFolder("$workDir/CreateAndUpdateProject")

            def projPath = paths.resolvePath("$workDir/CreateAndUpdateProject")

            // Create a new Project
            def theProject = projects.createProject {
                projectName 'updatedProject'
                projectFolder projPath

                general {
                    createStartMenuEntries false
                }

                daVinciDeveloper {
                    createDaVinciDeveloperWorkspace false
                }
            }

            // Update the new Project
            def extractPath = paths.resolveScriptPath("$workDir/SupportFiles/e_Rx_simple_AR4.arxml")
            workflow {
                updateProject(theProject) {
                    fileSet() {
                        inputFiles {
                            createInputFile(extractPath.asPersistablePath()) {}
                        }
                    }
                }
            }
            scriptLogger.info("Project created and update, location: $theProject")
        }
    }
}

// Function to delete files in folder (if path exists), so the tasks can be executed more than once
static void deleteProjectFolder(String path) {
    def directory = new File(path)

    if (directory.exists()) {
        directory.eachFile(FileType.FILES) { file -> file.delete() }
    }
}