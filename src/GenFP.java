import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GenFP {
    private int minSuport;
    private String infile;
    private int treeNodeCount=2;
    //增加节点计数
    public void increaseNodeCount(){
        treeNodeCount++;
    }
    //获取节点计数
    public int getTreeNodeCount() {
        return treeNodeCount;
    }
    //设置最小项数
    public void setMinSuport(int minSuport) {
        this.minSuport = minSuport;
    }
    //设置数据文件
    public void setInfile(String infile) {
        this.infile = infile;
    }

    public static void main(String[] args) {
        //树的根节点
        TreeNode root;
        //最小支持度
        GenFP genFP = new GenFP();
        genFP.setInfile("trolley.txt");
        genFP.setMinSuport(3);
        root=genFP.genTree();
        System.out.println("树中的节点数目是："+genFP.getTreeNodeCount());
    }

    //建树操作
    public TreeNode genTree(){
        TreeNode root;
        List<List<String>> trans = this.readTransRocords(new String[] { this.infile });
        long begin = System.currentTimeMillis();
        root=buildFPTree(trans,this.minSuport);
        long end = System.currentTimeMillis();
        System.out.println("建树用时：" + (end - begin)+"ms");
        return root;
    }

    //从文件中读取事务
    public  List<List<String>> readTransRocords(String[] filenames) {
        Set<String> set = new HashSet<String>();
        List<List<String>> transaction = null;
        if (filenames.length > 0) {
            transaction = new LinkedList<List<String>>();
            for (String filename : filenames) {
                try {
                    FileReader fr = new FileReader(filename);
                    BufferedReader br = new BufferedReader(fr);
                    try {
                        String line = null;
                        // 一项事务占一行
                        while ((line = br.readLine()) != null) {
                            if (line.trim().length() > 0) {
                                // 每个item之间用","分隔
                                String[] str = line.split(",");
                                //每一项事务中的重复项需要排重
                                Set<String> record = new HashSet<String>();
                                for (String w : str) {
                                    record.add(w);
                                    set.add(w);
                                }
                                List<String> rl = new ArrayList<String>();
                                rl.addAll(record);
                                transaction.add(rl);
                            }
                        }
                    } finally {
                        br.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Read transaction records failed." + ex.getMessage());
                    System.exit(1);
                }
            }
        }
        return transaction;
    }

    //建立树
    public TreeNode buildFPTree(List<List<String>> transRecords,int minSuport) {
        //计算每项的频数
        final Map<String, Integer> freqMap = getFrequency(transRecords);
        //将每一项事务排序
        for (List<String> transRecord : transRecords) {
            Collections.sort(transRecord, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return freqMap.get(o2) - freqMap.get(o1);
                }
            });
        }
        //创建项头表
        Map<String, TreeNode> headers = new HashMap<String, TreeNode>();
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            String name = entry.getKey();
            int cnt = entry.getValue();
            if (cnt >= minSuport) {
                TreeNode node = new TreeNode(name);
                node.setCount(cnt);
                headers.put(name, node);
            }
        }
        //接下来将每一个事物插入到树里面
        TreeNode treeRoot = buildSubTree(transRecords, freqMap, headers);
        return treeRoot;

    }

    //计算每个事务项的数量
    private static Map<String, Integer> getFrequency(List<List<String>> transRecords) {
        Map<String, Integer> rect = new HashMap<String, Integer>();
        for (List<String> record : transRecords) {
            for (String item : record) {
                Integer cnt = rect.get(item);
                if (cnt == null) {
                    cnt = new Integer(0);
                }
                rect.put(item, ++cnt);
            }
        }
        return rect;
    }

    //将事务插入到树中
    private TreeNode buildSubTree(List<List<String>> transRecords, final Map<String, Integer> freqMap,
                                         final Map<String, TreeNode> headers) {
        //虚根节点
        TreeNode root = new TreeNode();
        //对每一个事物集
        for (List<String> transRecord : transRecords) {
            LinkedList<String> record = new LinkedList<String>(transRecord);
            TreeNode subTreeRoot = root;
            TreeNode tmpRoot = null;
            if (root.getChildren() != null) {
                //延已有的分支，令各节点计数加1
                //如果在子节点中找到相同的那么给该节点计数加一，否则就会创建新的分支
                while (!record.isEmpty() && (tmpRoot = subTreeRoot.findChild(record.peek())) != null) {
                    tmpRoot.countIncrement(1);
                    subTreeRoot = tmpRoot;
                    record.poll();
                }
            }
            addNodes(subTreeRoot, record, headers);
        }
        return root;
    }

    //创建新节点
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

}
