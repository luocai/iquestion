package com.iquestion.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
@Component
public class SensitiveService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet(){

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");

        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        String words;

        try {
            while ((words = br.readLine()) != null){
                words = words.trim();
                addSensitiveWord(words);
            }
        }catch (IOException e){
            logger.error("读取敏感词文件失败" + e.getMessage());
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private class TreeNode{

        //是否最后一个字
        private boolean isKeyWordsEnd = false;

        //子节点
        private Map<Character,TreeNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TreeNode node){
            subNodes.put(key,node);
        }

        public TreeNode getSubNode(Character key){
            return subNodes.get(key);
        }

        public boolean isKeyWordsEnd(){
            return isKeyWordsEnd;
        }

        public void setKeyWordsEnd(Boolean end){
            isKeyWordsEnd = end;
        }
    }

    private TreeNode rootNode = new TreeNode();

    /**
     * 判断是否是一个汉字
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !(ic < 0x2E80 || ic > 0x9FFF);
    }

    public void addSensitiveWord(String words){

        TreeNode tempNode = rootNode;

        for(int i = 0;  i < words.length(); i++){

            Character c = words.charAt(i);
            if(!isSymbol(c)){
                continue;
            }

            TreeNode node = tempNode.getSubNode(c);
            if (node == null){
                node = new TreeNode();
                tempNode.addSubNode(c,node);
            }
            // 指针移动
            tempNode = node;

            //如果到了最后一个字符
            if(i == words.length() -1){
                tempNode.setKeyWordsEnd(true);
            }

        }

    }

    public String filter(String text){

        if (StringUtils.isEmpty(text)){
            return text;
        }

        String sensitiveWords = "***";
        StringBuilder result = new StringBuilder();

        TreeNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()){

            Character c = text.charAt(position);

            //如果非东亚字符，则直接跳过 ？？
            if(!isSymbol(c)){ //每次
                if(tempNode == rootNode){
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            //如果匹配失败
            if(tempNode == null){
                //说明以begin起头的那一段不存在非法词汇
                result.append(text.charAt(begin));
                begin++;
                position = begin;
                tempNode = rootNode;
                continue;
            }else if(tempNode.isKeyWordsEnd()){
                //替换敏感词
                result.append(sensitiveWords);

                position++;
                begin = position;
                tempNode = rootNode;
            }else {
                position++;
            }

        }
        result.append(text.substring(begin)); //把剩下的动加入合法集

        return result.toString();
    }


    public static void main(String[] args){

        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addSensitiveWord("妈的");
        sensitiveService.addSensitiveWord("吉吉");

        System.out.println(sensitiveService.filter("dd哈哈哈妈的e吉e吉哦"));
    }

}
