package SensitiveWordsFilter;

import com.sun.deploy.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muyi on 17-5-29.
 */
public class FilterDemo {

    private class TreeNode {

        private boolean end = false;
        private Character val;
        private Map<Character, TreeNode> sonNode = new HashMap<Character, TreeNode>();

        public TreeNode getSonNode(Character val) {
            return sonNode.get(val);
        }

        public void addTreeNode(Character key, TreeNode node) {
            sonNode.put(key, node);
        }

        public boolean isKeywords() {
            return end;
        }

        public void setKeywords(boolean end) {
            this.end = end;
        }

    }

    TreeNode rootNode = new TreeNode();


    //构建树
    private void addWord(String lineTxt) {

        TreeNode tmpNode = rootNode;

        for (int i = 0; i < lineTxt.length(); ++i) {
            Character val = lineTxt.charAt(i);
            TreeNode node = tmpNode.getSonNode(val);

            if (node == null) {
                node = new TreeNode();
                tmpNode.addTreeNode(val, node);
            }
            tmpNode = node;
            if (i == lineTxt.length() - 1) {

                tmpNode.setKeywords(true);
            }

        }

    }

    public String filter(String txt) {
        if (txt == null) {
            return txt;
        }
        StringBuffer result = new StringBuffer();
        String replace = "***";
        TreeNode treeNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position < txt.length()) {
            
            Character val = txt.charAt(position);
            treeNode = treeNode.getSonNode(val);

            if (treeNode == null) {
                result.append(txt.charAt(begin));
                position = begin + 1;
                begin = position;
                treeNode = rootNode;


            } else if (treeNode.isKeywords()) {

                position = position + 1;
                begin = position;
                result.append(replace);
                treeNode = rootNode;
            } else {

                ++position;
            }


        }


        result.append(txt.substring(begin));
        return result.toString();

    }

    public static void main(String[] args) {

        FilterDemo filterDemo = new FilterDemo();
        filterDemo.addWord("色情");
        filterDemo.addWord("嫖娼");
        System.out.println(filterDemo.filter("白色情人节"));

    }


}
