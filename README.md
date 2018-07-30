# rabbitmqdemo
rabbitmq 是一个消息中间件，用于接收和转发消息
# 学习任务
* 基本服务搭建
* rabbitmq的特性
* rabbitmq消息的接收、存储、转发原理

## windows 环境安装
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
  
## centos 7 环境安装
 1. 安装依赖文件 yum -y install gcc glibc-devel make ncurses-devel openssl-devel xmlto perl wget
 2. 下载 wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.15/rabbitmq-server-generic-unix-3.6.15.tar.xz  //下载RabbitMQ安装包
 3. 解压 tar -xvf rabbitmq-server-generic-unix-3.6.15.tar
 4. 配置rabbitmq环境变量
    vi /etc/profile  
      #set rabbitmq environment  
      export PATH=$PATH:/usr/local/rabbitmq/sbin  
    source /etc/profile  
 5. 启用插件 rabbitmq-plugins enable rabbitmq_management
 6. 添加用户  
    rabbitmqctl add_user superrd superrd  //添加用户，后面两个参数分别是用户名和密码，我这都用superrd了。  
    rabbitmqctl set_permissions -p / superrd ".*" ".*" ".*"  //添加权限  
    rabbitmqctl set_user_tags superrd administrator  //修改用户角色
 7. 启动服务 rabbitmq-server -detached  
    然后访问ip:15672就可以使用管理界面访问rabbitmq了
 8. 关闭防火墙
    firewall-cmd --permanent --add-port=15672/tcp  
    firewall-cmd --permanent --add-port=5672/tcp  
    systemctl restart firewalld.service
 9. 常用的命令
    启动服务：rabbitmq-server -detached【 /usr/local/rabbitmq/sbin/rabbitmq-server  -detached 】  
    查看状态：rabbitmqctl status【 /usr/local/rabbitmq/sbin/rabbitmqctl status  】  
    关闭服务：rabbitmqctl stop【 /usr/local/rabbitmq/sbin/rabbitmqctl stop  】  
    列出角色：rabbitmqctl list_users
    
## rabbitmq特性

首先梳理一下rabbitmq工作模式：
publisher->exchange->queue->consumer
即：生产者将消息发送到交换机，交换机将消息路由到和该交换机绑定的队列，然后消费者监听队列

四种交换机类型：
topic
header
fanout：将消息发送到所有绑定到当前交换机的队列，如pub/sub模式的实现，可用来实现日志收集系统
direct：比fanout更加严格的绑定模式，消息通过队列（Queue）和路由键（routingKey）绑定到交换机（exchange），所有符合路由键routingKey的消息都会被分发到对应的Queue上面去，使用场景：日志系统中，用该模式来实现对不同级别日志的分类处理

#### round-robin
该模式下，所有的消费者都会接收到相同数量的消息

#### ack机制确保消息不会丢失

即使声明了队列持久化，也不能保证数据完全不会丢失，rabbitmq不会将接收到的数据立即刷新到磁盘，默认25ms刷盘一次、

消息delivery确认：客户端向rabbitmq服务注册消费者时会生成一个单调递增的正整数tag，发送给服务端，服务端传递消息给消费者时，会在服务端和消费者之间开启一个Channel，并将该tag传递给Channel，消费者处理完消息发送消息确认给服务端时，必须带上该tag，如果消费者将消息发送到其他服务，则会抛出unknown delivery tag异常

消息传输确认有积极和消极两种机制：积极确认：确保消息被正确的消费掉，服务器才会删除该消息；消极确认，消息一旦发出，就删除消息，不关系后溪消息是否被正确接收

rabbitmq工作队列：
1、简单工作队列：点对点，一个生产者对应一个消费者
2、work Queue：一对多，该工作队列有多种分发模式
1）Round-robin dispatching 默认情况下，rabbitmq使用round-robin分发模式，将消息队列中的消息平等分发到各个消费队列中去，例如：有两个工作队列A和B的话，奇数的消息会被分发到A，偶数的会被分发到B
2）Fair dispatch
Round-robin分发模式有一个缺点，可能导致一个队列很忙，但是另一个队列却很闲，原因是工作队列只是简单的将消息发送给消费者，并没有关心ack，我们可以通过设置参数basicQos来实现Fair dispatch，basicQos有一个参数prefetchCount，用来控制每个消费队列允许最大的没有ack的消息数量，
3、pub/sub模式：
将交换机声明为fanout类型，将所有发送到交换机的消息分发到绑定在该交换机的所有队列中去
