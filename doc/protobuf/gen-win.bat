@echo off
rem ÕıÔÚËÑË÷... 
for /r ./ %%i in (*.proto) do call D:\Software\protoc-3.0.0-win32\bin\protoc.exe --java_out=..\ %%~nxi
rem ËÑË÷Íê±Ï  
pause  