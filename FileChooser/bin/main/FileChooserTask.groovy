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
     * Task: FileChooserTask
     * Type: DV_APPLICATION
     * -------------------------------------------------------------------------------------------------------
     * Loads a xls file and saves it under a selected path
     * -------------------------------------------------------------------------------------------------------
     */
    scriptTask("FileChooserTask", DV_APPLICATION) {
        taskDescription 'Loads a xls file, copies it in a excelworkbook and saves it under a selected path'

        taskHelp '''Opens a dialog to selected a xls file, copies it in a excelworkbook and opens a dialog to saves it under a selected path.
            Logs the message "Logging message from the script" to the scriptLogger.
        '''

        code {
            File newFile = openFile("xls")
            FileInputStream input = new FileInputStream(newFile.getPath())
            HSSFWorkbook mappingWorkbook = new HSSFWorkbook(input)
            saveFile(mappingWorkbook, "xls")
            scriptLogger.info "Logging message from the script"
        }
    }
}

static File openFile(String extension) {

    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"))
    FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + extension, extension)
    chooser.setFileFilter(filter)
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {     //clicked on 'open'
        if (filter.accept(chooser.getSelectedFile())) {
            println "File " + chooser.getSelectedFile().getPath() + "." + extension + " was loaded successfully "
            return chooser.getSelectedFile()
        }
        scriptLogger.infoFormat("The file '{0}' has wrong format. *." + extension + " is needed.", chooser.getSelectedFile().name)
    }
    return null
}

void saveFile(HSSFWorkbook wb, String extension) {

    println System.getProperty("user.dir")
    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"))
    FileFilter filter = new FileNameExtensionFilter("*." + extension, extension)
    chooser.addChoosableFileFilter(filter)
    chooser.setFileFilter(filter)

    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        try {
            OutputStream fileOut = new FileOutputStream(chooser.getSelectedFile().getPath() + "." + extension)
            wb.write(fileOut)
            println "File " + chooser.getSelectedFile().getPath() + "." + extension + " was saved successfully "
        } catch (FileNotFoundException e) {
            println "File could not created. Make sure no file named: '" + chooser.getSelectedFile().name + "' is open."
        } catch (Exception e) {
            println e.message
        } finally {
            wb.close()
        }
    }
}
