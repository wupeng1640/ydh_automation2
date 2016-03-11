Dim $filePath = "E:\workspace\com.MyWorkSpace.Java\lib\附件\goods_picture\goods10.png"
Dim $dialogTitle = "文件上传"
WinActivate($dialogTitle)
WinWaitActive($dialogTitle)
Sleep(1*1000)
ControlSetText($dialogTitle,"","Edit1",$filePath)
ControlClick($dialogTitle,"","Button1")
;Sleep(1*1000)ackTrace();