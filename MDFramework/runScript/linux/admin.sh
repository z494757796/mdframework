# !/bin/bash
# description:start/stop/check sjyl-admin.jar

SERVICEAPP_NAME=CeSuan-admin.jar
SERVICEAPP_DEPHOME=/usr/local/mdsoftware/wwwroot/CeSuan
SERVICEAPP_LOGHOME=/usr/local/mdsoftware/wwwroot/CeSuan/logs
SYSTEM_ENV=admin

# config for 4C8G
JAVA_OPTS="-Xms128m -Xmx128m  \
-XX:PermSize=64M \
-XX:MaxPermSize=128m \
-Xss1m \
-Xmn70m \
-XX:+AggressiveOpts \
-XX:+UseBiasedLocking \
-XX:+CMSParallelRemarkEnabled \
-XX:+UseConcMarkSweepGC \
-XX:ParallelGCThreads=4 \
-XX:SurvivorRatio=4 \
-XX:TargetSurvivorRatio=85 \
-verbose:gc \
-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-XX:+PrintHeapAtGC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=$SERVICEAPP_LOGHOME/dump.logs "

# -Djava.awt.headless=true

pid_admin=0

# 
checktpid(){
	if [ ! -d ${SERVICEAPP_LOGHOME} ]; then
	 mkdir -p ${SERVICEAPP_LOGHOME}
	 chmod 777 ${SERVICEAPP_LOGHOME}
	fi
	 
	if [ ! -f ${SERVICEAPP_LOGHOME}/tpid ]; then
	 touch ${SERVICEAPP_LOGHOME}/tpid
	fi
	
	tpid=`cat $SERVICEAPP_LOGHOME/tpid | awk '{print $1}'`
	tpid=`ps -aef | grep ${tpid} | awk '{print $2}' |grep ${tpid}`
	if [ ${tpid} ]; then
	        pid_admin=1
	else
	        pid_admin=0
	fi
}

# 
start(){
    checktpid

    if [ $pid_admin -ne 0 ];then
        echo "WARN:$SERVICEAPP_NAME already started! Ignoring startup request."
    else
        rm -f $SERVICEAPP_LOGHOME/tpid
        /usr/local/mdsoftware/runtime/java/jdk1.8.0_161/bin/java -Dloader.path="lib/" -jar $JAVA_OPTS $SERVICEAPP_DEPHOME/$SERVICEAPP_NAME --spring.config.location=$SERVICEAPP_DEPHOME/config/application-${SYSTEM_ENV}.properties > /dev/null 2>&1 &
#         java -jar $JAVA_OPTS $SERVICEAPP_DEPHOME/$SERVICEAPP_NAME --spring.profiles.active=$SYSTEM_ENV > /dev/null 2>&1 &
        echo $! > $SERVICEAPP_LOGHOME/tpid
        echo "Starting $SERVICEAPP_NAME admin..."
    fi
}

# 
stop(){
    checktpid
    if [ $pid_admin -ne 0 ];then
        echo "Stoping $SERVICEAPP_NAME..."
        kill -9 $tpid
        sleep 3s
        checktpid
        if [ $pid_admin -ne 0 ];then
        		echo "Stoping use kill -9..."
           	kill -9 $tpid 
        else
            echo "$SERVICEAPP_NAME Stopped!"      	
        fi 
    else    
		 		 echo "WARN:$SERVICEAPP_NAME is not runing"
    fi
}

# 
status(){
    checktpid
    if [ $pid_admin -eq 0 ];then
        echo "$SERVICEAPP_NAME is not runing"
    else
        echo "$SERVICEAPP_NAME is runing"
    fi
}

# 
case "$1" in
    'start')
        start
        ;;
    'stop')
        stop
        ;;
    'restart')
        stop
        sleep 5
        start
        ;;
    'status')
        status
        ;;
    *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
esac
exit 0;
