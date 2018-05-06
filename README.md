# rabbitmqdemo
rabbitmq 是一个消息中间件，用于接收和转发消息
# 学习任务
* 基本服务搭建
* rabbitmq的特性
* rabbitmq消息的接收、存储、转发原理

# windows 环境安装
 1. 下载erlang  地址： http://www.erlang.org/downloads
 2. 管理员的身份安装erlang (注意：必须使用管理员身份安装，否则可能导致后面的rabbitmq安装不成功)
 这里注意一下,我第一次安装的时候不是用管理员安装的，导致后面启动不了。卸载了之后重装，系统中产生了两个.erlang.cookie文件，分别位于C:\Users\18064
 和C:\Windows\System32\config\systemprofile这连个文件夹下，需要将C:\Windows\System32\config\systemprofile这个文件夹下的.erlang.cookie拷贝
 到C:\Users\18064下面，覆盖掉C:\Users\18064下面的文件
 3. 下载并安装rabbitmq
 4. rabbitmq配置：
  * 激活Rabbit MQ's Management Plugin 使用Rabbit MQ 管理插件，可以更好的可视化方式查看Rabbit MQ 服务器实例的状态，你可以在命令行中使用下面的命令激活活。 输入：rabbitmq-plugins.bat  enable  rabbitmq_management
  * 创建管理用户。输入：rabbitmqctl.bat add_user gujie gujie1991
  * 为用户赋值管理员权限。输入：rabbitmqctl.bat set_user_tags gujie administrator
  * 查看用户。输入：rabbitmqctl.bat list_users
  
# centos 7 环境安装
 1. 下载
