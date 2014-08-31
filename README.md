各种HashMap<br/>
一、无任何同步，非线程安全<br/>
二、读 更新 完全同步<br/>
三、更新时可以读<br/>
    1、Doug Lea的ConcurrentReaderHashMap<br/>
    2、CopyOnWrite<br/>
四、读、更新都并发，并且线程安全<br/>
   1、segment<br/>
      Doug Lea的CurrentHashMap<br/>
   2、bucket<br/>
      我的设想<br/>
五、使用QuickSort提供bucket搜索效率。<br/>


其它需要关注的问题：<br/>
1、是否允许NULL key、NULL value。<br/>
2、LinkedHashMap/DistributedHashMap等的考察。<br/>
3、HashMap应用级别atomic的保证。<br/>
4、HashMap应用级别事务性的探讨。<br/>

疑问：<br/>
1、sychronized和concurrent到底哪个性能好？<br/>


系统改造的方向：<br/>

一、将Reference技术应用进去(Done)<br/>
二、控制缓存对象的大小(Done)<br/>
三、控制缓存容量(注意：不是缓存对象数目)和”条目二“相关。(Done)<br/>
上面两个,可以从两个层次来实现,<br/>
一个层次:对象的大小的计算，很简单紧紧从一个对象着眼，不考虑对象图。<br/>
另一个层次：考虑对象图,很复杂。<br/>
即使是第二个层次实现了，那么对象图相关的运算的代价应该远远超过获得的好处。<br/>
四、提供更多的细节控制手段<br/>
    1、比如动态调整缓存容量(Done)<br/>
    2、动态调整缓存对象的生命周期(Done)<br/>
    3、动态调整Reference方式(Done)<br/>
    4、提供命中率统计(Done)<br/>
等等。<br/>

五、多级缓存，而不仅仅是用户添加对象(基本Done)<br/>
    包含的问题：In Memory--->Local File System--->DB System<br/>
    
六、分布式的实现(Partial)<br/>
    


