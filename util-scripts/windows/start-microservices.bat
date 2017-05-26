@echo off

REM print menu
:MENU
echo Select which service to start:
echo. 
echo [1] Eureka Registry 
echo [2] UAA Service
echo [3] Product Service
echo [4] Zuul Gateway
echo [5] Quit
echo.
REM EO MENU

REM Take user input
set /p service_no="Choose a service:"


IF %service_no%==1 (
	set service_id=registry-service
) ELSE IF %service_no%==2 (
	set service_id=uaa-service
) ELSE IF %service_no%==3 (
	set service_id=product-service
) ELSE IF %service_no%==4 (
	set service_id=api-gateway-service
) ELSE IF %service_no%==5 (
	GOTO EXIT
) ELSE (
	GOTO BAD_INPUT
)

REM Start the service
start start-spring-service.bat %service_id%

GOTO :MENU

:BAD_INPUT
echo Error: Cannot recognize the input...
GOTO :MENU

:EXIT
echo Bye!
cmd /k