# Kotlin_Try

2017年7月31 12:48

kotlin被定为android开发的官方语言已经快3个月了，一直没有去尝试使用kotlin。这里面的很大一个原因是kotlin不用分号。好不容易习惯上了C语言的风格，现在又要摈弃，
所以多少有些不情愿。

一、项目简介
  Kotlin_Try的主要功能是三个：听音乐，看新电影（只是影评和简介），阅读有质量的文章

  音乐部分来自百度音乐，电影资源来自豆瓣，文章来自One.不过关于文章的部分目前还没有实现，
  而且在视频方面豆瓣api提供的内容有限，这个到后期可能会更改。

二、项目展示
<table>
    <tr>
        <td>	<img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/splash.jpg" width = "300" ></td>
        <td>	<img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/songSheetNet.jpg" width = "300" ></td>
        <td> 	<img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/rank.jpg" width = "300"></td>
    </tr>
</table>


<table>
    <tr>
	    <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/radio.jpg" width = "300" ></td>
        <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/localSongList.jpg" width = "300" ></td>
        <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/musicPlay.jpg" width = "300" > </td>
    </tr>
</table>



<table>
    <tr>
        <td>	<img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/inTheater.jpg" width = "300" ></td>
        <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/Top250.jpg" width = "300"></td>
        <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/movieDetail.jpg" width = "300" ></td>
    </tr>
</table>

<table>
    <tr>
        <td>	<img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/localMovieList.jpg" width = "300" ></td>
		<td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/playVideo.jpg" width = "300"></td>  
        <td> <img src="https://github.com/KingLanding94/Kotlin_Try/blob/master/app/ScreenShot/songSheet.jpg" width = "300" ></td>
    </tr>
</table>



三、所用技术

  1、MVP开发模式,Kotlin开发语言
  2、rxjava+retrofit+okhttp网络框架
  
  3、glide加载图片
  
  4、glide-transformations实现对图片的变换（唱片的圆形裁剪，音乐播放背景的模糊）
  
  5、eventbus实现跨组件间的通信
  
  6、greendao对数据库的管理（备注：现在数据库存在很多问题）
  
  7、jiecaovideoplayer播放本地和网络视频，这个播放器用起来很简单
  
  8、SmartRefreshLayout下拉刷新
  
