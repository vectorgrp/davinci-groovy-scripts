## DaVinci Groovy Scripts
This repository contains a collection of Groovy scripts for a variety of purposes.
They all use the Published Automation Interface (PAI) of [DaVinci Configurator 5](https://www.vector.com/de/de/produkte/produkte-a-z/software/davinci-configurator-classic/).

<table>
<thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Task</th>
    <th>Type</th>
    <th>Description</th>
    <th>Requirements</th>
    <th>Received</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>001</td>
    <td><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012432">FileChooser</a></td>
    <td>FileChooserTask</td>
    <td>DV_APPLICATION</td>
    <td>opens a dialog to load an .xls file, copies it in an Excel Workbook and then opens a dialog to choose the path where to save it.</td>
    <td><lu><li>.xls file to save</li></lu> </td>
    <td>KnowledgeBase<br>ID: KB0012432</td>
  </tr>
  <tr>
    <td rowspan="2">002</td>
    <td rowspan="2"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012355">CSP_Project_API</a></td>
    <td>ReadTheActiveEcuCTask</td>
    <td>DV_PROJECT</td>
    <td>reads container and parameter with the BswmdModel from a loaded project.</td>
    <td rowspan="2"></td>
    <td rowspan="2">KnowledgeBase<br>ID: KB0012355</td>
  </tr>
  <tr>
    <td>WriteTheActiveEcuCTask</td>
    <td>DV_PROJECT</td>
    <td>writes EcuCores with different methods in a loaded project.</td>
  </tr>
  <tr>
    <td rowspan="2">003</td>
    <td rowspan="2"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012354">CSP_Application_API</a></td>
    <td>CreateNewProject</td>
    <td>DV_APPLICATION</td>
    <td>creates a new project and activates Det, Dio and EcuC modules.</td>
    <td><lu><li>insert path to the directory of the new .dpa project in the code</li></lu></td>
    <td rowspan="2">KnowledgeBase<br>ID: KB0012354</td>
  </tr>
  <tr>
    <td>CreateAndUpdateProjectTask</td>
    <td>DV_APPLICATION</td>
    <td>creates a new project and updates it with a communication extract.</td>
    <td><lu><li>insert in the code the path to the already existing directory/folder in which the new .dpa project should be created</li><li>e_Rx_simple_AR4.arxml file to get the communication definitions</li></lu></td>
  </tr>
  <tr>
    <td rowspan="3">004</td>
    <td rowspan="3"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012359">CSP_Workflow_API</a></td>
    <td>UpdateExistingProject</td>
    <td>ON_SUCCESSFULL_WORKFLOW_UPDATE</td>
    <td>will be executed after a successful update and then just prints update successful in the console output.</td>
    <td></td>
    <td rowspan="3">KnowledgeBase<br>ID: KB0012359</td>
  </tr>
  <tr>
    <td>TestUpdateSettings</td>
    <td>APPLICATION</td>
    <td>starts an update and enables the possibility to modify the SystemExtract. Then executes a python script and disables the possibility afterwards.</td>
    <td><lu><li>.dpa project path defined in the code (project is not allowed to be open/running in the DV CFG)</li><li>.py script path defined in the code</li></lu></td>
  </tr>
  <tr>
    <td>ArPackageNames</td>
    <td>DV_ON_FILE_PREPROCESSING_RESULT</td>
    <td>will be executed after the filepreprocesing and therefore it is using the ScriptTaskType DV_ON_FILEPREPROCESSING_RESULT. It reads the ARPackages from the MDFModel and prints the ASRPath in the console output.</td>
    <td></td>
  </tr>
  <tr>
    <td rowspan="2">005</td>
    <td rowspan="2"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012358">CSP_GenerationStep_API</a></td>
    <td>GenStepTas_Step</td>
    <td>DV_GENERATION_STEP</td>
    <td>will be executed during generation as a generation step and then creates a new validationResult.</td>
    <td rowspan="2"></td>
    <td rowspan="2">KnowledgeBase<br>ID: KB0012358</td>
  </tr>
  <tr>
    <td>GenEndTask_End</td>
    <td>DV_ON_GENERATION_END</td>
    <td>will be automatically executed at the end of the generation process and just prints the result of the generation and the executed generators in the console output.</td>

  </tr>
  <tr>
    <td rowspan="2">006</td>
    <td rowspan="2"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012356">CSP_UI_API</a></td>
    <td>SimpleApplTask</td>
    <td>DV_EDITOR_SELECTION</td>
    <td rowspan="2">both just print some text in the console output but it differs depending on the selected element(s).</td>
    <td rowspan="2"></td>
    <td rowspan="2">KnowledgeBase<br>ID: KB0012356</td>
  </tr>
  <tr>
    <td>SimpleApplTask_Multi</td>
    <td>DV_EDITOR_MULTI_SELECTION</td>
  </tr>
  <tr>
    <td rowspan="9">007</td>
    <td rowspan="9"><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012357">CSP_Validation_API</a></td>
    <td>CheckValidationResults_filterByOriginId</td>
    <td>DV_PROJECT</td>
    <td rowspan="9">example for the ScriptTaskType Validation API. This project contains various tasks regarding the IValidation API. It gives an overview how to get the validation results, how to filter them and how to use the ISolver API. There are examples which show the usage of solvingActions and how to create your own validationResult.</td>
    <td rowspan="8"><lu><li>to use all this tasks correct it is required to modify the requested entities in the code. For example the searched warning entity has to be listed and the amount of all entities has to be set correct. If this is not done before execution, all the asserts will be FALSE. Also have in mind that if the solving actions weren't reverted, the amount of all entities were changed and need to be modified before the next task will be executed</li></lu></td>
    <td rowspan="9">KnowledgeBase<br>ID: KB0012357</td>
  </tr>
  <tr>
    <td>SolveResultSolvingAction</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>SolveMultipleResults</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>SolveAllWithPreferred</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>FilterResultsUsingAnIdConstant2</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>SolveMultipleResultsByGroupId</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>IValidationResultUIApiOverview</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>SolvingReturnValue</td>
    <td>DV_PROJECT</td>
  </tr>
  <tr>
    <td>ScriptTaskCreationResult</td>
    <td>DV_GENERATION_STEP</td>
    <td></td>
  </tr>
  <tr>
    <td>008</td>
    <td><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012238">CreateApplicationDataTypeFromExcelList</a></td>
    <td>CreateApplicationDataTypeFromExcelList</td>
    <td>DV_ON_FILE_PREPROCESSING_RESULT</td>
    <td>creates Application Primitive Datatypes based on an Excel file.</td>
    <td></td>
    <td>KnowledgeBase<br>ID: KB0012238</td>
  </tr>
  <tr>
    <td>009</td>
    <td><a href="https://support.vector.com/kb?id=kb_article_view&sysparm_article=KB0012361">ExamplePostImport</a></td>
    <td>UpdateExistingProject</td>
    <td>DV_ON_SUCCESSFUL_UPDATE_WORKFLOW</td>
    <td>changes the value of the parameter DemEventCreateInfoPort in all occurrences in DemEventParametern after a successful update was executed. This task type will be executed automatically, but only if the update was successful.</td>
    <td></td>
    <td>KnowledgeBase<br>ID: KB0012361</td>
  </tr>
</tbody>
</table>

## License

This project is licensed under the [MIT LICENSE](LICENSE).