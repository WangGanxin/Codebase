
###Python打包及优化（美团多渠道打包）
---
既然是Python打包，那么python环境是必须的，否则无法运行python脚本文件，mac系统下默认安装了Python环境，而Windows系统下则需要自己安装了，这个安装过程相对可以简单，大家可以自行谷歌下，记得配好环境变量。验证是否安装成功的方式是，打开命令行，输入python,如果安装成功的话，会打印python的版本信息。

下面我们以[友盟应用统计](http://dev.umeng.com/analytics/android-doc/integration)为例，进行相关的操作。

#####需要准备的东西：

- 签名好的Apk文件 
- channel.py (python脚本文件
- channel列表文件 （注：必须命名为android_channels.txt，如果不想这样命名，可自行更改python脚本代码
- ChannelUtil.java工具类 


其中channel列表文件（android_channels.txt）的格式为每一个渠道号换一行，示例：

```Txt

xiaomi
360mobile
wandoujia
baidu

```

另外python脚本文件、ChannelUtil.java这里就不贴代码了，有点长，但还是比较简单容易理解的，大家可以自己下载下来看看，附上 ：[传送门](http://stay4it.com/course/13/reviews/) 

#####使用姿势：

首先，在签名打包一个Apk之前，需要在程序的入口处添加如下代码：

```Java

        String channel= ChannelUtil.getChannel(mAppContext);  //获取渠道号，内存>SharedPreferences>apk的/META-INF/目录
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),umengAppkey,channel)); //友盟：通过代码的方式设置渠道号

```

生成好以后，将该apk文件和上面所准备的东西放在同一个文件夹，打开终端命令行，进入该文件夹。执行命令：

	python channel.py apk包名

很快就在当前目录下生成一个release文件夹，里面生成了各个渠道所需要的渠道包。

这一条简单的命令背后，到底隐藏着什么不为人知的流程呢？请看下图：

![Python-Workflow](/media/2016/python-packaging-workflow.png)

整理思路来看，就是通过python脚本去读取渠道列表，然后一个for循环将签名好的apk写入渠道号，并在当前目录下，生成一个release文件夹，将写入好的渠道包放进去，那么这个for循环内部究竟是怎样的一个操作呢？它分为以下几步：

1. 复制签名好的signatured.apk到./release文件夹下
2. 重命名signatured.spk 为 signatured_channel_xxx.apk
3. 找到apk/META-INF/目录
4. 新建一个文件名为 channel_xxx的空文件

这种通过python打包的方式有什么优点和缺点呢？

优点：

- 只需要一个签名好的apk
- 速度快（在渠道少的情况下，基本秒开，如果渠道更多，有100多个的话应该也不会超过一分钟）

缺点：

- 依赖Java的签名方式（现有的打包方式，在apk的META-INF目录下添加文件，是不需要重新签名的，但如果谷歌更改了这套签名方式，那么这种方法就不适用了）
- 必须支持只用Java代码写入渠道号

#####zipalign优化：

是否上述的这种打包方式就一定完美了呢？也许你会遇到这样的问题：

- 在Google Play上提交会失败（如果你的应用要上传到该市场的话
- Lollipop系统（Android 5.0.1）安装可能会提示解析安装包错误

所以介绍来要介绍另外一款工具——zipalign，官方的定义是这样子的：

	zipalign is an archive alignment tool that provides important optimization to Android application (APK) files

简单提炼为关键词就是：**优化工具**、**4字节边界对齐**、**减少内存使用**、**提高效率**

何时需要使用这个工具？

- apk签名之后（在开发过程中，更多的时候是eclipse、as自动帮我们使用了这个工具，不需要我们手动去使用它的
- 对apk进行添加或更改的时候

常用的命令：

- zipalign -c -v <alignment> existing.apk 
- zipalign [-f] [-v] <alignment> infile.apk outfile.apk

-c ：验证apk是否按照某种对齐方式对齐

-f ：覆盖已经存在的文件

-v ：输出verbose级别的信息

<alignment\>  ：对齐方式，这里我们以4字节的方式对齐

existing.apk ：需要验证的apk文件

infile.apk  ：要打包的apk文件

outfile.apk ：要输出的apk文件


第一条命令是用来验证apk是否有进行过zipalign优化；第二条命令是用来进行zipalign优化


那么zipalign这个工具在哪里呢？

我们可以打开android sdk的安装目录下，在build-tools/版本号文件夹/找到一个zipalign.exe，其实zipalign是在android 1.6之后提供的一个工具，在使用之前我们最好可以把这个路径配置到环境变量里。

在使用的时候，我们通常先用第一条命令判断我们的apk文件是否已经进行过zipalign优化，接着再使用第二条命令是优化我们的apk文件，快捷命令：

首先是验证apk：  
	
	zipalign -c -v 4 demo_channel.apk

进行zipalign优化： 

	zipalign -f -v 4 demo_channel.apk demo_align.apk

有些同学可能会问，你这一个apk优化还好，那如果有很多个渠道包，我们应该怎么去优化呢？这里已经在刚刚的传送门里，给大家准备好了两个脚本文件，分别是mac系统下的zipalign_batch.sh和windows系统下的zipalign_batch.bat文件，并且已经集成到channel.py的python脚本当中，我们在使用的时候，可以根据自己的需要自行开启相关的代码即可：

```Python

    #mac
    #os.system('chmod u+x zipalign_batch.sh')
    #os.system('./zipalign_batch.sh')

    #windows
    #os.system('zipalign_batch.bat')

```

再次提醒下大家，在开启使用这段代码的前，要把zipalign工具配置到你系统的环境变量里，否则在运行python脚本过程中会提示相关的文件找不到哈。