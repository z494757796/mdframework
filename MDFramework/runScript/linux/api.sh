# !/bin/bash
# description:start/stop/check sjyl-api.jar

SERVICEAPP_API_NAME=yewubaozhuang-api.jar
SERVICEAPP_API_DEPHOME=/usr/local/mdsoftware/wwwroot/YeWuBaoZhuang
SERVICEAPP_API_LOGHOME=/usr/local/mdsoftware/wwwroot/YeWuBaoZhuang/logs
SYSTEM_API_ENV=api

# config for 4C8G
API_JAVA_OPTS="-Xms128m -Xmx128m  \
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
-XX:HeapDumpPath=$SERVICEAPP_API_LOGHOME/dump.logs "

# -Djava.awt.headless=true

pid_api=0

# 
checktapipid(){
	if [ ! -d ${SERVICEAPP_API_LOGHOME} ]; then
	 mkdir -p ${SERVICEAPP_API_LOGHOME}
	 chmod 777 ${SERVICEAPP_API_LOGHOME}
	fi
	 
	if [ ! -f ${SERVICEAPP_API_LOGHOME}/tpidapi ]; then
	 touch ${SERVICEAPP_API_LOGHOME}/tpidapi
	fi
	
	tpidapi=`cat $SERVICEAPP_API_LOGHOME/tpidapi | awk '{print $1}'`
	tpidapi=`ps -aef | grep ${tpidapi} | awk '{print $2}' |grep ${tpidapi}`
	if [ ${tpidapi} ]; then
	        pid_api=1
	else
	        pid_api=0
	fi
}

# 
startapi(){
    checktapipid

    if [ $pid_api -ne 0 ];then
        echo "WARN:$SERVICEAPP_API_NAME already started! Ignoring startup request."
    else
        rm -f $SERVICEAPP_API_LOGHOME/tpidapi
        /usr/local/mdsoftware/runtime/java/jdk1.8.0_161/bin/java -Dloader.path="lib/" -jar $API_JAVA_OPTS $SERVICEAPP_API_DEPHOME/$SERVICEAPP_API_NAME --spring.config.location=$SERVICEAPP_API_DEPHOME/config/application-${SYSTEM_API_ENV}.properties > /dev/null 2>&1 &
#         java -jar $API_JAVA_OPTS $SERVICEAPP_API_DEPHOME/$SERVICEAPP_API_NAME --spring.profiles.active=$SYSTEM_API_ENV > /dev/null 2>&1 &
        echo $! > $SERVICEAPP_API_LOGHOME/tpidapi
        echo "Starting $SERVICEAPP_API_NAME api..."
    fi
}

# 
stopapi(){
    checktapipid
    if [ $pid_api -ne 0 ];then
        echo "Stoping $SERVICEAPP_API_NAME..."
        kill -9 $tpidapi
        sleep 3s
        checktapipid
        if [ $pid_api -ne 0 ];then
        		echo "Stoping use kill -9..."
           	kill -9 $tpidapi ss
        else
            echo "$SERVICEAPP_API_NAME Stopped!"      	
        fi 
    else    
		 		 echo "WARN:$SERVICEAPP_API_NAME is not runing"
    fi
}

# 
status(){
    checktapipid
    if [ $pid_api -eq 0 ];then
        echo "$SERVICEAPP_API_NAME is not runing"
    else
        echo "$SERVICEAPP_API_NAME is runing"
    fi
}

# 
case "$1" in
    'start')
        startapi
        ;;
    'stop')
        stopapi
        ;;
    'restart')
        stopapi
        sleep 5
        startapi
        ;;
    'status')
        status
        ;;
    *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
esac
exit 0;
