# youdaonote-github
####有道云笔记自动上传笔记到github

> 实现功能：有道云笔记啊分享的笔记自动上传到github



#### 步骤
- 1. 分享你的笔记文件夹
- 2. F12打开调试-网络 找到如图的连接，填写在








- 3.自动发布到github脚本

 ```
 #!/bin/bash
 
 #你的文件位置
 cd /Users/qulibin/IdeaProjects/java-eye
  
 git pull
 
 git add ./
 
 current='date "+%Y-%m-%d %H:%M:%S"'
 
 git commit -m "file modify on $datetime"
 
 git push origin master
 
 ```