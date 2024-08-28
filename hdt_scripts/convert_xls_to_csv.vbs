REM Quick XLS to CSV converter.
REM

Set objArgs = WScript.Arguments
For I = 0 to objArgs.Count - 1

    FullName = objArgs(I)
    FileName = Left(objArgs(I), InstrRev(objArgs(I), ".") )

    Set objExcel = CreateObject("Excel.application")	
    Set objExcelBook = objExcel.Workbooks.Open(FullName)

    objExcel.application.visible=false
    objExcel.application.displayalerts=false	

    objExcelBook.SaveAs FileName & "csv", 23

    objExcel.Application.Quit
    objExcel.Quit   

    Set objExcel = Nothing
    set objExcelBook = Nothing

Next