# AndriodBillingSystem
由于记账的用户很多，需要用户注册使用本系统，要实现用户的注册，登录，用户注册的信息有（账号为姓名或电话，密码，照片），注册，可以修改自己的密码。
用户信息和所有的数据存储在远程的服务器中，本地登录后，本地保存用户的登录信息，并将远程的数据下载到本地数据库，如果用户更新数据或发现当前的本地数据的时间戳与远程服务器不同也需要更新，此时需要同步本地和服务器的数据。
记账的数据分为支出和收入两类：每一类都要记录：日期，主题，金额，时间戳。用户可增删改查自己的记账信息。可以分类统计，使用，每月/每年/总计的收入和支出信息。
实现：
移动端界面设计合理，功能完备。服务器需要自己搭建，技术任意，可以支持系统的功能。技术上网络框架可以使用OKHTTP，用户图片的上传和下载，数据的上传和下载。下载和上传的进度显示处理，可自定义View实现。
记账的数据分为支出和收入两类：每一类都要记录：日期，主题，金额，时间。用户可增删改查自己的记账信息。可以分类统计，使用，每月/每年/总计的收入和支出信息。
