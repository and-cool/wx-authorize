#!/bin/bash

scriptname=$0
script_path=$(cd `dirname $scriptname`;pwd)
cd $script_path/..
APP_HOME=.

#需要启动的Java主程序（main方法类）
APP_MAIN_CLASS="com.ebtech.trust.EbtechTrustApplication"

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
CLASSPATH=".:$APP_HOME/config:$APP_HOME/lib/*"

s_pid=0
checkPid() {
   java_ps=`jps -l | grep $APP_MAIN_CLASS`
   if [ -n "$java_ps" ]; then
      s_pid=`echo $java_ps | awk '{print $1}'`
   else
      s_pid=0
   fi
}

start() {
checkPid
if [ $s_pid -ne 0 ]; then
    echo "================================================================"
    echo "warn: $APP_MAIN_CLASS already started! (pid=$s_pid)"
    echo "================================================================"
else
    echo "Starting $APP_MAIN_CLASS ..."

#    nohup /usr/local/java/jdk1.8.0_202/bin/java -classpath .:config:lib/* com.bayss.bws.BwsServerApplication >./catalina.out 2>&1 &

    nohup java -classpath $CLASSPATH $APP_MAIN_CLASS >./catalina.out 2>&1 &
    sleep 6s
    checkPid
    if [ $s_pid -ne 0 ]; then
        echo "application started... (pid=$s_pid) "
    else
        echo "[Failed to start application...]"
    fi
fi
}

#echo "starting project......"
start