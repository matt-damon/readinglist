#!/bin/bash
# 按队伍统计总分和平均分
# 两层循环，对每个team，都遍历一遍数据文件
# BEGIN 处理数据前执行  {} 针对每行的数据执行 END 处理数据后执行
for team in $(gawk -F, '{print $2}' gawk_data.txt | uniq)
do
  gawk -v team=$team 'BEGIN{FS=","; total=0}
  {
    if ($2==team)
    {
      total += $3 + $4 + $5;
    }
  }
  END {
    avg = total / 6;
    print "total for", team, "is", total, ",the avg is",avg
  }' gawk_data.txt
done