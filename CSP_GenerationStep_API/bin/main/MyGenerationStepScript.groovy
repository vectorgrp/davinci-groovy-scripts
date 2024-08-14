/* Copyright (c) 2024 Vector Informatik GmbH
 * SPDX-License-Identifier: MIT
 */

import com.vector.cfg.automation.model.ecuc.microsar.ecuc.EcuC
import static com.vector.cfg.automation.api.ScriptApi.*
import com.vector.cfg.consistency.EValidationSeverityType

//daVinci enables the IDE code completion support
daVinci {
    scriptDescription "GenerationStep script tasks, which are executed during/after generation"
    
    /* 
     * Task: GenStepTask
     * Type: DV_GENERATION_STEP
     * -------------------------------------------------------------------------------------------------------
     * Executes the script task code as generation step and report a validation result
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("GenStepTas_Step", DV_GENERATION_STEP){
        taskDescription "GenerationStep task"
        
        code{ phase, genType, resultSinkFromTask ->
            scriptLogger.info "Generation Step started"

            if(phase.calculation){
                // Execute code before / after calculation
                transaction {
                   // Modify the Model in the calculation phase
                }
            }
        
           if(phase.validation){
                // Execute code before / after validation
                 
                // Report a validation result 
                validation{
                    resultCreation{
                        // The ValidationResultId group multiple results
                        def valId = createValidationResultIdForScriptTask(
                                /* ID */ 1234,
                                /* Description */ "Summary of the ValidationResultId",
                                /* Severity */ EValidationSeverityType.ERROR
                                )

                        // Create a new resultBuilder
                        def builder = newResultBuilder(valId, "Description of the Result")

                        // You can add multiple elements are error objects to mark them
                        builder.addErrorObject(bswmdModel(EcuC.DefRef).single)
                        // Add more calls when needed

                        // Create the result from the builder
                        def valResult = builder.buildResult()

                        // Report the result
                        resultSinkFromTask.reportValidationResult(valResult)
                    }
                }
            }

            if(phase.generation){
                // Execute code before / after generation

                generation {
                    generate()
                }
            }
        }
    }

    /*
     * Task: GenStepTask
     * Type: DV_ON_GENERATION_END
     * -------------------------------------------------------------------------------------------------------
     * Executes the script task code after generation was finished
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("GenEndTask_End", DV_ON_GENERATION_END){
        taskDescription "The task is automatically executed at generation end and log the result and the generator in the scriptlogger"

        code{ generationResult, generators ->

            scriptLogger.info "Process result was: $generationResult"
            scriptLogger.info "Executed Generators: $generators"
        }
    }
}
