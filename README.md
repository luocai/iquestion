# 项目简介
该项目是一个类知乎的问答网站，用户登录后可以进行提问、回答问题。可以给回答点赞、反对。同时可以对问题或者其他用户进行关注，关注后可以在自己的timelime上看到这些关注对象的最新动态。除此之外，
用户可以给其他用户发送站内信以及接受系统或者其他用户给自己发的站内信。
# 实现功能
* 登录注册
* 用户提问、回答
* 对回答进行点赞、踩
* 敏感词和js标签过滤
* 站内信
* 关注、粉丝列表实现
* timeline的实现，每个用户看到的是不同的动态
* 全文搜索的实现
# 技术栈
* springboot
* mybatis
* thymeleaf
* redis
* mysql
* solr
# 项目难/亮点
* 敏感词过滤算法：采用前缀树实现
![敏感词前缀树](/imgs/1.png)
   + 详细的算法过程及讲解请参考我的博文：[敏感词过滤算法详解](https://blog.csdn.net/qq_37410328/article/details/83183673)
* 异步队列的设计
![异步队列](/imgs/12.png)
   + 事件类型（枚举类型）
   + 事件模型（事件类型，谁发起的，事件的载体，谁响应） 如：我点赞了小明的评论 我是发起者，事件类型是点赞，事件载体是评论 响应者是小明
   + 生产者（把具体事件放入redis中 jedis.lpush）
   + 事件处理器(包括具体的处理方法dohandler 以及 获取关联的事件 getSupportEventTypes）
   + 消费者（先通过AppcontexContext把所有的handler取出来，再获取每个handler关联的事件类型，然后用一个map把事件类型与具体的handler存起来，再启一个线程一直从队列中（redis）中取事件jedis.brpop，取出后从map中找对应的handler并执行）
* timeline的实现
   + feed实体（type，数据）
   + 拉：当发生事件时（评论，关注等），产生feed数据并存入mysql。用户打开页面后根据关注的实体获取他们最新的动态生成timeline。
   + 推：当发生事件时 (评论，关注等)，获取所有的关注实体，并把事件推给所有粉丝（存入redis，key为‘timeline’+ followerId）,这样的话用户从redis中获取所有的新动态
   + 推拉结合：活跃/在线用户推，其他用户拉
# 项目收获
* 开发工具更加熟练，idea大法好
* thymeleaf更加熟练了，再也不用jsp啦哈哈哈
* 某某中心的设计，比如关注中心，评论中心。比如可以关注人、也可以关注问题，就可设计一个方法follow(int userId,int entityType,int entityId),entityType和entityId共同决定关注的实体。同理评论也如此，可以评论问题，评论答案等。
* 当一个页面需要登录，登录后回到原来浏览的页面。登录Controller中增加一个next参数，required = false。当拦截器拦截到需要登录时，设置next为当前页面url,登录完成后设置return 'redirect:' + next即可回到原来页面
* 在使用redis时候，最好增加个生成redis key的工具类
* 返回给前端的组合数据可以用一个viewObject（里头放一个）进行包装，就不需要为每个页面增加一个组合类
