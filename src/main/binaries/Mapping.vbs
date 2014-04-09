' Arguments
Dim Args
Set Args  = WScript.Arguments 

' Drive
strDrive = Args(0)
' Path
strPath = Args(1)

' Gets the share name using the folder name from the path
iPos = InStrRev(strPath, "\")
strFolder = Mid(strPath, iPos + 1)

' 1) Shares the folder using this name
ShareFolder strPath, strFolder, strDrive & " mapping"
strRemotePath = "\\" & GetLocalIP_Address & "\" & strFolder

' 2) Release the drive letter
ReleaseMappedDrive (strDrive)

' 3) Actual mapping
MapToDrive strDrive, strRemotePath

' All functions

' Gets the local IP address
Public Function GetLocalIP_Address()

	Set objWMIService = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
	Set colItems = objWMIService.ExecQuery ("Select * From Win32_NetworkAdapterConfiguration Where IPEnabled = True")

	For Each objConfiguration In colItems
		arrayAddresses = objConfiguration.IPAddress
		For Each strAddress In arrayAddresses
			If InStr(strAddress, "::") = 0 Then
				GetLocalIP_Address = strAddress
				Exit Function
			End If
		Next
	Next

End Function

' Shares a folder
Public Function ShareFolder (ByVal strPath, ByVal strName, ByVal strReason)
	Set objWMIService = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
	Set objNewShare = objWMIService.Get("Win32_Share")
	errReturn = objNewShare.Create (strPath, strName, 0, 5, strReason)
End Function

' Releases a mapped drive
Public Function ReleaseMappedDrive(ByVal strDriveLetter)
	Dim objNetwork, objShell 

	err.number = vbEmpty
	Set objNetwork = CreateObject("WScript.Network") 
	On Error Resume Next
	' Forces the release of the network drive
	objNetwork.RemoveNetworkDrive strDriveLetter, True, True

	' Waits until the mapping is actually undone
	WScript.Echo "Sleeping..."
	WScript.Sleep 8000
	WScript.Echo "End of sleep"

	' Error handling
	If err.number = vbEmpty then
		' No error
		ReleaseMappedDrive = True
	ElseIf err.number = -2147022646 then
		'Drive did not exist
		'Do nothing
		ReleaseMappedDrive = True
	Else 
		'Unknown error
		ReleaseMappedDrive = False
	End If
End Function

' Actual mapping
Public Function MapToDrive(Byval strDriveLetter, Byval strRemotePath)
	WScript.Echo "Mapping " & strDriveLetter & " to " & strRemotePath
	Dim objNetwork 
	Set objNetwork = CreateObject("WScript.Network") 
	objNetwork.MapNetworkDrive strDriveLetter, strRemotePath, True
End Function
