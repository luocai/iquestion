package com.iquestion.common;

public class Constant {

    public static final int RESULT_CODE_SUCCESS = 200;  // 成功处理请求
    public static final int RESULT_CODE_BAD_REQUEST = 412;  // bad request
    public static final int RESULT_CODE_SERVER_ERROR = 500;  // 没有对应结果

    public static final int NEED_LOGIN = 401;

    public static int QUESTION_TYPE = 0; // 问题类型（评论中心需要）
    public static int COMMENT_TYPE = 1;  //评论类型


    public static final int ANONYMOUS_USERID = 3; //未登录用户
}
