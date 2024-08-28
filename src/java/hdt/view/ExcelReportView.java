/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.view;

/**
 * Creates a detailed BoM in Excel format.
 * 
 * @author escralp
 */
import hdt.domain.APPAlternative;
import hdt.domain.APPNumber;
import hdt.domain.Alternatives;
import hdt.domain.Note;
import hdt.domain.Parameter;
import hdt.domain.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelReportView extends AbstractExcelView {

    // FIXME: These should come from a properties file.    
    private static final short MAIN_HEADER_FONTSIZE = 14;
    private static final short SUB_HEADER_FONTSIZE = 12;
    private static final short DEFAULT_FONTSIZE = 10;
    private static final String DEFAULT_FONT_NAME = "Calibri";
    private static final String SHEET_TITLE = "HDT Report";
    private static final String MAIN_HEADER = "HDT Dimensioning Report";
    private static final String SUB_HEADER_1 = "Overview";
    private static final String SUB_HEADER_2 = "Dimensioning Results";
    private static final String CUSTOMER_NAME = "Customer Name";
    private static final String CONF_NAME = "Configuration Name";
    private static final String SYSTEM = "System";
    private static final String NETWORK = "Network";
    private static final String BUNDLE = "Configuration";
    private static final String HDT_CUSTOM_CONFIG = "Custom Configuration";
    private static final String HDT_USER = "HDT User";
    private static final String HDT_APP_NUM = "APP";
    private static final String HDT_QTY = "Qty";
    private static final String HDT_USER_QTY = "User Qty";
    private static final String HDT_DESCRIPTION = "Description";
    private static final String HDT_EOL = "EOL";
    private static final String HDT_LOD = "LOD";    
    private static final String HDT_IMPORTANT_NOTES = "Important Notes";
    private static final String HDT_NOTE = "NOTE: ";
    private static final String HDT_WARN = "WARNING: ";
    private static final String HDT_PARAMETER_DEFAULT = "Default";
    private static final String HDT_PARAMETER_CURRENT = "Current";    
    private static final String HDT_CHANGED_PARAMETERS = "Manually overridden Dimensioning Parameters for ";
    private static final String HDT_CHANGED_APP_QTY = "Manually overridden APP Quantities for ";
    private static final String HDT_CHANGED_APP_QTY_WARN = "Please refer to notes on changing APP Number Quantities!";
    private static final String HDT_SHOW_HIDE_GROUP = "Click '+' to expand group, '-' to hide.";
    private static final String HDT_VER = "HDT Software Version";
    private static final String HDT_BUILD = "Build: ";
    private static final String HDT_DB_VER = "HDT Database Version";
    private static final String DATE_STR = "Date, Time";
    private int colNum = 0;
    private int maxColNum = 0;

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Row row;
        int rowNumber;

        // FIXME: This is really messy, should generate worbook from some kind of XML template or JSP etc.
        // Consider using something like DisplayTag: http://www.displaytag.org/

        // Create a new Excel workbook.       
        HSSFSheet sheet = workbook.createSheet(SHEET_TITLE);
        DataFormat format = workbook.createDataFormat();
        short numberFmt = format.getFormat("#");

        //
        // Create some fonts.
        //
        Font mainHeaderFont = workbook.createFont();
        mainHeaderFont.setFontName(DEFAULT_FONT_NAME);
        mainHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        mainHeaderFont.setFontHeightInPoints(MAIN_HEADER_FONTSIZE);

        Font subHeaderFont = workbook.createFont();
        subHeaderFont.setFontName(DEFAULT_FONT_NAME);
        subHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        subHeaderFont.setFontHeightInPoints(SUB_HEADER_FONTSIZE);

        Font boldFont = workbook.createFont();
        boldFont.setFontName(DEFAULT_FONT_NAME);
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        boldFont.setFontHeightInPoints(DEFAULT_FONTSIZE);

        Font normalFont = workbook.createFont();
        normalFont.setFontName(DEFAULT_FONT_NAME);
        normalFont.setFontHeightInPoints(DEFAULT_FONTSIZE);

        Font redNormalFont = workbook.createFont();
        redNormalFont.setFontName(DEFAULT_FONT_NAME);
        redNormalFont.setFontHeightInPoints(DEFAULT_FONTSIZE);
        redNormalFont.setColor(IndexedColors.RED.getIndex());

        Font redBoldFont = workbook.createFont();
        redBoldFont.setFontName(DEFAULT_FONT_NAME);
        redBoldFont.setFontHeightInPoints(DEFAULT_FONTSIZE);
        redBoldFont.setColor(IndexedColors.RED.getIndex());
        redBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        //
        // Create some cell styles.
        //
        CellStyle mainHeaderCellStyle = workbook.createCellStyle();        
        mainHeaderCellStyle.setFont(mainHeaderFont);
        mainHeaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle subHeaderCellSyle = workbook.createCellStyle();       
        subHeaderCellSyle.setFont(subHeaderFont);
        subHeaderCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle boldCellSyle = workbook.createCellStyle();        
        boldCellSyle.setFont(boldFont);
        boldCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle normalCellSyle = workbook.createCellStyle();
        normalCellSyle.setFont(normalFont);
        normalCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle highlightedCellSyle = workbook.createCellStyle();
        highlightedCellSyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        highlightedCellSyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        highlightedCellSyle.setFont(normalFont);
        highlightedCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle normalNumberCellSyle = workbook.createCellStyle();
        normalNumberCellSyle.setFont(normalFont);
        normalNumberCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);
        normalCellSyle.setDataFormat(numberFmt);

        CellStyle redNormalCellSyle = workbook.createCellStyle();        
        redNormalCellSyle.setFont(redNormalFont);
        redNormalCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle redBoldCellSyle = workbook.createCellStyle();
        redBoldCellSyle.setFont(redBoldFont);
        redBoldCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle redNormalNumberCellSyle = workbook.createCellStyle();
        redNormalNumberCellSyle.setFont(redNormalFont);
        redNormalNumberCellSyle.setAlignment(CellStyle.ALIGN_GENERAL);
        redNormalNumberCellSyle.setDataFormat(numberFmt);

        CellStyle detailsTableCellStyle = workbook.createCellStyle();
        detailsTableCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        detailsTableCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        detailsTableCellStyle.setFont(normalFont);
        detailsTableCellStyle.setAlignment(CellStyle.ALIGN_GENERAL);

        CellStyle detailsTableHeaderStyle = workbook.createCellStyle();
        detailsTableHeaderStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        detailsTableHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        detailsTableHeaderStyle.setFont(boldFont);
        detailsTableCellStyle.setAlignment(CellStyle.ALIGN_GENERAL);

        // 
        // Row creation starts here.
        //

        // Create row.
        rowNumber = 0;
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, colNum, mainHeaderCellStyle, MAIN_HEADER);

        // Empty row.
        row = sheet.createRow(rowNumber++);

        // Create row.
        row = sheet.createRow(rowNumber++);
        createCell(row, colNum, subHeaderCellSyle, SUB_HEADER_1);

        // Empty row.
        row = sheet.createRow(rowNumber++);

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, CUSTOMER_NAME);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("customerName"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, CONF_NAME);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("confName"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, SYSTEM);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("systemDesc"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, NETWORK);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("networkDesc"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);

        Boolean noParChanges = (Boolean) model.get("noParChanges");
        Boolean noAPPChanges = (Boolean) model.get("noAPPChanges");
        if (!(noParChanges && noAPPChanges)) {
            createCell(row, increaseColNum(), redBoldCellSyle, HDT_CUSTOM_CONFIG);
        } else {
            createCell(row, increaseColNum(), boldCellSyle, BUNDLE);
            createCell(row, increaseColNum(), normalCellSyle, (String) model.get("bundleDesc"));
        }

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, HDT_USER);
        createCell(row, increaseColNum(), normalCellSyle, "demo");

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, HDT_VER);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("hdt_ver") + ", " + HDT_BUILD + (String) model.get("hdt_buildnum"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, HDT_DB_VER);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("hdt_db_ver"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, DATE_STR);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("date_str"));


        // 2 empty row.
        row = sheet.createRow(rowNumber++);
        row = sheet.createRow(rowNumber++);

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), subHeaderCellSyle, SUB_HEADER_2);

        // Empty row.
        row = sheet.createRow(rowNumber++);

        //
        // APP Number BoM for each role.
        //

        // Retrieve all the roles from the model.

        List<Role> roleList = (List<Role>) model.get("roleList");
        List<Parameter> changedMainParameters = (List<Parameter>) model.get("changedMainParameters");
        Map<String, List<Parameter>> changedSecondaryParameters = (Map<String, List<Parameter>>) model.get("changedSecondaryParameters");
        Map<String, List<APPNumber>> changedAPPQuantities = (Map<String, List<APPNumber>>) model.get("changedAPPQuantities");
        
        int roleNum = 1;
        for (Role role : roleList) {
            resetColNum();
            row = sheet.createRow(rowNumber++);
            createCell(row, increaseColNum(), boldCellSyle, Integer.toString(roleNum++) + ".) " + role.getDescription());

            // Empty row.
            row = sheet.createRow(rowNumber++);

            // Details table.
            resetColNum();
            row = sheet.createRow(rowNumber++);
            createCell(row, increaseColNum(), boldCellSyle, HDT_APP_NUM);
            createCell(row, increaseColNum(), boldCellSyle, HDT_DESCRIPTION);
            createCell(row, increaseColNum(), boldCellSyle, HDT_QTY);
            createCell(row, increaseColNum(), boldCellSyle, HDT_EOL);
            createCell(row, increaseColNum(), boldCellSyle, HDT_LOD);

            // Retrieve APP numbers for this role from model.
            Map<String, Alternatives> alternativesMap = (Map<String, Alternatives>) model.get("alternativesMap");
            Alternatives alt = null;
            APPAlternative selectedAlt = null; 
            List<APPNumber> appNumList = null;  
            
            if (alternativesMap != null) {
                alt = alternativesMap.get(role.getName());
                
                if (alt != null) {
                    selectedAlt = alt.getSelectedAlternative();
                    
                    if (selectedAlt != null) {
                        appNumList = selectedAlt.getAlternative();
                    }
                }
            }
            
            if (appNumList != null) {
                //
                // Details for each APP Number
                //
                for (APPNumber appNum : appNumList) {
                    // Iterate over all APP numbers
                    resetColNum();
                    row = sheet.createRow(rowNumber++);
                    createCell(row, increaseColNum(), normalCellSyle, appNum.getName());
                    createCell(row, increaseColNum(), normalCellSyle, appNum.getDescription());

                    // FIXME: Colors? Print both?
                    if (appNum.isUserQtySet()) {
                        createCell(row, increaseColNum(), redNormalNumberCellSyle, appNum.getUserQuantity());
                    } else {
                        createCell(row, increaseColNum(), normalNumberCellSyle, appNum.getQuantity());
                    }

                    createCell(row, increaseColNum(), normalCellSyle, appNum.getEndOfServiceLife());
                    createCell(row, increaseColNum(), normalCellSyle, appNum.getLastOrderDate());
                }
            }

            // Empty row.
            row = sheet.createRow(rowNumber++);

            //
            // Parameter Changes Details
            //
            
            // FIXME: Main parameter and secondary parameter changes should be listed separately.
            List<Parameter> changedParList = new ArrayList<Parameter>();
            changedParList.addAll(changedMainParameters);
            List<Parameter> tmpList = changedSecondaryParameters.get(role.getName());
            if (tmpList != null) {
                changedParList.addAll(tmpList);
            }
            
            if(changedParList != null && !changedParList.isEmpty()) {
                // Note.
                resetColNum();
                row = sheet.createRow(rowNumber++);
                
                // Colorise different parts of the string separately.
                CreationHelper ch = workbook.getCreationHelper();
                String tmp = HDT_NOTE + HDT_CHANGED_PARAMETERS + role.getDescription();
                RichTextString rts = ch.createRichTextString(tmp);
                rts.applyFont(0, HDT_NOTE.length(), redBoldFont);
                rts.applyFont(HDT_NOTE.length(), tmp.length(), boldFont);
                createCell(row, increaseColNum(), normalCellSyle, rts);

                 // Details Table start
                int rowStart = rowNumber;
                // Details Table Header
                resetColNum();
                row = sheet.createRow(rowNumber++);                
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_DESCRIPTION);
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_PARAMETER_DEFAULT);
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_PARAMETER_CURRENT);
                
                for (Parameter cP : changedParList) {
                    resetColNum();
                    row = sheet.createRow(rowNumber++);
                    createCell(row, increaseColNum(), detailsTableCellStyle, cP.getDescription());
                    createCell(row, increaseColNum(), detailsTableCellStyle, cP.getDefaultValueAsString());
                    createCell(row, increaseColNum(), detailsTableCellStyle, cP.getValueAsString());
                }
                int rowEnd = rowNumber;
                
                // Group the Details Table rows.
                sheet.groupRow(rowStart, rowEnd);
                sheet.setRowGroupCollapsed(rowStart, true);

                // Footer
                resetColNum();
                row = sheet.createRow(rowNumber++);
                createCell(row, increaseColNum(), normalCellSyle, HDT_SHOW_HIDE_GROUP);

                // Empty row.
                row = sheet.createRow(rowNumber++);
            }

            // APP Quantity Changes Details.
            List<APPNumber> changedAPPList = changedAPPQuantities.get(role.getName());
            if(changedAPPList != null && !changedAPPList.isEmpty()) {
                // Warning.
                resetColNum();
                row = sheet.createRow(rowNumber++);
                
                // Colorise different parts of the string separately.
                CreationHelper ch = workbook.getCreationHelper();
                String tmp = HDT_WARN + HDT_CHANGED_APP_QTY + role.getDescription() + ". " + HDT_CHANGED_APP_QTY_WARN;
                RichTextString rts = ch.createRichTextString(tmp);
                rts.applyFont(0, HDT_WARN.length(), redBoldFont);
                rts.applyFont(HDT_WARN.length(), tmp.length(), boldFont);
                createCell(row, increaseColNum(), highlightedCellSyle, rts);

                 // Details Table start
                int rowStart = rowNumber;
                // Details Table Header
                resetColNum();
                row = sheet.createRow(rowNumber++);                
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_APP_NUM);
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_QTY);
                createCell(row, increaseColNum(), detailsTableHeaderStyle, HDT_USER_QTY);
                
                for (APPNumber app : changedAPPList) {
                    resetColNum();
                    row = sheet.createRow(rowNumber++);
                    createCell(row, increaseColNum(), detailsTableCellStyle, app.getName());
                    createCell(row, increaseColNum(), detailsTableCellStyle, app.getQuantity());
                    createCell(row, increaseColNum(), detailsTableCellStyle, app.getUserQuantity());
                }
                int rowEnd = rowNumber;

                // Group the Details Table rows.
                sheet.groupRow(rowStart, rowEnd);
                sheet.setRowGroupCollapsed(rowStart, true);

                // Footer
                resetColNum();
                row = sheet.createRow(rowNumber++);
                createCell(row, increaseColNum(), normalCellSyle, HDT_SHOW_HIDE_GROUP);

                // Empty row.
                row = sheet.createRow(rowNumber++);                
            }                       

            // Empty row.
            row = sheet.createRow(rowNumber++);
        }

        // 2 empty rows.
        row = sheet.createRow(rowNumber++);
        row = sheet.createRow(rowNumber++);

        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), redBoldCellSyle, HDT_IMPORTANT_NOTES);

        // Empty row.
        row = sheet.createRow(rowNumber++);

        // Render the Notes.
        List<Note> noteList = (List<Note>) model.get("noteList");
        for (Note note : noteList) {
            if (note.isEnabled()) {
                // Create row.
                resetColNum();
                row = sheet.createRow(rowNumber++);
                createCell(row, increaseColNum(), boldCellSyle, note.getNoteText());
            }
        }

        // Must be last call to take all widths into account.
        adjustWidths(sheet);

        // Use this to create a watermark.
        //createWatermark(workbook, sheet);
    }

    private void resetColNum() {
        colNum = 0;
    }

    private int increaseColNum() {
        int retVal = colNum;

        colNum++;
        if (colNum > maxColNum) {
            maxColNum = colNum;
        }

        // Return the old value before increasing.
        return retVal;
    }

    // Adjust optimal column width.
    private void adjustWidths(Sheet sheet) {
        for (int col = 0; col < maxColNum; col++) {
            sheet.autoSizeColumn(col);
        }
    }

    // Create a cell and update the maximum column width for later adjustment.
    private void createCell(Row row, int index, CellStyle style, String value) {
        Cell cell = row.createCell(index);

        cell.setCellValue(value);

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private void createCell(Row row, int index, CellStyle style, Integer value) {
        Cell cell = row.createCell(index);

        cell.setCellValue(value);

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private void createCell(Row row, int index, CellStyle style, RichTextString value) {
        Cell cell = row.createCell(index);

        cell.setCellValue(value);

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private void createWatermark(HSSFWorkbook wb, HSSFSheet ws) {
        HSSFPatriarch dp = ws.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 6, 0, (short) 15, 17);
        HSSFTextbox txtbox = dp.createTextbox(anchor);
        HSSFRichTextString rtxt = new HSSFRichTextString("Technology Preview - Not for actual dimensioning");
        Font font = wb.createFont();
        font.setColor(IndexedColors.RED.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontHeightInPoints((short) 48);
        font.setFontName(DEFAULT_FONT_NAME);
        rtxt.applyFont(font);
        txtbox.setString(rtxt);
        txtbox.setLineStyle(HSSFShape.LINESTYLE_NONE);
        txtbox.setNoFill(true);
    }
}
