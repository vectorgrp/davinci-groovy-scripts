import com.vector.cfg.automation.api.ScriptApi
import com.vector.cfg.model.access.IMdfAccessPublishedExt
import com.vector.cfg.model.mdf.ar4x.swcomponenttemplate.datatype.datatypes.MIApplicationPrimitiveDataType
import com.vector.cfg.model.mdf.model.autosar.base.MIARPackage
import com.vector.cfg.model.mdf.model.autosar.base.MIAUTOSAR
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import static com.vector.cfg.automation.api.ScriptApi.daVinci

//daVinci enables the IDE code completion support . The daVinci{} block is only required for code completion support in the IDE. It has no eﬀect
//during runtime, so the daVinci{} is optional in script ﬁles (.dvgroovy)
daVinci {
    /*
     * Task: ArPackageNames
     * Type: DV_ON_FILE_PREPROCESSING_RESULT
     * -------------------------------------------------------------------------------------------------------
     *  Create New Application Data Types From Excel
     * -------------------------------------------------------------------------------------------------------
     */
    scriptDescription("Example of a description for the whole script")


    scriptTask("CreateApplicationDataTypeFromExcelList", DV_ON_FILE_PREPROCESSING_RESULT) {
        taskDescription ' Create New Application Data Types From Excel'

        // The type DV_ON_FILE_PREPROCESSING_INPUT_FILE allows the
        //script to modify every Autosar input ﬁle (.arxml) which is used in the update workﬂow except
        //of ProjectStandardConﬁguration ﬁles.


        //A script task can also have an optional help text. The help text shall describe in detail what the task does and when it could be executed. The method taskHelp(String) sets the help of the script task
        //example: taskHelp '''Simple Application task to create applicationdatatypes in an ARXML file of  Cfg5 project based on an Excel file

        code {

            try {
                // Call methods of the IScriptExecutionContext
                def logger = scriptLogger
                def temp = paths.tempFolder
                def args = getScriptTaskArguments()

                scriptLogger.info("Create New Application Data Types From Excel")

                //required for the access to the active project and the mdf model
                def mdfAccess = ScriptApi.activeProject.getInstance(IMdfAccessPublishedExt)

                //Create ArPackage for ApplicationDataTypes
                MIAUTOSAR root = AUTOSAR

                MIARPackage mySubPkg
                transaction {
                    // The asrPath points to an MIVariableDataPrototype
                    // For example create a new sub package
                    mySubPkg = root.subPackage.byNameOrCreate("MySubPkg")

                }

                // read excel file -- put the corresponding path where the excel file is located here
                String pathexcel = "D:/PES_8/FOSS/foss_scripts/CreateApplicationDataTypeFromExcelList/Input.xlsx"
                scriptLogger.info(pathexcel)


                InputStream excelFileToRead = new FileInputStream(pathexcel)
                XSSFWorkbook workbook = new XSSFWorkbook(excelFileToRead)
                XSSFSheet sheet = workbook.getSheetAt(0)

                // rows and cells
                XSSFRow row
                XSSFCell cell

                Iterator rows = sheet.rowIterator()
                def counterRows = 0
                while (rows.hasNext()) {
                    counterRows += 1
                    row = (XSSFRow) rows.next()
                    Iterator cells = row.cellIterator()

                    if (counterRows > 1) // ignore the title
                    {
                        // are there still cells to go trough?
                        def appDataTypename
                        def category
                        def counterCells = 0
                        while (cells.hasNext()) {
                            counterCells += 1

                            cell = (XSSFCell) cells.next()

                            if (cell.getCellType() == CellType.STRING) {
                                if (counterCells == 1) {
                                    appDataTypename = cell.getStringCellValue()
                                    scriptLogger.info(appDataTypename + " " + " name of new ApplicationDatatype")
                                } else if (counterCells == 2) {
                                    category = cell.getStringCellValue()
                                    scriptLogger.info(category + " " + " category of new ApplicationDatatype")
                                }
                            }
                        }

                        if (appDataTypename != null && category != null) {

                            // create applicationDataType
                            transaction {

                                MIApplicationPrimitiveDataType dataType = mdfAccess.createMDFObject(MIApplicationPrimitiveDataType.class)
                                dataType.name = appDataTypename
                                mySubPkg.getElement().add(dataType)
                                scriptLogger.info("new ApplicationDataType created")

                                // create category
                                dataType.setCategory(category)
                            }

                            scriptLogger.info("info")

                        }
                    }
                }

            } catch (Exception ex) {
                println("error " + ex.toString())
                scriptLogger.info("error " + ex.toString())
            }
        }

        // The property OutputFolder is the folder of the generated artifacts
        scriptLogger.info "the script was executed successfully"

    }
}
