# Pearl Chunk Loading

珍珠区块加载

**仅适用于`mc1.21`和`mc1.21.1`, 没有移植到其它版本的计划**

默认设置下, 珍珠的x和z的速度大于20且超过2s珍珠就会自动移除, 你可以使用下面这条java参数来禁用此功能
```
-Dpearl.keep=true
```

如果你在使用[Carpet TIS Addition](https://modrinth.com/mod/carpet-tis-addition), 此模组会将珍珠的加载票注入到
Carpet TIS Addition的记录器中, 你可以使用下面这条指令订阅它
```
/log ticket ender_pearl
```