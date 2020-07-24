#!/bin/bash

scriptname=$0
script_path=$(cd `dirname $scriptname`;pwd)
cd $script_path/..
APP_HOME=.

#需要启动的Java主程序（main方法类）
APP_MAIN_CLASS="com.ebtech.trust.EbtechTrustApplication"
#SHUTDOWN_CLASS="com.bayss.base.core.Shutdown"

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
CLASSPATH="$APP_HOME/config:$APP_HOME/lib/*"

ARGS="http://127.0.0.1:8080/actuator/shutdown"

s_pid=0
checkPid() {
   java_ps=`jps -l | grep $APP_MAIN_CLASS`
   if [ -n "$java_ps" ]; thensw
      s_pid=`echo $java_ps | awk '{print $1}'`
   else
      s_pid=0
   fi
}

stop() {
checkPid
if [ $s_pid -ne 0 ]; then
    echo -e "Stopping $APP_MAIN_CLASS ...(pid=$s_pid)"
#    nohup java -classpath $CLASSPATH $SHUTDOWN_CLASS $ARGS >./shutdown.out 2>&1 &
    kill -2 $s_pid >./shutdown.out 2>&1 &
    sleep 5
    checkPid
    if [ $s_pid -eq 0 ]; then
       echo "[ OK ]"
    else
       echo "[Failed to stop application...]"
    fi
#    checkPid
    if [ $s_pid -ne 0 ]; then
       stop
    else
       echo "$APP_MAIN_CLASS Stopped."
    fi
else
    echo "================================================================"
    echo "warn: $APP_MAIN_CLASS is not running"
    echo "================================================================"
fi
}

#echo "Stop project......"
stop