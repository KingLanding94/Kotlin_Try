# Kotlin_Try

2017年7月31 12:48

kotlin被定为android开发的官方语言已经快3个月了，一直没有去尝试使用kotlin。这里面的很大一个原因是kotlin不用分号。好不容易习惯上了C语言的风格，现在又要摈弃，
所以多少有些不情愿。

一、项目简介
  Kotlin_Try的主要功能是三个：听音乐，看新电影（只是影评和简介），阅读有质量的文章

  音乐部分来自百度音乐，电影资源来自豆瓣，文章来自One

二、项目展示
<table>
    <tr>
        <td>	<img src="http://img.blog.csdn.net/20170801000006050" width = "300" ></td>
        <td>	<img src="http://img.blog.csdn.net/20170801003111574" width = "300" ></td>
        <td> <img src="http://img.blog.csdn.net/20170801000755587" width = "300"></td>
    </tr>
</table>


<table>
    <tr>
	    <td> <img src="http://img.blog.csdn.net/20170801000148386" width = "300" ></td>
        <td> <img src="http://img.blog.csdn.net/20170801003212767" width = "300" ></td>
        <td> <img src="http://img.blog.csdn.net/20170801003133767" width = "300" > </td>
    </tr>
</table>



<table>
    <tr>
        <td>	<img src="http://img.blog.csdn.net/20170801003551379" width = "300" ></td>
        <td> <img src="http://img.blog.csdn.net/20170801003712173" width = "300"></td>
        <td> <img src="http://img.blog.csdn.net/20170801003212767" width = "300" ></td>
    </tr>
</table>

<table>
    <tr>
        <td>	<img src="http://img.blog.csdn.net/20170801004116449" width = "300" ></td>
		<td> <img src="http://img.blog.csdn.net/20170801004013285" width = "300"></td>  
        <td> <img src="http://img.blog.csdn.net/20170801004047097" width = "300" ></td>
    </tr>
</table>



三、所用技术

  1、MVP开发模式
  
  2、rxjava+retrofit+okhttp网络框架
  
  3、glide加载图片
  
  4、glide-transformations实现对图片的变换（唱片的圆形裁剪，音乐播放背景的模糊）
  
  5、eventbus实现跨组件间的通信
  
  6、greendao对数据库的管理（备注：现在数据库存在很多问题）
  
  7、jiecaovideoplayer播放本地和网络视频，这个播放器用起来很简单
  
  8、SmartRefreshLayout下拉刷新
  
