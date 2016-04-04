@echo off
SET currentPath=%~dp0
start cmd /k call StartTracker.bat %currentPath%
start cmd /k call StartZeroConf.bat %currentPath%