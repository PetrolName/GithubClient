[![Platform][1]][2]

[1]:https://img.shields.io/badge/platform-Android-blue.svg  
[2]:https://github.com/hegaojian/JetpackMvvm

# GithubClient
- **基于MVVM架构集成JetPack组件库（LiveData、ViewModel、Lifecycle、Navigation等）编写的Github客户端**
- **整个项目全部使用Kotlin语言，应用了大量的扩展函数，简洁快捷**
- **网络请求Retrofit+协程封装，快速高效**  

## 框架解析

#### MVVM框架
![](https://upload-images.jianshu.io/upload_images/18796234-2995d1670b257a64.png?imageMogr2/auto-orient/strip|imageView2/2/w/621/format/webp)

#### 设计模式
单例模式，建造者模式，模板模式，责任链模式等多种设计模式。

#### 权限
使用rxpermissions库动态申请应用程序需要存储的权限。

#### 继承基类
BaseActivity/BaseFragment：如果不想用ViewModel，可以继承BaseActivity/BaseFragment，其中BaseFragment支持懒加载；
BaseVmActivity/BaseVmFragment：如果想使用ViewModel，可以继承BaseVmActivity/BaseVmFragment；
BaseViewModel：如果需要使用ViewModel，可以继承BaseViewModel，里面实现了协程，可直接方便调用；
BaseLoadMoreAdapter：如果需要加载更多，可直接继承BaseLoadMoreAdapter，快速集成；

#### 多状态页面
请求数据失败时，会显示错误页面，可点击重试，提示文字可自行编辑；
请求数据时空时，会显示数据空提示，提示文字可自行编辑；

#### 网络请求（Retrofit+协程）
继承BaseViewModel就可以直接使用协程请求网络，方便快捷；

## 使用的库：
- [OkHttp](https://github.com/square/okhttp)
- [Retrofit](https://github.com/square/retrofit)
- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
- [Glide](https://github.com/bumptech/glide)
- [RxPermissions](https://github.com/tbruyelle/RxPermissions)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [Moshi](https://github.com/square/moshi)
- [ImmersionBar](https://github.com/gyf-dev/ImmersionBar)
- [AgentWeb](https://github.com/Justson/AgentWeb)
- [LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)
- [PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar)
- [Mockito](https://site.mockito.org/)

## 使用(APK包在GithubClient/app/debug目录下)
1、由于github方面的限制，所以开发者要去github上面注册OAuth Application，然后clone下来，如：
git clone https://github.com/PetrolName/GithubClient

2、如不知怎么注册的，可以参考下面[此链接](https://docs.github.com/cn/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) ，按步骤获取Access Token；

3、获取Access Token后，配置到项目根目录下的local.properties文件中，如：
USER_ACCESS_TOKEN="************************************"

## 效果图
![](https://upload-images.jianshu.io/upload_images/18796234-bda679a513b2cba7.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/401/format/webp) ![](https://upload-images.jianshu.io/upload_images/18796234-4631fc0866b76627.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/402/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-305e41833fcbd41a.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/400/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-0fd03ec8c3681458.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/401/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-adc43a2afd5e885f.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/399/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-c4578f28e750b125.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/402/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-206010c5d9392fd0.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/402/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-4862b458e8c4ec8a.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/404/format/webp)
![](https://upload-images.jianshu.io/upload_images/18796234-ffcaf94c26b3bb02.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/400/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-3a5ed6854948ee8c.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/403/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-130c3283fd69b886.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/402/format/webp)![](https://upload-images.jianshu.io/upload_images/18796234-e6f171223d7a993e.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/402/format/webp)

