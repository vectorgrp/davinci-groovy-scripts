import static com.vector.cfg.automation.api.ScriptApi.daVinci

//daVinci enables the IDE code completion support
daVinci {

    /* 
     * Task: SimpleApplTask
     * Type: DV_EDITOR_SELECTION
     * -------------------------------------------------------------------------------------------------------
     * Prints "HelloApplication" and the scriptTaskArguments to console and logs a message to the scriptLogger
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SimpleApplTask", DV_EDITOR_SELECTION) {
        taskDescription 'Prints "HelloApplication" and the scriptTaskArguments to console and logs a message to the scriptLogger'

        taskHelp '''Simple Application task to show the creation of an UI task. It can only be executed with a selected element in GUI.
        The task will print  "HelloApplication" and the scriptTaskArguments to console.
        Logs the message "Logging message from the script" to the scriptLogger.
        '''

        code {

            println scriptTaskArguments
            println "HelloApplication"
            scriptLogger.info "Logging message from the script"
        }
    }

    /*
     * Task: SimpleApplTask_Multi
     * Type: DV_EDITOR_MULTI_SELECTION
     * -------------------------------------------------------------------------------------------------------
     * Prints "HelloApplication" and the scriptTaskArguments to console and logs a message to the scriptLogger
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SimpleApplTask_Multi", DV_EDITOR_MULTI_SELECTION) {
        taskDescription 'Prints "HelloApplication" and the scriptTaskArguments  to console and logs a message to the scriptLogger'

        taskHelp '''Simple Application task to show the creation of an UI task. It can only be executed with one or more selected element(s) in GUI.
        The task will print  "HelloApplication" and the scriptTaskArguments to console.
        Logs the message "Logging message from the script" to the scriptLogger.
        '''

        code { args ->

            for (def a in args) {
                println()
            }
            println scriptTaskArguments
            scriptLogger.info "Logging message from the script"
        }
    }

}