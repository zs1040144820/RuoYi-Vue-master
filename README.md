# IPv6安全感知及主动防护系统前后端分离-前端

&nbsp;&nbsp;本系统采用B/S架构，分为前端、后端和数据库三部分。其中，用户通过前端登录自己账号密码，认证后可使用自己所属角色使用允许的相关功能，包括系统管理、地址生成、漏洞扫描三大部分。通过系统管理模块，可以添加用户、设置角色、管理日志等；通过地址生成模块，可以进行资产发现、资产扫描、地址预测等；通过漏洞扫描可以设定参数，对资产或资产列表进行漏洞扫描，并查看历史扫描记录等。前端通过发送请求到后端，由后端解析命令并且执行相应代码，从而提供相应的服务。后端的核心功能是调用扫描器，扫描资产和发现漏洞，并且更新漏洞库。更进一步，通过地址预测在前端给定参数情形下进行地址生成。数据库中存储扫描过程中需要存储的相关信息。蜜罐诱捕系统，是基于云原生技术（k3s）的欺骗防御系统，可以真实仿真业务环境，系统通过部署高交互高仿真蜜罐及流量代理转发，将攻击者的攻击引导到蜜罐中，达到扰乱引导以及延迟攻击的效果，可以很大程度上保护业务的安全。

## 主要技术
### IPv6资产发现与漏洞扫描系统
![在这里插入图片描述](https://img-blog.csdnimg.cn/3dc176104b2e481c8f2c83bf767818d7.png)
### 明阳蜜罐系统
![在这里插入图片描述](https://img-blog.csdnimg.cn/047dda8b9a5c433e89ab2666122c4827.png)
## 前端展示
### 首页
![在这里插入图片描述](https://img-blog.csdnimg.cn/984cb70c2f35430f86f9dd10b23c0703.png)
### ip地址收集
![在这里插入图片描述](https://img-blog.csdnimg.cn/22ad7e8248f443f0948dcc273c46eada.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/b64220f93fd143b082f3a9f03dfd523d.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/7c9d393768a34813a811eca14ac790e9.png)
### 地址预测

![在这里插入图片描述](https://img-blog.csdnimg.cn/b1a55b85929546bf88acfd345c1e42bf.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/b1d1c6a400d9404189fdc15ec1dd4379.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/0650b0c0ea184eb5ab467dc81d515cfc.jpeg)
### 口令破解
![在这里插入图片描述](https://img-blog.csdnimg.cn/d432e54283eb418d99d0345a708c5244.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/5921cd9a4d22466d8a51b89f2eb020e5.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/9e59ee6024a64071b6edc5f793e76bec.png)
### 漏洞扫描
![在这里插入图片描述](https://img-blog.csdnimg.cn/f222fe803364428e9f112ac65a6477a1.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/b4af50b661d145f185b43251107333c3.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/a35e05f6e64c463fa8c57e2c7b8b7a19.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/0f3dc81cdb9d4b86ad8a885ec2dfa5e3.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/bfbe27b2fbf8414e8655d2acf70a1c03.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/68b9729d04c741a9b19983e29ea027b9.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/e623563b317a4f969ea5d5465e4ee947.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/1f13a78f32d440ac82fd521162fb07e1.jpeg)
![在这里插入图片描述](https://img-blog.csdnimg.cn/bed53f61d56c44d9b8c42f061b267916.jpeg)
### 漏洞特征库
![在这里插入图片描述](https://img-blog.csdnimg.cn/8c08aa1f05104b23a3be7f579b31f533.png)

### 脚本库
![在这里插入图片描述](https://img-blog.csdnimg.cn/94b14ae101a847338d89612fa9ea63ec.png)
## 部署流程
1. 安装redis（须先运行，直接看博客下载安装）
2. 安装mysql（让我按到了C盘下，看着环境变量找）和Navicat，（师弟发的文件和破解软件，navicat是可视化）用Navicat运行数据库sql文件（在navicat中创建链接创建数据建库，名称ipv6addr_vuln 字符集utf8mb4 排列顺序 utf8mb4_0900_ai_ci，然后使用后端有的那个sql表创建）
3. 安装JDK1.8或以上版本
4. 安装phthon，并将pyasn包pip install一下（安装python3.8或者3.10）
5. 安装Nmap，安装之前需要安装npcap
6.Maven >= 3.0
7.Node >= 12
8. 开启电脑WSL，在微软商店下载Kali linux发行版（对应一个模块不影响整体使用）
9. 升级WSL1到WSL2，在kali linux里下载tcpdump
