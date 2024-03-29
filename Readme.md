# 本项目主要实现FP-tree树的建树工作
包含以下几个子任务的实现：

0.根据所给数据建立FP-tree

1.统计所建FP-tree中的节点数目,包括根节点;

2.通过使用一个算法给出从根到叶子的节点数最多一条路径,并显示其中除根以外每个节点上的支持度计数;

3.给出关于根到叶子的节点一条路径,并显示其中除根以外每个节点上的支持度计数,要求此路径上所有节点的支持度计数之和最大.

## 本项目的运行环境是安装在各种操作系统上的jvm
jdk 1.8以上版本 IDE:IntelliJ IDEA可以直接打开本工程,运行genFP.java的main方法可以测试本项目
TreeNode.java是树的节点

## 项目运行设置和输出
如果使用其他数据应该修改项目中的GenFP.java中的main方法中的参数setMinSupprot来设置最小支持度，使用setInfile设置输入数据的文件
输入数据文件的格式为
```$xslt
value1,value2,value3
value4,value1
```
具体参考文件：[aaa.txt](aaa.txt)

运行main函数的输出格式如下（和main中的输出代码直接相关）：
```$xslt
最小支持度：140
建树用时：17ms
树中的节点数目是：1023
打印最长路径：
root(0)->20(67)->2(17)->27(3)->25(1)->13(1)->7(1)
root(0)->20(67)->8(11)->19(2)->9(1)->25(1)->32(1)
root(0)->1(86)->20(18)->19(4)->17(1)->9(1)->29(1)
root(0)->6(102)->1(21)->20(2)->29(1)->32(1)->13(1)
root(0)->6(102)->2(16)->8(2)->9(1)->29(1)->15(1)
root(0)->6(102)->27(9)->17(1)->9(1)->25(1)->15(1)
root(0)->22(138)->1(25)->9(4)->25(1)->32(1)->13(1)
root(0)->22(138)->6(19)->1(2)->19(1)->27(1)->7(1)
root(0)->22(138)->8(16)->27(3)->9(1)->15(1)->7(1)
root(0)->22(138)->20(19)->19(1)->17(1)->25(1)->13(1)
root(0)->14(168)->1(15)->8(3)->17(1)->9(1)->32(1)
root(0)->14(168)->1(15)->20(3)->25(1)->29(1)->15(1)
root(0)->14(168)->1(15)->20(3)->8(1)->29(1)->15(1)
root(0)->14(168)->22(29)->6(9)->8(2)->27(1)->13(1)
打印最大支持度路径：
支持度和:210	路径:root(0)->14(168)->22(29)->6(9)->17(3)->29(1)
支持度和:210	路径:root(0)->14(168)->22(29)->6(9)->17(3)->15(1)
支持度和:210	路径:root(0)->14(168)->22(29)->6(9)->8(2)->27(1)->13(1)
```

## 实现第一个子任务我们这里是用建树的时候实现的统计计数
对于每一个记录对于每一项来说：
应该将该项放到树中从根节点开始遍历树并对比，如果有路径上节点等于该项，那么该节点数值加一，如果没有则需要创建新的节点，那么只需要在每次创建新节点的时候计数即可

## 第二个任务的实现是一个最长路径的问题
首先建立三个栈abc然后将根结点放到a栈中然后将a中的节点的子节点，放到b栈中，然后将b栈中的节点的子节点放到a栈中，重复上面的过程直到ab两个栈中的节点都没有子节点那么只需要将剩余节点放到c栈中就得到了最长路径的末节点，然后从这些节点返回到root就得到了最长路径当然可能会有多条最长路径

## 第三个任务
这里第三个任务最终还是使用了遍历整个FP-tree的方法来实现,之前用的魔改方法二的方法行不通，最终采用遍历法,不过令人欣慰的是效果还挺令人满意的这也会成为本项目的最终上交版



# 开发计划

准备实现数据挖掘算法FP—growth

---

# 参考资料
正所谓 前人栽树后人乘凉,经过参看别人的分享可以快速成长，下面是本文实现所参考的文章

[FP-Tree算法的实现 - 张朝阳 - 博客园](https://www.cnblogs.com/zhangchaoyang/articles/2198946.html#)

[FP Tree算法原理总结 - 刘建平Pinard - 博客园](https://www.cnblogs.com/pinard/p/6307064.html)