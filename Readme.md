# 本项目主要实现FP-tree树的建树工作
包含以下几个子任务的实现：

0.根据所给数据建立FP-tree

1.统计所建FP-tree中的节点数目,包括根节点;

2.通过使用一个算法给出从根到叶子的节点数最多一条路径,并显示其中除根以外每个节点上的支持度计数;

3.给出关于根到叶子的节点一条路径,并显示其中除根以外每个节点上的支持度计数,要求此路径上所有节点的支持度计数之和最大.

## 本项目的运行环境是安装在各种操作系统上的jvm
jdk 1.8以上版本 IDE:IntelliJ IDEA


## 实现第一个子任务我们这里是用建树的时候实现的统计计数
对于每一个记录对于每一项来说：
应该将该项放到树中对比，如果有一个节点等于该项，那么该节点数值加一，如果没有则需要创建新的节点，那么只需要在每次创建新节点的时候计数即可
关键代码
```
private void addNodes(TreeNode ancestor, LinkedList<String> record, final Map<String, TreeNode> headers) {
        while (!record.isEmpty()) {
            String item = (String) record.poll();
            //单个项的出现频数必须大于最小支持数，否则不允许插入FP树。达到最小支持度的项都在headers中。把要把频数低于minSuport的排除在外，这也正是FPTree比穷举法快的真正原因
            if (headers.containsKey(item)) {
                TreeNode leafnode = new TreeNode(item);
                leafnode.setCount(1);
                leafnode.setParent(ancestor);
                ancestor.addChild(leafnode);
                TreeNode header = headers.get(item);
                TreeNode tail=header.getTail();
                if(tail!=null){
                    tail.setNextHomonym(leafnode);
                }else{
                    header.setNextHomonym(leafnode);
                }
                header.setTail(leafnode);
                this.increaseNodeCount();
                addNodes(leafnode, record, headers);
            }
        }
    }
```

## 第二个任务的实现是一个最长路径的问题
首先建立三个栈abc然后将根结点放到a栈中然后将a中的节点的子节点，放到b栈中，然后将b栈中的节点的子节点放到a栈中，重复上面的过程直到ab两个栈中的节点都没有子节点那么只需要将剩余节点放到c栈中就得到了最长路径的末节点，然后从这些节点返回到root就得到了最长路径当然可能会有多条最长路径

## 第三个任务
这里第三个任务最终还是使用了遍历的方法来实现,不过令人欣慰的是效果还挺令人满意的这也会成为本项目的最终上交版


# 开发计划

准备实现数据挖掘算法FP—growth

---

# 参考资料
正所谓 前人栽树后人乘凉,经过参看别人的分享可以快速成长，下面是本文实现所参考的文章

[FP-Tree算法的实现 - 张朝阳 - 博客园](https://www.cnblogs.com/zhangchaoyang/articles/2198946.html#)

[FP Tree算法原理总结 - 刘建平Pinard - 博客园](https://www.cnblogs.com/pinard/p/6307064.html)