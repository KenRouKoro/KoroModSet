# Koro Mod Set
这里存放的是一些实验性的mod。
## HutoolCore
这个mod设计之初只是用于提供hutool环境，现在作为一个公共工具库使用
> 它也许是第一个真正意义上的三端mod(Forge Fabric Paper)
## WAC 世界高度控制
> 在后续测试完在paper上的mixin后也许会加入paper支持

提供一种直观的方式调整mc中世界的高度，支持数据包。  
它的原理是在创建ServerLevel（ServerWorld）时动态修改ServerLevel（ServerWorld）的高度配置。  
> 暂未测试该方案能否用于动态修改。