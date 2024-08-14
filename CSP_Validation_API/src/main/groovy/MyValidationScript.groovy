/* Copyright (c) 2024 Vector Informatik GmbH
 * SPDX-License-Identifier: MIT
 */

import com.vector.cfg.automation.model.ecuc.microsar.ecuc.EcuC
import com.vector.cfg.consistency.EValidationSeverityType
import com.vector.cfg.util.activity.execresult.EExecutionResult

import static com.vector.cfg.automation.api.ScriptApi.daVinci

//daVinci enables the IDE code completion support
daVinci {

    /*
     * Task: CheckValidationResults_filterByOriginId
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Get all validationResults, check the size and filter results by a given ID
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("CheckValidationResults_filterByOriginId", DV_PROJECT) {
        taskDescription 'Get all validationResults, check the size and filter results by a given ID'

        taskHelp '''This will not work with every data, then please adapt the example to an ID which is
        contained in your CFG5 project 
        '''

        code {
            validation {
                // access all validation-results
                def allResults = validationResults
                assert allResults.size() > 3

                // filter based on methods of IValidationResultUI e.g. isId()
                def id = ["OS", 2140]
                def tp12Results = validationResults.filter { it.isId(*id) }
                assert tp12Results.size() == 8

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    /*
     * Task: SolveResultSolvingAction
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Task get the validationResults, filter them. First solve them with preferredSolvingAction and then with a
     * special solving action
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SolveResultSolvingAction", DV_PROJECT) {
        taskDescription 'Task get the validationResults, filter them. First solve them with preferredSolvingAction ' +
                'and then with a special solving action'

        taskHelp '''This will not work with every data, then please adapt the example to an ID which is
        contained in your CFG5 project
        '''

        code {
            validation {
                def id = ["VTTFls", 54013]
                def results = validationResults.filter { it.isId(*id) }
                assert results.size() == 1

                solver.solveAllWithPreferredSolvingAction()

                solver.solve { result { isId(*id) }.withAction { containsString("shortname") } }

                def results2 = validationResults.filter { it.isId(*id) }
                assert results2.size() == 0

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    /*
     * Task: SolveMultipleResults
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Task uses ISolver API for solving more tha one validationResult
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SolveMultipleResults", DV_PROJECT) {
        taskDescription 'Uses ISolver API for solving more tha one validationResult '

        taskHelp '''This will not work with every data, then please adapt the example to an ID which is
        contained in your CFG5 project
        '''
        code {
            validation {
                def numResultsBefore = validationResults.size()
                assert numResultsBefore > 5
                solver.solve {
                    // Call result() and pass a closure that works as filter based on methods of IValidationResultUI.
                    result { isId("CAN", 2019) }.withAction { containsString("Dafault") }

                    // On the return value, call withAction() and pass a closure that
                    // selects a solving-action based on methods of IValidationResultForSolvingActionSelect
                    // multiple result() calls can be placed in one solve() call.
                    result { isId("AR-ECUC", 3019) }.withAction { containsString("Delete") }
                }

                assert numResultsBefore > validationResults.size()

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    /*
     * Task: SolveAllWithPreferred
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Task shows two different ways of solving with preferred action
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SolveAllWithPreferred", DV_PROJECT) {
        taskDescription 'Task shows two different ways of solving with preferred action'

        taskHelp '''This will not work with every data, then please adapt the example a size which fits you project
        validationResults
        '''
        code {
            validation {
                assert validationResults.size() == 515
                solver.solveAllWithPreferredSolvingAction()
                assert validationResults.size() == 504
                scriptLogger.info("11 Entries were solved")

                // this would do the same
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving were reversed to reproduce them in a different way")

                assert validationResults.size() == 515

                solver.solve {
                    result { true }.withAction { preferred }
                }

                assert validationResults.size() == 504
                scriptLogger.info("11 Entries were solved")

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")

            }
        }
    }

    /*
     * Task: FilterResultsUsingAnIdConstant2
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Filter validationResults with a const ID
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("FilterResultsUsingAnIdConstant2", DV_PROJECT) {
        taskDescription 'Filter validationResults with a const ID'

        taskHelp '''This will not work with every data, then please adapt the example to an ID which is
        contained in your CFG5 project
        '''
        code {
            validation {
                def osConst = ["OS", 2130]

                assert validationResults.size() > 3
                assert validationResults.filter { it.isId(*osConst) }.size() == 6

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    final int SA_GROUP_ID_CREATE_AND_SET_TO_TRUE = 3
    /*
     * Task: SolveMultipleResultsByGroupId
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Task solves multiple validationResults with a global group ID
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SolveMultipleResultsByGroupId", DV_PROJECT) {
        taskDescription 'Task solves multiple validationResults with a global group ID'

        taskHelp '''This will not work with every data, then please adapt the example to an ID which is
        contained in your CFG5 project
        '''
        code {
            validation {
                assert validationResults.size() == 515

                solver.solve {
                    result { isId("DCM", 6026) }
                            .withAction { byGroupId(SA_GROUP_ID_CREATE_AND_SET_TO_TRUE) }
                    // instead of .withAction{containsString("next bigger valid value")}
                }

                assert validationResults.size() == 507
                // Three TP12 validation-results solved.

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    /*
     * Task: IValidationResultUIApiOverview
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * This task gives just an overview over the possibilities from the ValidationResult
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("IValidationResultUIApiOverview", DV_PROJECT) {
        taskDescription 'This task gives just an overview over the possibilities from the ValidationResult'

        taskHelp '''This is just an example. It will not work correct if your selected project hasn't had the right data
        '''

        code {
            validation {
                def r = validationResults.filter { it.isId("OS", 2140) }.first()
                assert r.id.origin == "OS"
                assert r.id.id == 2140
                assert r.description.toString().contains("is not referenced")
                assert r.severity == EValidationSeverityType.IMPROVEMENT
                assert r.solvingActions.size() == 2
                // assert r.getSolvingActionByGroupId(2).description.contains("AUTOSAR OS-Core")
                // this result has a preferred-solving-action
                // assert r.preferredSolvingAction == r.getSolvingActionByGroupId(2)

                // results with lower severity than ERROR can be acknowledged in the GUI
                assert r.acknowledgement.isPresent() == false

                // if the cause was an exception, r.cause.get() returns it
                assert r.cause.isPresent() == false

                // an ERROR result gets reduced to WARNING if one of its erroneous CEs is user-defined (user-overridden)
                assert r.isReducedSeverity() == false

                // on-demand results are visualized with a gear-wheel icon
                assert r.isOnDemandResult() == false
            }
        }
    }

    /*
     * Task: SolvingReturnValue
     * Type: DV_PROJECT
     * -------------------------------------------------------------------------------------------------------
     * Task gives an example how to use the solving Result from the ISolver API
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SolvingReturnValue", DV_PROJECT) {
        taskDescription 'Task gives an example how to use the solving Result from the ISolver API'

        taskHelp '''Because it is just an example, the size of the validationResult may not be correct for
        your project. So to get the task to work, you must adapt to your data
        '''
        code {
            validation {
                println(validationResults.size())
                assert validationResults.size() == 515
                // In this example, eleven validation-results have a valid preferred solving action.
                // There might be more but they cannot be solved because a parameter is user-defined for example.
                def summaryResult = solver.solveAllWithPreferredSolvingAction()
                assert validationResults.size() == 504
                // eleven have been solved, some more with a preferred solving-action were left.
                assert summaryResult.executionResult == EExecutionResult.WARNING

                // undo the action to not involve the other examples in this project
                transactions.transactionHistory.undo()
                scriptLogger.info("The solving action were reversed.")
            }
        }
    }

    /*
     * Task: ScriptTaskCreationResult
     * Type: DV_GENERATION_STEP
     * -------------------------------------------------------------------------------------------------------
     * Task will be executed as an external generation step and creates a new validationResult
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("ScriptTaskCreationResult", DV_GENERATION_STEP) {
        taskDescription 'Task will be executed as an external generation step and creates a new validationResult'

        taskHelp '''Add this task as a external generation step, then it will be executed during generation
        '''

        code { phase, genType, resultSinkFromTask ->
            scriptLogger.info "Generation Step started"

            if (phase.calculation) {
                // Execute code before / after calculation
                transaction {
                    // Modify the Model in the calculation phase
                }
            }

            if (phase.validation) {
                // Execute code before / after validation
                validation {
                    resultCreation {
                        // The ValidationResultId group multiple results
                        def valId = createValidationResultIdForScriptTask(
                                /* ID */ 2140,
                                /* Description */ "Summary of the ValidationResultId",
                                /* Severity */ EValidationSeverityType.ERROR)
                        // Create a new resultBuilder
                        def builder = newResultBuilder(valId, "Description of the Result")

                        // You can add multiple elements as error objects to mark them
                        builder.addErrorObject(bswmdModel(EcuC.DefRef).single)
                        // Add more calls when needed

                        // Create the result from the builder
                        def valResult = builder.buildResult()

                        // You need to report the result to a resultSink
                        // You have to get the sink from the context, e.g. script task args
                        // a sample line would be
                        resultSinkFromTask.reportValidationResult(valResult)
                    }
                }
            }
            if (phase.generation) {
                // Execute code before / after generation

                generation {
                    generate()
                }
            }
        }
    }
}