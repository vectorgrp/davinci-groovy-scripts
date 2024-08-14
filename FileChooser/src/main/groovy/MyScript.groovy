/* Copyright (c) 2024 Vector Informatik GmbH
 * SPDX-License-Identifier: MIT
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter

import static com.vector.cfg.automation.api.ScriptApi.*

//daVinci enables the IDE code completion support
daVinci {

    /* 
     * Task: SimpleApplTask
     * Type: DV_APPLICATION
     * -------------------------------------------------------------------------------------------------------
     * Prints "HelloApplication" to console and logs a message to the scriptLogger
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("SimpleApplTask", DV_APPLICATION) {
        taskDescription 'Prints "HelloApplication" to console and logs a message to the scriptLogger'
        
        taskHelp '''Simple Application task to show the creation of a task.
The task will print  "HelloApplication" to console.
Logs the message "Logging message from the script" to the scriptLogger.
        '''
        
        code{

            /*
            * als einfaches und recht gut funktionierendes Item haben wir einen FileChooser integrieren können,

hier mal ein SourceCode dazu der ein Excel Workbook abspeichert und liest:

*/
            HSSFWorkbook mappingWorkbook = new HSSFWorkbook()

            openFile("xls")

            saveFile(mappingWorkbook, "Mappings.xls")
            println "HelloApplication"
            scriptLogger.info "Logging message from the script"
        }
    }


}

File openFile(String extension){

    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"))
    FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + extension, extension)
    chooser.setFileFilter(filter)
    chooser.setSelectedFile(new File(System.getProperty("user.dir") + "/Mappings.xls"))
    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {     //clicked on 'open'
        if(filter.accept(chooser.getSelectedFile())){
            return chooser.getSelectedFile()
        }
        scriptLogger.infoFormat("The file '{0}' has wrong format. *." + extension + " is needed." , chooser.getSelectedFile().name)
    }
    return null

}

void saveFile(HSSFWorkbook wb, String fileName){

    String[] tmp = fileName.split("\\.")
    String extension = tmp[1]
    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"))
    FileFilter filter = new FileNameExtensionFilter("*." + extension, extension)
    chooser.setSelectedFile(new File(System.getProperty("user.dir") + "/Mappings.xls"))
    chooser.addChoosableFileFilter(filter)
    chooser.setFileFilter(filter)

    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
        try{
            OutputStream fileOut = new FileOutputStream(chooser.getSelectedFile())
            wb.write(fileOut)
        }catch(FileNotFoundException e){
            println "File could not created. Make sure no file named: '" + chooser.getSelectedFile().name + "' is open."
        }catch(Exception e){
            println e.message
        }finally{
            wb.close()
        }
    }
}
