#!/bin/bash
######################################
# 函数定义
function get_answer {
  unset ANSWER
  ASK_COUNT=0
  while [ -z "$ANSWER" ]  # 检查字符串长度是否为0，为0则test命令返回0
  do
    ASK_COUNT=$[ $ASK_COUNT + 1 ]
    case $ASK_ACOUNT IN
    2) echo "请根据提示输入：";;
    3) echo "最后一次确认，请根据提示输入：";;
    4) echo "由于您拒绝输入，即将退出程序。"
       exit;;
    esac

    if [ -n "$LINE2" ] # -n 检查字符串长度是否非0
    then
        echo $LINE1
        echo -e $LINE2" \c"
    else
        echo -e $LINE1" \c"
    fi
    read -t 60 ANSWER
  done
  unset LINE1
  unset LINE2
}

function process_answer() {
  case $ANSWER in
  y|Y|yes|YES) ;;
  *) echo $EXIT_LINE1
     echo $EXIT_LINE2
     exit;;
  esac
  unset EXIT_LINE1
  unset EXIT_LINE2
}
############### MAIN SCRIPT ######################
LINE1="please enter the username of the user "
LINE2="account you wish to delete from system"
get_answer
USER_ACCOUNT=$ANSWER
# double check
LINE1="$USER_ACCOUNT，是否是您希望"
LINE2="从系统中删除的用户账号? [y/n]"
get_answer
EXIT_LINE1="用户$USER_ACCOUNT，不是您希望删除的， "
EXIT_LINE2="我们即将退出程序..."
process_answer

USER_ACCOUNT_RECORD=$(cat /etc/passwd | grep -w $USER_ACCOUNT)
if [ $? -eq 1 ]
then
  echo "账号$USER_ACCOUNT未找到，即将退出脚本..."
  exit
fi
#####################################
# 查找属于该用户的运行中的进程
ps -u $USER_ACCOUNT >/dev/null
case $? in
1) echo "该用户下当前没有进程在运行。" ;;
0) echo "用户$USER_ACCOUNT下有一下运行中的进程："
   ps -u $USER_ACCOUNT
   LINE1="是否终止这些进程？[y/n]"
   get_answer
   case $ANSWER in
   y|Y|yes|YES)
      echo "进程终止中..."
      COMMAND_1="ps -u $USER_ACCOUNT --no-heading"
      COMMAND_3="xargs -d \\n /bin/kill -9"
      $COMMAND_1 | gawk '{print $1}' | $COMMAND_3
      echo "进程已终止";;
   *) echo "不终止进程";;
esac
#####################################
# 查找属于该用户的所有文件
echo "即将生成报告展示属于该用户的所有文件"
REPORT_DATE=$(date + %y%m%d)
REPORT_FILE=$USER_ACCOUNT"_Files_"$REPORT_DATE".rpt"
find / -user $USER_ACCOUNT > $REPORT_FILE 2>/dev/null
echo "报告名称:  $REPORT_FILE"
echo "报告地址： $(pwd)"
echo "确定从系统中删除用户$USER_ACCOUNT? [y/n]"
get_answer
EXIT_LINE1="您选择了现在不删除用户，即将退出..."
process_answer
userdel $USER_ACCOUNT
echo "用户$USER_ACCOUNT已删除。"
exit


