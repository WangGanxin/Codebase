@echo off
echo begin running
if exist release\zipaligned (
	rd /s /q release\zipaligned
)
md release\zipaligned
for /f "delims=" %%i in ('dir release\*.apk /b') do (
	zipalign -v 4  release\%%i release\zipaligned\%%i
)
echo end running